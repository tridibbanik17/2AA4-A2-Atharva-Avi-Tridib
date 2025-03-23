package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Stage to perform a U-turn based on initial parameters.
public class UTurn implements Stage {

    private static final int MAX_TURNS = 4;

    private final TurnExecutor turnExecutor;

    public UTurn(boolean turnLeft, boolean outward) {
        this.turnExecutor = new TurnExecutor(turnLeft, outward);
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        return turnExecutor.getCommand(controller, dir);
    }

    @Override
    public void processInfo(JSONObject info) {
        // No processing needed for U-turn.
    }

    @Override
    public Stage getNextStage() {
        return new ScanLine(!turnExecutor.shouldTurnLeft());
    }

    @Override
    public boolean isFinished() {
        return turnExecutor.hasCompletedTurn();
    }

    @Override
    public boolean isLastStage() {
        return false;
    }

    // Handles U-turn execution logic.
    private static class TurnExecutor {
        private final boolean turnLeft;
        private final int flyDistance;
        private final int turnOpposite;
        private int turnCount;
        private boolean hasTurned;

        public TurnExecutor(boolean turnLeft, boolean outward) {
            this.turnLeft = turnLeft;
            this.turnCount = 0;
            if (outward) {
                this.flyDistance = 3;
                this.turnOpposite = 0;
            } else {
                this.flyDistance = 1;
                this.turnOpposite = 4;
            }
        }

        public boolean shouldTurnLeft() {
            return turnLeft;
        }

        public boolean hasCompletedTurn() {
            return hasTurned;
        }

        // Returns the command based on the current turn state.
        public String getCommand(Controller controller, Direction dir) {
            String command;
            if (turnCount == turnOpposite) {
                command = generateTurnCommand(controller, !turnLeft, dir);
            } else if (turnCount == flyDistance) {
                command = controller.flyCommand();
            } else {
                command = generateTurnCommand(controller, turnLeft, dir);
            }

            if (turnCount >= MAX_TURNS) {
                hasTurned = true;
            }

            turnCount++;
            return command;
        }

        // Generates a turn command based on direction.
        private String generateTurnCommand(Controller controller, boolean dirLeft, Direction direction) {
            direction = dirLeft ? direction.getLeft() : direction.getRight();
            return controller.headingCommand(direction);
        }
    }
}
