package ca.mcmaster.se2aa4.island.team033.drone;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Handles commands and interacts with the drone, returning JSON responses.
public class DroneController implements Controller {
    private final Drone drone; // Reference to the controlled drone.
    private static final String DECISION_KEY = "action"; // Key for action in JSON response.

    public DroneController(Drone drone) {
        this.drone = drone;
    }

    @Override
    public String flyCommand() {
        // Executes fly command, moves drone forward, and returns JSON response.
        JSONObject response = new JSONObject();
        response.put(DECISION_KEY, "fly");
        drone.moveForward();
        return response.toString();
    }

    @Override
    public String headingCommand(Direction dir) {
        // Executes heading change command, updates drone direction, and returns JSON response.
        JSONObject response = new JSONObject();
        JSONObject params = new JSONObject();
        response.put(DECISION_KEY, "heading");
        params.put("direction", dir.getSymbol());
        response.put("parameters", params);

        if (dir.equals(drone.getHeading().getRight())) {
            drone.turnRight();
        } else if (dir.equals(drone.getHeading().getLeft())) {
            drone.turnLeft();
        }

        return response.toString();
    }

    @Override
    public String echoCommand(Direction dir) {
        // Executes echo command and returns JSON response with direction.
        JSONObject response = new JSONObject();
        JSONObject params = new JSONObject();
        response.put(DECISION_KEY, "echo");
        params.put("direction", dir.getSymbol());
        response.put("parameters", params);
        return response.toString();
    }

    @Override
    public String scanCommand() {
        // Executes scan command and returns JSON response.
        JSONObject response = new JSONObject();
        response.put(DECISION_KEY, "scan");
        return response.toString();
    }

    @Override
    public String stopCommand() {
        // Executes stop command and returns JSON response.
        JSONObject response = new JSONObject();
        response.put(DECISION_KEY, "stop");
        return response.toString();
    }
}
