package ca.mcmaster.se2aa4.island.team033;

import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.position.Direction;
import ca.mcmaster.se2aa4.island.team033.stage.FindIsland;
import ca.mcmaster.se2aa4.island.team033.stage.ScanLine;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;

// Tests the FindIsland stage to ensure its behavior is correct.
public class FindIslandTest {
    private final int battery = 7000;
    private final Direction dir = Direction.EAST;
    private Drone drone;
    private DroneController controller;
    private FindIsland testStage;

    private final String echoLeft = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}"; // Drone facing EAST, left is NORTH
    private final String echoRight = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}"; // Drone facing EAST, right is SOUTH
    private final String turnSouth = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}"; // Turn right from EAST to SOUTH
    private final String fly = "{\"action\":\"fly\"}";
    private final String echoFront = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}"; // Echo in the current direction (EAST)

    @BeforeEach
    void setup() {
        drone = new BasicDrone(battery, dir);
        controller = new DroneController(drone);
        testStage = new FindIsland();
    }

    @Test
    void testInitialStateFly() {
        assertEquals(fly, testStage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    void testTransitionToEchoLeft() {
        // Simulate response for FLY state to transition to ECHO_LEFT
        testStage.processInfo(echoResponse(10, "OUT_OF_RANGE")); // "OUT_OF_RANGE" means no ground detected
        assertEquals(echoLeft, testStage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    void testTransitionToEchoRight() {
        // Simulate responses to transition from FLY to ECHO_LEFT to ECHO_RIGHT
        testStage.processInfo(echoResponse(10, "OUT_OF_RANGE")); // Transition to ECHO_LEFT
        testStage.processInfo(echoResponse(5, "OUT_OF_RANGE")); // Transition to ECHO_RIGHT
        assertEquals(echoRight, testStage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    void testTransitionToTurnSouth() {
        // Simulate responses to transition from FLY to ECHO_LEFT to ECHO_RIGHT to TURN_RIGHT
        testStage.processInfo(echoResponse(10, "OUT_OF_RANGE")); // Transition to ECHO_LEFT
        testStage.processInfo(echoResponse(35, "OUT_OF_RANGE")); // Transition to ECHO_RIGHT
        testStage.processInfo(echoResponse(5, "GROUND")); // Transition to TURN_RIGHT
        assertEquals(turnSouth, testStage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    void testTransitionToRemainingFlightDistance() {
        // Simulate responses to transition from FLY to ECHO_LEFT to TURN_LEFT to REMAINING_FLIGHT_DISTANCE
        testStage.processInfo(echoResponse(10, "OUT_OF_RANGE")); // Transition to ECHO_LEFT
        testStage.processInfo(echoResponse(5, "GROUND")); // Transition to TURN_LEFT
        testStage.processInfo(echoResponse(3, "GROUND")); // Transition to REMAINING_FLIGHT_DISTANCE
        assertEquals(echoFront, testStage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    void testNextStageIsScanLine() {
        Stage nextStage = testStage.getNextStage();
        Stage expectedStage = new ScanLine(true); // Assuming uTurnLeft is true
        assertEquals(expectedStage.getClass(), nextStage.getClass());
    }

    @Test
    void testIsNotFinished() {
        assertFalse(testStage.isFinished());
    }

    @Test
    void testIsNotLastStage() {
        assertFalse(testStage.isLastStage());
    }

    // Helper method to simulate an echo response.
    private JSONObject echoResponse(int range, String found) {
        JSONObject response = new JSONObject();
        response.put("range", range);
        response.put("found", found);
        return response;
    }
}
