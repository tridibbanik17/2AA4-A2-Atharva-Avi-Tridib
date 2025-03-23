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
import ca.mcmaster.se2aa4.island.team033.stage.MoveToCorner;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;

// Implements a grid-based search strategy for the drone.
public class GridSearch implements Search {

    private static final Logger logger = LogManager.getLogger(GridSearch.class);
    private static final int MIN_BATTERY_LEVEL = 50; // Minimum battery level to continue search.

    private final Drone drone;
    private final Controller controller;
    private Stage stage; // Current search stage.

    // Initializes the grid search with a drone and starting stage.
    public GridSearch(Drone drone) {
        this.drone = drone;
        this.controller = new DroneController(drone);
        this.stage = new MoveToCorner();
    }

    @Override
    public String performSearch() {
        if (shouldStopSearch()) {
            return controller.stopCommand(); // Stop if battery is low or last stage is reached.
        }

        return stage.isFinished() ? getNextStageCommand() : getCurrentStageCommand();
    }

    @Override
    public void readResponse(JSONObject response, Map map) {
        updateBatteryLevel(response); // Update drone's battery level.
        logDronePosition(); // Log current position.
        processExtraInfo(response, map); // Process additional information.
    }

    // Checks if search should stop due to low battery or completion of stages.
    private boolean shouldStopSearch() {
        return stage.isLastStage() || drone.getBatteryLevel() < MIN_BATTERY_LEVEL;
    }

    // Gets the command for the current stage.
    private String getCurrentStageCommand() {
        return stage.getDroneCommand(controller, drone.getHeading());
    }

    // Advances to the next stage and gets its command.
    private String getNextStageCommand() {
        stage = stage.getNextStage();
        return stage.getDroneCommand(controller, drone.getHeading());
    }

    // Updates drone's battery level based on response cost.
    private void updateBatteryLevel(JSONObject response) {
        int cost = response.getInt("cost");
        drone.drainBattery(cost);
        // logger.info("Budget Remaining: {}", drone.getBatteryLevel()); // Uncomment to view the remaining battery budget after each action executed by the drone controller.
    }

    // Logs the drone's current position.
    private void logDronePosition() {
        drone.getLocation();
        // logger.info("Drone Position: ({}, {})", drone.getLocation().getX(), drone.getLocation().getY()); // Uncomment to view the drone's updated position after each action executed by the drone controller.
    }

    // Processes additional information from the response.
    private void processExtraInfo(JSONObject response, Map map) {
        JSONObject extraInfo = response.getJSONObject("extras");
        stage.processInfo(extraInfo); // Process stage-specific info.
        processCreeks(extraInfo, map); // Add creeks to the map.
        processSites(extraInfo, map); // Add emergency sites to the map.
    }

    // Adds creeks from the response to the map.
    private void processCreeks(JSONObject extraInfo, Map map) {
        if (extraInfo.has("creeks")) {
            JSONArray creeksArray = extraInfo.getJSONArray("creeks");
            for (int i = 0; i < creeksArray.length(); i++) {
                addPointOfInterest(map, creeksArray.getString(i), PointOfInterestType.CREEK);
            }
        }
    }

    // Adds emergency sites from the response to the map.
    private void processSites(JSONObject extraInfo, Map map) {
        if (extraInfo.has("sites")) {
            JSONArray sitesArray = extraInfo.getJSONArray("sites");
            if (!sitesArray.isEmpty()) {
                addPointOfInterest(map, sitesArray.getString(0), PointOfInterestType.EMERGENCY_SITE);
            }
        }
    }

    // Adds a point of interest to the map at the drone's current location.
    private void addPointOfInterest(Map map, String name, PointOfInterestType type) {
        map.addPointOfInterest(new PointOfInterest(name, type, drone.getLocation()));
    }
}
