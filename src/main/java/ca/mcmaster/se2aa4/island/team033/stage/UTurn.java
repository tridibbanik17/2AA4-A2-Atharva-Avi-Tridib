package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// UTurn is a stage where the drone turns 180 degrees (makes a U-turn) in order to return to its path.
// The drone may turn left or right depending on the initial parameters.
public class UTurn implements Stage {

    private static final int MAX_TURNS = 4; // Maximum number of turns before the U-turn is considered complete
    private boolean hasTurned; // Flag to track if the drone has completed the turn
    private boolean turnLeft; // Flag indicating whether the drone turns left or right
    private Integer turnCount; // Counter for the number of turns made by the drone
    private Integer flyDistance; // The number of units the drone flies after making the turn
    private Integer turnOpposite; // Counter for the opposite condition of the U-turn logic (to reverse the turn direction after a certain number of turns)


    public UTurn(boolean turnLeft, boolean outward) {
        this.hasTurned = false;
        this.turnLeft = turnLeft;
        this.turnCount = 0;

        // Set fly distance and opposite turn condition based on the outward direction
        if (outward) {
            flyDistance = 3;  // Set a larger fly distance if moving outward
            turnOpposite = 0; // No opposite turn in the outward case
        } 
        else {
            flyDistance = 1;  // Set a shorter fly distance if not moving outward
            turnOpposite = 4; // Opposite turn condition to reverse after 4 turns
        }
    }

    @Override
    public String getDroneCommand(Controller controller, Direction dir) {
        String command;

        if (turnCount.equals(turnOpposite)) {
            command = turnCommand(controller, !turnLeft, dir);
        } else if (turnCount.equals(flyDistance)) {
            command = controller.flyCommand();
        } else {
            command = turnCommand(controller, turnLeft, dir);
        }

        if (turnCount >= MAX_TURNS) {
            hasTurned = true;
        }

        turnCount++;

        return command;
    }

    @Override
    public void processInfo(JSONObject info) { 
        // Stage does need to process any information from JSON response. Drone U-turn logic is fixed.
    }

    @Override
    public Stage getNextStage() {
        // The next stage after the U-turn is the ScanLine stage with the opposite turn direction
        return new ScanLine(!turnLeft);
    }

    @Override
    public boolean isFinished() {
        // The stage is finished once the U-turn is completed
        return hasTurned;
    }

    @Override
    public boolean isLastStage() {
        // This is not the last stage of the process
        return false;
    }

    // Helper method to generate the turn command for the drone.
    // The direction of the turn is determined by the turnLeft flag and the current direction.
    private String turnCommand(Controller controller, boolean dirLeft, Direction direction) {
        direction = dirLeft ? direction.getLeft() : direction.getRight();
        return controller.headingCommand(direction);
    }
}