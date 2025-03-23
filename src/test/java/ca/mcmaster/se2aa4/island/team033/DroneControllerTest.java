package ca.mcmaster.se2aa4.island.team033;

import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.drone.Controller;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class DroneControllerTest {
    private Drone drone;
    private Controller controller;

    @BeforeEach
    public void setUp() {
        this.drone = new BasicDrone(7000, Direction.EAST);
        this.controller = new DroneController(drone);
    }

    @Test
    public void testFly() {
        JSONObject decision = new JSONObject();
        decision.put("action", "fly");

        assertEquals(decision.toString(), controller.flyCommand());

        Coordinate position = drone.getLocation();

        assertEquals(1, position.getX());
        assertEquals(0, position.getY());
        assertEquals(Direction.EAST, drone.getHeading());
    }

    @Test 
    public void testHeading() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put("action", "heading");
        params.put("direction", "S");
        decision.put("parameters", params);

        assertEquals(decision.toString(), controller.headingCommand(Direction.SOUTH));

        Coordinate position = drone.getLocation();

        assertEquals(1, position.getX());
        assertEquals(-1, position.getY());
        assertEquals(Direction.SOUTH, drone.getHeading());
    }

    @Test
    public void testEcho() {
        JSONObject decision = new JSONObject();
        JSONObject params = new JSONObject();

        decision.put("action", "echo");
        params.put("direction", "W");
        decision.put("parameters", params);

        assertEquals(decision.toString(), controller.echoCommand(Direction.WEST));
    }

    @Test
    public void testScan() {
        JSONObject decision = new JSONObject();
        decision.put("action", "scan");

        assertEquals(decision.toString(), controller.scanCommand());
    }

    @Test
    public void testStop() {
        JSONObject decision = new JSONObject();
        decision.put("action", "stop");

        assertEquals(decision.toString(), controller.stopCommand());
    }
}