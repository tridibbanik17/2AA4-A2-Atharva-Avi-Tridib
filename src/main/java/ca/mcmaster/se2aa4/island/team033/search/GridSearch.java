package ca.mcmaster.se2aa4.island.team033.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.map.Map;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterest;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterestType;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.stage.MoveToCorner;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;

// The GridSearch class implements the Search interface and defines the grid-based search strategy for the drone.
// It includes logic for drone movement and processing the response to add points of interest to the map.
public class GridSearch implements Search {

    // Logger to log important information and actions during the grid search
    private final Logger logger = LogManager.getLogger();

    private Drone drone;     // Drone object that performs the search
    private Controller controller;     // Controller that sends commands to the drone
    private Stage stage;    // The current stage the drone is in during the search

    public GridSearch(Drone drone) {
        this.drone = drone;
        this.controller = new DroneController(drone);
        this.stage = new MoveToCorner();
    }

    // The drone follows a series of stages and performs actions such as moving and stopping.
    // The search is stopped if the battery level falls below 50% or if the last stage is reached.
    @Override
    public String performSearch() {
        String command;

        // If the stage is the last stage or battery level is below 50%, stop the drone
        if (stage.isLastStage() || drone.getBatteryLevel() < 50) {
            command = controller.stopCommand();
        } else {
            // If the current stage is not finished, get the next command for that stage
            if (!stage.isFinished()) {
                command = stage.getDroneCommand(controller, drone.getHeading());
            } else {
                // If the stage is finished, move to the next stage and get the command for it
                stage = stage.getNextStage();
                command = stage.getDroneCommand(controller, drone.getHeading());
            }
        }
        return command;
    }

    // Process response from drone after performing a search.
    // Response contains cost, battery usage, poi
    @Override
    public void readResponse(JSONObject response, Map map) {
        Integer cost = response.getInt("cost");
        drone.drainBattery(cost);
        logger.info("Budget Remaining: {}", drone.getBatteryLevel());

        Coordinate droneLoc = drone.getLocation();
        logger.info("Drone Position: ({}, {})", droneLoc.getX(), droneLoc.getY());

        JSONObject extraInfo = response.getJSONObject("extras");
        stage.processInfo(extraInfo);

        if (extraInfo.has("creeks")) {
            JSONArray creeksFound = extraInfo.getJSONArray("creeks");
            if (!creeksFound.isEmpty()) {
                for (int i = 0; i < creeksFound.length(); i++) {
                    map.addPointOfInterest(new PointOfInterest(creeksFound.getString(i), PointOfInterestType.CREEK, drone.getLocation()));
                }
            }
        }

        if (extraInfo.has("sites")) {
            JSONArray sites = extraInfo.getJSONArray("sites");
            if (!sites.isEmpty()) {
                map.addPointOfInterest(new PointOfInterest(sites.getString(0), PointOfInterestType.EMERGENCY_SITE, drone.getLocation()));
            }
        }
    }
}