package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

    // ScanLine is a stage where the drone scans the environment to detect if it is off the island.
    // The drone uses echo and movement commands to gather information about its location.
    public class ScanLine implements Stage {

    // Different states of the drone in this stage
    private enum State {
        FLY,
        SCAN, 
        ECHO_FRONT // Echo info in front of the drone
    }

    private final boolean turnLeft;   // Boolean to determine if the drone will turn left
    private boolean offIsland;        // Tracks if the drone is off the island
    private boolean hasMoved;         // Tracks if the drone has moved
    private boolean moveOutwards;     // Tracks if the drone is moving outwards
    private State state;              // Current state of the drone in this stage

    public ScanLine(boolean turnLeft) {
        this.turnLeft = turnLeft;
        this.offIsland = false;
        this.hasMoved = false;
        this.moveOutwards = false;
        this.state = State.FLY; 
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        // Command the drone to perform an action based on the current state
        return switch (state) {
            case State.FLY -> controller.flyCommand();
            case State.SCAN -> controller.scanCommand();
            case State.ECHO_FRONT -> controller.echoCommand(dir);
            default -> controller.stopCommand();
        };
    }

    @Override
    public void processInfo(JSONObject info) {
        // Process the received information based on the current state
        switch (state) {
            case FLY -> state = State.SCAN;  // Transition to scanning after flying

            case SCAN -> {
                if (isDroneOffLand(info)) {  // Check if the drone is off the island
                    state = State.ECHO_FRONT;  // Transition to echoing if off land
                } else {
                    state = State.FLY;  // Stay flying if still on land
                    hasMoved = true;  // Mark the drone as having moved
                }
            }

            case ECHO_FRONT -> {
                String echoStatus = info.getString("found");
                if (echoStatus.equals("LAND")) {
                    state = State.FLY;  // Transition to flying if land is detected
                } else {
                    offIsland = true;  // Mark that the drone is off the island
                }
            }
        }
    }
    
    @Override
    public Stage getNextStage() {
        // Transition to the next stage (UTurn) once the drone is off the island
        return new UTurn(turnLeft, moveOutwards);
    }

    @Override
    public boolean isFinished() {
        // The stage is finished if the drone is off the island
        return offIsland;
    }

    @Override
    public boolean isLastStage() {
        // This is not the last stage of the process
        return offIsland && !hasMoved;
    }

    // Return true if drone is off land, false otherwise. 
    // info is the information received from the drone's sensors.
    private boolean isDroneOffLand(JSONObject info) {
        JSONArray biomes = info.getJSONArray("biomes");
        String currentBiome = biomes.getString(0);
        return (biomes.length() == 1) && currentBiome.equals("OCEAN");
    }
}