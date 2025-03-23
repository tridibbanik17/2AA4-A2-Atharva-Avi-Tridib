package ca.mcmaster.se2aa4.island.team033;

import org.json.JSONArray;
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
import ca.mcmaster.se2aa4.island.team033.stage.ScanLine;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;
import ca.mcmaster.se2aa4.island.team033.stage.UTurn;

public class ScanLineTest {
    private Drone drone;
    private Controller controller;
    private Stage stage;
    private final String echoNorth = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
    private final String scan = "{\"action\":\"scan\"}";
    private final String fly = "{\"action\":\"fly\"}";

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        drone = new BasicDrone(7000, Direction.NORTH);
        controller = new DroneController(drone);
        stage = new ScanLine(true);
    }

    @Test
    void testInitialFlyCommand() {
        String droneCommand = stage.getDroneCommand(controller, drone.getHeading());
        assertEquals(fly, droneCommand);
    }

    @Test
    void testScanAfterFly() {
        stage.getDroneCommand(controller, drone.getHeading());
        stage.processInfo(null); 
        String droneCommand = stage.getDroneCommand(controller, drone.getHeading());
        assertEquals(scan, droneCommand);
    }

    @Test
    void testEchoAfterScan() {
        stage.getDroneCommand(controller, drone.getHeading());
        stage.processInfo(null); 
        stage.getDroneCommand(controller, drone.getHeading()); 
        stage.processInfo(scanResponse()); 
        String droneCommand = stage.getDroneCommand(controller, drone.getHeading());
        assertEquals(echoNorth, droneCommand);
    }

    @Test
    void testNextStage() {
        Stage nextStage = stage.getNextStage();
        Stage correctStage = new UTurn(true, true);
        assertEquals(correctStage.getClass(), nextStage.getClass());
    }

    @Test
    void testIsNotFinished() {
        assertFalse(stage.isFinished());
    }

    @Test
    void testIsNotLastStage() {
        assertFalse(stage.isLastStage());
    }

    // Simulates a scan response indicating the drone is off the island.
    private JSONObject scanResponse() {
        JSONObject response = new JSONObject();
        JSONArray biomes = new JSONArray();
        biomes.put("OCEAN"); // Indicates the drone is over the ocean.
        response.put("biomes", biomes);
        response.put("creeks", new JSONArray());
        response.put("sites", new JSONArray());
        return response;
    }
}
