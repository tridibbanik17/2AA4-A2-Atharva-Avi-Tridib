package ca.mcmaster.se2aa4.island.team033.stage;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

/**
 * Interface representing a stage in the drone's mission.
 * Each stage defines the behavior of the drone during that stage.
 */
public interface Stage {
    String getDroneCommand(Controller controller, Direction direction); // Get the drone's command based on the controller and direction
    void processInfo(JSONObject info); // Process information received from the drone's sensors
    Stage getNextStage(); // Determine the next stage in the mission
    boolean isFinished(); // Check if the current stage is completed
    boolean isLastStage(); // Check if the current stage is the final one in the mission
}
