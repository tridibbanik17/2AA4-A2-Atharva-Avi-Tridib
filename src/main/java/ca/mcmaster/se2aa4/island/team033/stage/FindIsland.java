package ca.mcmaster.se2aa4.island.team033.stage;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

import org.json.JSONObject;

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
        GET_RANGE,
        FLY_TO_ISLAND
    }

    private boolean atIsland; // Track if the drone has reached the island
    private boolean uTurnLeft; // Determine if the drone will make a U-turn to the left
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
            case State.FLY -> controller.fly();
            case State.ECHO_LEFT -> controller.echo(dir.getLeft());
            case State.ECHO_RIGHT -> controller.echo(dir.getRight());
            case State.TURN_LEFT -> controller.movingDirection(dir.getLeft());
            case State.TURN_RIGHT -> controller.movingDirection(dir.getRight());
            case State.GET_RANGE -> controller.echo(dir);
            case State.FLY_TO_ISLAND -> controller.fly();
            default -> controller.stop();
        };
    }

    @Override
    public void processInfo(JSONObject info) {
        // Process the information based on the current state
        String echoStatus;

        switch (state) {
            case State.FLY -> state = State.ECHO_LEFT;
            
            case State.ECHO_LEFT -> {
                echoStatus = info.getString("found");
                if (echoStatus.equals("GROUND")) { 
                    state = State.TURN_LEFT;
                    uTurnLeft = false;
                } else {
                    state = State.ECHO_RIGHT;
                }
            }

            case State.ECHO_RIGHT -> {
                echoStatus = info.getString("found");
                if (echoStatus.equals("GROUND")) {
                    state = State.TURN_RIGHT;
                    uTurnLeft = true;
                } else {
                    state = State.FLY;
                }
            }

            case State.TURN_LEFT -> state = State.GET_RANGE;
            
            case State.TURN_RIGHT -> state = State.GET_RANGE;

            case State.GET_RANGE -> {
                flightsToIsland = info.getInt("range");
                state = State.FLY_TO_ISLAND;
            }
            
            case State.FLY_TO_ISLAND -> {
                flightsToIsland--;
                if (flightsToIsland <= 0) {
                    atIsland = true;
                }
            }
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