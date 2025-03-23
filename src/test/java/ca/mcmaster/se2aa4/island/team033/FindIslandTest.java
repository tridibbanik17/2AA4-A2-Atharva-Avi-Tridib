package ca.mcmaster.se2aa4.island.team033;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.stage.FindIsland;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;
import ca.mcmaster.se2aa4.island.team033.stage.ScanLine;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class FindIslandTest {
    private final Integer battery = 7000;
    private Direction dir = Direction.EAST;
    private Drone drone;
    private DroneController controller;
    private FindIsland test_var;

    private final String echoLeft = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
    private final String echoRight = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}";
    private final String turn = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}";
    private final String fly = "{\"action\":\"fly\"}";

    @BeforeEach
    public void setup() {
        drone = new BasicDrone(battery, dir);
        controller = new DroneController(drone);
        test_var = new FindIsland();
    }

    @Test
    public void testStateFly() {
        assertEquals(fly, test_var.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testEchoLeft() {
        test_var.getDroneCommand(controller, drone.getHeading());
        test_var.processInfo(null);
        
        assertEquals(echoLeft, test_var.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testNextPhase() {
        Stage nextPhase = test_var.getNextStage();
        Stage correctPhase = new ScanLine(true);
        assertEquals(correctPhase.getClass(), nextPhase.getClass());
    }

    @Test
    public void testFinished() {
        assertEquals(false, test_var.isFinished());
    }

    @Test
    public void testLastPhase() {
        assertEquals(false, test_var.isLastStage());
    }

    private JSONObject echoResponse(int range, String found) {
        JSONObject response = new JSONObject();
        response.put("range", range);
        response.put("found",found);
        return response;
    }
}