package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Stage to scan the environment and detect if the drone is off the island.
public class ScanLine implements Stage {

    private enum State {
        FLY, SCAN, ECHO_FRONT
    }

    private final boolean turnLeft;
    private final EnvironmentScanner scanner;

    public ScanLine(boolean turnLeft) {
        this.turnLeft = turnLeft;
        this.scanner = new EnvironmentScanner();
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        return scanner.getCommand(controller, dir, scanner.getState());
    }

    @Override
    public void processInfo(JSONObject info) {
        scanner.processSensorData(info);
    }

    @Override
    public Stage getNextStage() {
        return new UTurn(turnLeft, scanner.shouldMoveOutwards());
    }

    @Override
    public boolean isFinished() {
        return scanner.isOffIsland();
    }

    @Override
    public boolean isLastStage() {
        return scanner.isOffIsland() && !scanner.hasMoved();
    }

    // Handles environment scanning logic.
    private static class EnvironmentScanner {
        private boolean offIsland;
        private boolean hasMoved;
        private boolean moveOutwards;
        private State state;

        public EnvironmentScanner() {
            this.offIsland = false;
            this.hasMoved = false;
            this.moveOutwards = false;
            this.state = State.FLY;
        }

        public State getState() {
            return state;
        }

        public boolean isOffIsland() {
            return offIsland;
        }

        public boolean hasMoved() {
            return hasMoved;
        }

        public boolean shouldMoveOutwards() {
            return moveOutwards;
        }

        // Updates state based on sensor data.
        public void processSensorData(JSONObject info) {
            switch (state) {
                case FLY -> state = State.SCAN;

                case SCAN -> {
                    if (isDroneOffLand(info)) {
                        state = State.ECHO_FRONT;
                    } else {
                        state = State.FLY;
                        hasMoved = true;
                    }
                }

                case ECHO_FRONT -> {
                    String echoStatus = info.getString("found");
                    if (echoStatus.equals("OUT_OF_RANGE")) {
                        offIsland = true;
                        moveOutwards = info.getInt("range") >= 3;
                    } else {
                        state = State.FLY;
                        hasMoved = true;
                    }
                }
            }
        }

        // Checks if the drone is off the land based on biome data.
        private boolean isDroneOffLand(JSONObject info) {
            JSONArray biomes = info.getJSONArray("biomes");
            String currentBiome = biomes.getString(0);
            return biomes.length() == 1 && currentBiome.equals("OCEAN");
        }

        // Returns the command based on the current state.
        public String getCommand(Controller controller, Direction dir, State state) {
            return switch (state) {
                case FLY -> controller.flyCommand();
                case SCAN -> controller.scanCommand();
                case ECHO_FRONT -> controller.echoCommand(dir);
                default -> controller.stopCommand();
            };
        }
    }
}
