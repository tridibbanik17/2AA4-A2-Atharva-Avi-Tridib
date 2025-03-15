package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Stage that moves the drone towards the corner of the island.
// The drone scans the environment to determine the best path and reach the corner.
public class MoveToCorner implements Stage {

    // Different states of the drone in this stage
    private enum State {
        ECHO_LEFT, 
        ECHO_RIGHT, 
        ALIGN_TO_CORNER, 
        MOVE_TO_CORNER, 
        ALIGN_INWARD, 
    }

    private State state;
    private int distanceLeft;
    private int distanceRight;
    private int distanceTraveled = 0;
    private boolean hasReachedCorner;
    private boolean turnRight;

    public MoveToCorner() {
        this.state = State.ECHO_LEFT;
        this.hasReachedCorner = false;
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        // Determine heading direction based on the state and distance comparison
        Direction turnDirection = switch (state) {
            case State.ECHO_LEFT -> dir.getLeft();
            case State.ECHO_RIGHT -> dir.getRight();
            case State.ALIGN_TO_CORNER, State.ALIGN_INWARD -> {
                // Determine if the drone should turn left or right based on distance
                turnRight = distanceRight >= distanceLeft;
                yield turnRight ? dir.getLeft() : dir.getRight();
            }
            default -> null;  // Handle cases where no turn is needed
        };

        // Handle different states
        return switch (state) {
            case State.ECHO_LEFT, State.ECHO_RIGHT -> controller.echoCommand(turnDirection);
            case State.MOVE_TO_CORNER -> {
                distanceTraveled++;
                yield controller.flyCommand();
            }
            case State.ALIGN_TO_CORNER, State.ALIGN_INWARD -> controller.headingCommand(turnDirection);
            default -> controller.stopCommand();
        };
    }


    @Override
    public void processInfo(JSONObject info) {
        // Process the information based on the current state
        switch (state) {
            case State.ECHO_LEFT -> {
                distanceLeft = info.getInt("range");
                state = State.ECHO_RIGHT;
            }

            case State.ECHO_RIGHT -> {
                distanceRight = info.getInt("range");
                if (Math.min(distanceLeft, distanceRight) > 2) {
                    state = State.ALIGN_TO_CORNER;
                } else {
                    hasReachedCorner = true;
                }
            }

            case State.ALIGN_TO_CORNER -> state = State.MOVE_TO_CORNER;

            case State.MOVE_TO_CORNER -> {
                if (distanceTraveled >= Math.min(distanceLeft, distanceRight) - 2) {
                    state = State.ALIGN_INWARD;
                }
            }

            case State.ALIGN_INWARD -> hasReachedCorner = true;   
        }
    }

    @Override
    public Stage getNextStage() {
        // Transition to the next stage (FindIsland)
        return new FindIsland();
    }

    @Override
    public boolean isFinished() {
        // Check if the stage is finished (if the corner is reached)
        return hasReachedCorner;
    }

    @Override
    public boolean isLastStage() {
        // This is not the last stage in the process
        return false;
    }
}