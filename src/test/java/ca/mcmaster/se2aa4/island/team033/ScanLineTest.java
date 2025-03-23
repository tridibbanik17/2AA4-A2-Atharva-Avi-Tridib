package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;
import ca.mcmaster.se2aa4.island.team033.stage.ScanLine;
import ca.mcmaster.se2aa4.island.team033.stage.UTurn;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class ScanLineTest {
    private Drone drone;
    private Controller controller;
    private Stage stage;
    private final String echo = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"E\"}}";
    private final String scan = "{\"action\":\"scan\"}";
    private final String fly = "{\"action\":\"fly\"}";

    @BeforeEach
    public void setUp() {
        this.drone = new BasicDrone(7000, Direction.EAST);
        this.controller = new DroneController(drone);
        this.stage = new ScanLine(true);
    }

    @Test
    public void testFly() {
        assertEquals(fly, stage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testScan() {
        stage.getDroneCommand(controller, drone.getHeading());
        stage.processInfo(null);
        assertEquals(scan, stage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testNextStage() {
        Stage nextStage = stage.getNextStage();
        Stage correctStage = new UTurn(true, true);
        assertEquals(correctStage.getClass(), nextStage.getClass());
    }

    @Test
    public void testFinished() {
        assertEquals(false, stage.isFinished());
    }

    @Test
    public void testLastStage() {
        assertEquals(false, stage.isLastStage());
    }

    private JSONObject scanResponse() {
        JSONObject response = new JSONObject();
        JSONArray biomes = new JSONArray(1);
        biomes.put("OCEAN");
        response.put("biomes", biomes);
        response.put("creeks", new JSONArray());
        response.put("sites", new JSONArray());
        return response;
    }
}