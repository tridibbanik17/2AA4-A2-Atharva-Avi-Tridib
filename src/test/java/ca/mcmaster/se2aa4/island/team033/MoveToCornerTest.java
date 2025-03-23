package ca.mcmaster.se2aa4.island.team033;

import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.position.Direction;
import ca.mcmaster.se2aa4.island.team033.stage.FindIsland;
import ca.mcmaster.se2aa4.island.team033.stage.MoveToCorner;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;

public class MoveToCornerTest {
    private Drone drone;
    private Controller controller;
    private Stage stage;
    private final String echoLeft = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"W\"}}"; // Drone's current heading firstion is set to NORTH, so left is WEST
    private final String echoRight = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}"; // Drone's current heading firstion is set to NORTH, so right is EAST

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        drone = new BasicDrone(7000, Direction.NORTH); // Drone starts facing NORTH
        controller = new DroneController(drone);
        stage = new MoveToCorner();
    }

    @Test
    void testInitialEchoLeftCommand() {
        assertEquals(echoLeft, stage.getDroneCommand(controller, drone.getHeading())); // Default is echo left
    }

    @Test
    void testTransitionToEchoRight() {
        // Simulate response for ECHO_LEFT state
        JSONObject response = echoResponse(5); // Range 5
        stage.processInfo(response);

        // Next command should be ECHO_RIGHT
        assertEquals(echoRight, stage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    void testNextStageIsFindIsland() {
        Stage nextStage = stage.getNextStage();
        Stage expectedStage = new FindIsland();
        assertEquals(expectedStage.getClass(), nextStage.getClass());
    }

    @Test
    void testIsNotFinishedInitially() {
        assertFalse(stage.isFinished());
    }

    @Test
    void testIsNotLastStage() {
        assertFalse(stage.isLastStage());
    }

    // Helper method to simulate an echo response.
    private JSONObject echoResponse(int range) {
        JSONObject response = new JSONObject();
        response.put("range", range);
        return response;
    }
}
