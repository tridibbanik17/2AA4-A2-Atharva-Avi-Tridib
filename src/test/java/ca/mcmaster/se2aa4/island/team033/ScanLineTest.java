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
        this.drone = new BasicDrone(10000, Direction.EAST);
        this.controller = new DroneController(drone);
        this.stage = new ScanLine(true);
    }

    @Test
    public void testFly() {
        assertEquals(fly, stage.getDroneCommand(controller, drone.getHeading()));
    }
}