package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Search for the island by flying and echoing signals.
// Attempt to locate the island and proceed to the next stage once it is found.

public class FindIsland implements Stage {

    // Different states of the drone in this stage
    private enum State {
        FLY,
        ECHO_LEFT,
        ECHO_RIGHT,
        TURN_LEFT,
        TURN_RIGHT,
        REMAINING_FLIGHT_DISTANCE,
        FLY_TOWARDS_ISLAND
    }

    private boolean atIsland; // Track if the drone has reached the island
    private boolean uTurnLeft; // Determine if the drone will turn to the left
    private Integer flightsToIsland; // The number of flights needed to reach the island
    private State state; // The current state of the drone in this stage
    
    public FindIsland() {
        this.atIsland = false;
        this.uTurnLeft = false;
        this.flightsToIsland = 0;
        this.state = State.FLY;
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        // Determine the drone's command based on the current state
        return switch (state) {
            case State.FLY -> controller.flyCommand();
            case State.ECHO_LEFT -> controller.echoCommand(dir.getLeft());
            case State.ECHO_RIGHT -> controller.echoCommand(dir.getRight());
            case State.TURN_LEFT -> controller.headingCommand(dir.getLeft());
            case State.TURN_RIGHT -> controller.headingCommand(dir.getRight());
            case State.REMAINING_FLIGHT_DISTANCE -> controller.echoCommand(dir);
            case State.FLY_TOWARDS_ISLAND -> controller.flyCommand();
            default -> controller.stopCommand();
        };
    }

    @Override
    public void processInfo(JSONObject info) {
        // Process the information based on the current state
        String echoStatus;

        switch (state) {
            case State.FLY -> // Initial state: transition to left-side echo detection
                state = State.ECHO_LEFT;
            
            case State.ECHO_LEFT -> {
                // Retrieve detection status ("found" key) from the provided JSON
                echoStatus = info.getString("found");
                
                if (echoStatus.equals("GROUND")) { 
                    // If ground is detected on the left, turn left
                    state = State.TURN_LEFT;
                    uTurnLeft = false;
                } else {
                    // Otherwise, check the right side
                    state = State.ECHO_RIGHT;
                }
            }

            case State.ECHO_RIGHT -> {
                // Retrieve detection status from JSON
                echoStatus = info.getString("found");

                if (echoStatus.equals("GROUND")) {
                    // If ground is detected on the right, turn right
                    state = State.TURN_RIGHT;
                    uTurnLeft = true; 
                } else {
                    // If no ground is detected, continue flying
                    state = State.FLY;
                }
            }

            case State.TURN_LEFT, State.TURN_RIGHT -> // After turning, proceed to measure distance
                state = State.REMAINING_FLIGHT_DISTANCE;
            case State.REMAINING_FLIGHT_DISTANCE -> {
                // Retrieve the flight range from the JSON and set the next phase
                flightsToIsland = info.getInt("range");
                state = State.FLY_TOWARDS_ISLAND;
            }

            case State.FLY_TOWARDS_ISLAND -> {
                // Simulate the flight progress by decrementing the distance counter
                flightsToIsland -= 1;
                if (flightsToIsland <= 0) {
                    // Mark arrival at the island
                    atIsland = true;
                }
            }

            default -> throw new IllegalStateException("Unexpected state: " + state);
        }
    }

    @Override
    public Stage getNextStage() {
        // Transition to the next stage (ScanLine)
        return new ScanLine(uTurnLeft);
    }

    @Override
    public boolean isFinished() {
        // Check if the stage is finished (if the drone has reached the island)
        return atIsland;
    }

    @Override
    public boolean isLastStage() {
        // This is not the last stage in the process
        return false;
    }
}