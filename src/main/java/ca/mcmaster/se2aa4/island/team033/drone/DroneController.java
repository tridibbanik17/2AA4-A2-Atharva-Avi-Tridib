package ca.mcmaster.se2aa4.island.team033.drone;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

// DroneController handles the commands and interacts with the Drone.
public class DroneController implements Controller {
    private final Drone drone;
    private final String decisionKey = "action";

    public DroneController(Drone drone) {
        this.drone = drone;
    }

    @Override
    public String flyCommand() {
        // Prepare a JSON response for the "fly" action
        JSONObject decision = new JSONObject();
        decision.put(decisionKey, "fly");
        drone.moveForward();
        return decision.toString();
    }

    @Override
    public String headingCommand(Direction dir) {
        // Prepare a JSON response for the "heading" action
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put(decisionKey, "heading");
        params.put("direction", dir.getSymbol());
        decision.put("parameters", params);

        // Update drone direction based on the input
        if (dir.equals(drone.getHeading().getRight())) {
            drone.turnRight();
        } else if (dir.equals(drone.getHeading().getLeft())) {
            drone.turnLeft();
        }

        return decision.toString();
    }

    @Override
    public String echoCommand(Direction dir) {
        // Prepare a JSON response for the "echo" action
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put(decisionKey, "echo");
        params.put("direction", dir.getSymbol());
        decision.put("parameters", params);

        return decision.toString();
    }

    @Override
    public String scanCommand() {
        // Prepare a JSON response for the "scan" action
        JSONObject decision = new JSONObject();
        decision.put(decisionKey, "scan");
        return decision.toString();
    }

    @Override
    public String stopCommand() {
        // Prepare a JSON response for the "stop" action
        JSONObject decision = new JSONObject();
        decision.put(decisionKey, "stop");
        return decision.toString();
    }
}