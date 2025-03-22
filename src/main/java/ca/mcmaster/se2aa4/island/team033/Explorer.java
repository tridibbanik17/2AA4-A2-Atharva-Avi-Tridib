package ca.mcmaster.se2aa4.island.team033;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;

import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.map.ListMap;
import ca.mcmaster.se2aa4.island.team033.map.Map;
import ca.mcmaster.se2aa4.island.team033.position.Direction;
import ca.mcmaster.se2aa4.island.team033.report.NavigationReport;
import ca.mcmaster.se2aa4.island.team033.report.Report;
import ca.mcmaster.se2aa4.island.team033.search.GridSearch;
import ca.mcmaster.se2aa4.island.team033.search.Search;
import eu.ace_design.island.bot.IExplorerRaid;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private Map map;
    private Search gridSearch;
    private Report report;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));

        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        this.map = new ListMap();
        Drone drone = new BasicDrone(batteryLevel, Direction.fromSymbol(direction));
        this.gridSearch = new GridSearch(drone);
        this.report = new NavigationReport();

        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
        logger.info("----------------------------Initialization Ends Here------------------------------------\n");
    }

    @Override
    public String takeDecision() {
        String decision = gridSearch.performSearch();
        logger.info("Decision: {}", decision);
        return decision;
    }

    @Override
    public void acknowledgeResults(String s) {
        logger.info("Response: {}", s);
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        gridSearch.readResponse(response, map);
    }

    @Override
    public String deliverFinalReport() {
        String missionReport = report.generateReport(map);
        logger.info(missionReport);
        return missionReport;
    }
}