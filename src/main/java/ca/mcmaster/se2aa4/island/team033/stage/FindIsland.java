package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Stage to locate the island using echo and flight commands.
public class FindIsland implements Stage {

    private enum State {
        FLY, ECHO_LEFT, ECHO_RIGHT, TURN_LEFT, TURN_RIGHT, REMAINING_FLIGHT_DISTANCE, FLY_TOWARDS_ISLAND
    }

    private final IslandLocator islandLocator;
    private final FlightNavigator flightNavigator;

    public FindIsland() {
        this.islandLocator = new IslandLocator();
        this.flightNavigator = new FlightNavigator();
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        return flightNavigator.getCommand(controller, dir, islandLocator.getState());
    }

    @Override
    public void processInfo(JSONObject info) {
        islandLocator.processSensorData(info);
    }

    @Override
    public Stage getNextStage() {
        return new ScanLine(islandLocator.shouldTurnLeft());
    }

    @Override
    public boolean isFinished() {
        return islandLocator.hasReachedIsland();
    }

    @Override
    public boolean isLastStage() {
        return false;
    }

    // Handles island detection logic.
    private static class IslandLocator {
        private boolean atIsland;
        private boolean uTurnLeft;
        private int flightsToIsland;
        private State state;

        public IslandLocator() {
            this.atIsland = false;
            this.uTurnLeft = false;
            this.flightsToIsland = 0;
            this.state = State.FLY;
        }

        public State getState() {
            return state;
        }

        public boolean shouldTurnLeft() {
            return uTurnLeft;
        }

        public boolean hasReachedIsland() {
            return atIsland;
        }

        // Updates state based on sensor data.
        public void processSensorData(JSONObject info) {
            String echoStatus;

            switch (state) {
                case FLY -> state = State.ECHO_LEFT;

                case ECHO_LEFT -> {
                    echoStatus = info.getString("found");
                    if (echoStatus.equals("GROUND")) {
                        state = State.TURN_LEFT;
                        uTurnLeft = false;
                    } else {
                        state = State.ECHO_RIGHT;
                    }
                }

                case ECHO_RIGHT -> {
                    echoStatus = info.getString("found");
                    if (echoStatus.equals("GROUND")) {
                        state = State.TURN_RIGHT;
                        uTurnLeft = true;
                    } else {
                        state = State.FLY;
                    }
                }

                case TURN_LEFT, TURN_RIGHT -> state = State.REMAINING_FLIGHT_DISTANCE;

                case REMAINING_FLIGHT_DISTANCE -> {
                    flightsToIsland = info.getInt("range");
                    state = State.FLY_TOWARDS_ISLAND;
                }

                case FLY_TOWARDS_ISLAND -> {
                    flightsToIsland--;
                    if (flightsToIsland <= 0) {
                        atIsland = true;
                    }
                }
            }
        }
    }

    // Handles flight commands based on the current state.
    private static class FlightNavigator {
        public String getCommand(Controller controller, Direction dir, State state) {
            return switch (state) {
                case FLY -> controller.flyCommand();
                case ECHO_LEFT -> controller.echoCommand(dir.getLeft());
                case ECHO_RIGHT -> controller.echoCommand(dir.getRight());
                case TURN_LEFT -> controller.headingCommand(dir.getLeft());
                case TURN_RIGHT -> controller.headingCommand(dir.getRight());
                case REMAINING_FLIGHT_DISTANCE -> controller.echoCommand(dir);
                case FLY_TOWARDS_ISLAND -> controller.flyCommand();
                default -> controller.stopCommand();
            };
        }
    }
}
