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

/**
 * Implements a grid-based search strategy for the drone, including movement logic and response processing.
 */
public class GridSearch implements Search {

    private static final Logger logger = LogManager.getLogger(GridSearch.class);
    private static final int MIN_BATTERY_LEVEL = 50;

    private final Drone drone;
    private final Controller controller;
    private Stage stage;

    public GridSearch(Drone drone) {
        this.drone = drone;
        this.controller = new DroneController(drone);
        this.stage = new MoveToCorner();
    }

    @Override
    public String performSearch() {
        if (shouldStopSearch()) {
            return controller.stopCommand();
        }

        String command = stage.isFinished() ? getNextStageCommand() : getCurrentStageCommand();
        return command;
    }

    @Override
    public void readResponse(JSONObject response, Map map) {
        updateBatteryLevel(response);
        logDronePosition();
        processExtraInfo(response, map);
    }

    private boolean shouldStopSearch() {
        return stage.isLastStage() || drone.getBatteryLevel() < MIN_BATTERY_LEVEL;
    }

    private String getCurrentStageCommand() {
        return stage.getDroneCommand(controller, drone.getHeading());
    }

    private String getNextStageCommand() {
        stage = stage.getNextStage();
        return stage.getDroneCommand(controller, drone.getHeading());
    }

    private void updateBatteryLevel(JSONObject response) {
        int cost = response.getInt("cost");
        drone.drainBattery(cost);
        logger.info("Budget Remaining: {}", drone.getBatteryLevel());
    }

    private void logDronePosition() {
        Coordinate droneLoc = drone.getLocation();
        logger.info("Drone Position: ({}, {})", droneLoc.getX(), droneLoc.getY());
    }

    private void processExtraInfo(JSONObject response, Map map) {
        JSONObject extraInfo = response.getJSONObject("extras");
        stage.processInfo(extraInfo);
        processCreeks(extraInfo, map);
        processSites(extraInfo, map);
    }

    private void processCreeks(JSONObject extraInfo, Map map) {
        if (extraInfo.has("creeks")) {
            JSONArray creeksArray = extraInfo.getJSONArray("creeks");
            if (!creeksArray.isEmpty()) {
                for (int i = 0; i < creeksArray.length(); i++) {
                    addPointOfInterest(map, creeksArray.getString(i), PointOfInterestType.CREEK);
                }
            }
        }
    }

    private void processSites(JSONObject extraInfo, Map map) {
        if (extraInfo.has("sites")) {
            JSONArray sitesArray = extraInfo.getJSONArray("sites");
            if (!sitesArray.isEmpty()) {
                addPointOfInterest(map, sitesArray.getString(0), PointOfInterestType.EMERGENCY_SITE);
            }
        }
    }

    private void addPointOfInterest(Map map, String name, PointOfInterestType type) {
        map.addPointOfInterest(new PointOfInterest(name, type, drone.getLocation()));
    }
}
