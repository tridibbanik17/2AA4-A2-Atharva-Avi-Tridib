package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

/**
 * Stage responsible for moving the drone towards the corner of the island.
 * Scans the environment to determine the best path and reach the corner.
 */
public class MoveToCorner implements Stage {

    private enum State {
        ECHO_LEFT, ECHO_RIGHT, ALIGN_TO_CORNER, MOVE_TO_CORNER, ALIGN_INWARD
    }

    private final CornerNavigator navigator;

    public MoveToCorner() {
        this.navigator = new CornerNavigator();
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        return navigator.getCommand(controller, dir);
    }

    @Override
    public void processInfo(JSONObject info) {
        navigator.processSensorData(info);
    }

    @Override
    public Stage getNextStage() {
        return new FindIsland();
    }

    @Override
    public boolean isFinished() {
        return navigator.hasReachedCorner();
    }

    @Override
    public boolean isLastStage() {
        return false;
    }

    // Inner class for corner navigation logic
    private static class CornerNavigator {
        private State state;
        private int distanceLeft;
        private int distanceRight;
        private int distanceTraveled;
        private boolean hasReachedCorner;
        private boolean turnRight;

        public CornerNavigator() {
            this.state = State.ECHO_LEFT;
            this.hasReachedCorner = false;
        }

        public boolean hasReachedCorner() {
            return hasReachedCorner;
        }

        public String getCommand(Controller controller, Direction dir) {
            Direction turnDirection = switch (state) {
                case ECHO_LEFT -> dir.getLeft();
                case ECHO_RIGHT -> dir.getRight();
                case ALIGN_TO_CORNER, ALIGN_INWARD -> {
                    turnRight = distanceRight >= distanceLeft;
                    yield turnRight ? dir.getLeft() : dir.getRight();
                }
                default -> null;
            };

            return switch (state) {
                case ECHO_LEFT, ECHO_RIGHT -> controller.echoCommand(turnDirection);
                case MOVE_TO_CORNER -> {
                    distanceTraveled++;
                    yield controller.flyCommand();
                }
                case ALIGN_TO_CORNER, ALIGN_INWARD -> controller.headingCommand(turnDirection);
                default -> controller.stopCommand();
            };
        }

        public void processSensorData(JSONObject info) {
            switch (state) {
                case ECHO_LEFT -> {
                    distanceLeft = info.getInt("range");
                    state = State.ECHO_RIGHT;
                }

                case ECHO_RIGHT -> {
                    distanceRight = info.getInt("range");
                    if (Math.min(distanceLeft, distanceRight) > 2) {
                        state = State.ALIGN_TO_CORNER;
                    } else {
                        hasReachedCorner = true;
                    }
                }

                case ALIGN_TO_CORNER -> state = State.MOVE_TO_CORNER;

                case MOVE_TO_CORNER -> {
                    if (distanceTraveled >= Math.min(distanceLeft, distanceRight) - 2) {
                        state = State.ALIGN_INWARD;
                    }
                }

                case ALIGN_INWARD -> hasReachedCorner = true;
            }
        }
    }
}
