package ca.mcmaster.se2aa4.island.team033.stage;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

import org.json.JSONObject;

// Stage that moves the drone towards the corner of the island.
// The drone scans the environment to determine the best path and reach the corner.
public class MoveToCorner implements Stage {

    // Different states of the drone in this stage
    private enum State {
        ECHO_LEFT, 
        ECHO_RIGHT, 
        TURN_TO_CORNER, 
        FLY_TO_CORNER, 
        TURN_INWARD, 
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
        // Return the command based on the current state
        return switch (state) {
            case ECHO_LEFT -> controller.echo(dir.getLeft());
            case ECHO_RIGHT -> controller.echo(dir.getRight());
            case TURN_TO_CORNER -> controller.movingDirection(distanceRight < distanceLeft ? dir.getRight() : dir.getLeft());
            case FLY_TO_CORNER -> controller.fly();
            case TURN_INWARD -> controller.movingDirection(distanceRight < distanceLeft ? dir.getRight() : dir.getLeft());
            default -> controller.stop();
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
                    state = State.TURN_TO_CORNER;
                } else {
                    hasReachedCorner = true;
                }
            }

            case State.TURN_TO_CORNER -> state = State.FLY_TO_CORNER;

            case State.FLY_TO_CORNER -> {
                if (distanceTraveled >= Math.min(distanceLeft, distanceRight) - 2) {
                    state = State.TURN_INWARD;
                }
            }

            case State.TURN_INWARD -> hasReachedCorner = true;   
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