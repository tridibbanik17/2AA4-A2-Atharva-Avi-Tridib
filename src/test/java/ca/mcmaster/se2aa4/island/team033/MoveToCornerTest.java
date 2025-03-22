package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.stage.FindIsland;
import ca.mcmaster.se2aa4.island.team033.stage.MoveToCorner;
import ca.mcmaster.se2aa4.island.team033.stage.Stage;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class MoveToCornerTest {
    private Drone drone;
    private Controller controller;
    private Stage stage;
    private final String echoLeft = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"N\"}}";
    private final String echoRight = "{\"action\":\"echo\",\"parameters\":{\"direction\":\"S\"}}";
    private final String turn = "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}";
    private final String fly = "{\"action\":\"fly\"}";



    @BeforeEach
    public void setUp() {
        this.drone = new BasicDrone(7000, Direction.EAST);
        this.controller = new DroneController(drone);
        this.stage = new MoveToCorner();
    }

    @Test
    public void testEchoLeftState() {
        assertEquals(echoLeft, stage.getDroneCommand(controller, drone.getHeading()));
    }

    @Test
    public void testNextPhase() {
        Stage nextPhase = stage.getNextStage();
        Stage correctPhase = new FindIsland();
        assertEquals(correctPhase.getClass(), nextPhase.getClass());
    }

    @Test
    public void testFinished() {
        assertEquals(false, stage.isFinished());
    }

    @Test
    public void testLastPhase() {
        assertEquals(false, stage.isLastPhase());
    }
}