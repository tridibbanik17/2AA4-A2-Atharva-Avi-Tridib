package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class BasicDroneTest {
    private final Integer battery = 10000;
    private final Integer cost = 5;
    private final Direction dir = Direction.EAST;
    private final Coordinate coord = new Coordinate(0, 0);
    private final Coordinate coord1 = new Coordinate(1, 0);
    private final Coordinate coord2 = new Coordinate(1, -1);
    private final Coordinate coord3 = new Coordinate(1, 1);
    private Drone drone;

    @BeforeEach
    public void setUp() {
        drone = new BasicDrone(battery, dir);
    }

    @Test
    public void testGetHeading() {
        assertEquals(Direction.EAST, drone.getHeading());
    }

    @Test
    public void testGetBateryLevel() {
        assertEquals(battery, drone.getBatteryLevel());
    }

    @Test
    public void testDrainBattery() {
        drone.drainBattery(cost);
        assertEquals(battery - cost, drone.getBatteryLevel());
    }

    @Test
    public void testGetLocation() {
        Coordinate droneCoord = drone.getLocation();
        assertEquals(droneCoord.getX(), coord.getX());
        assertEquals(droneCoord.getY(), coord.getY());
    }

    @Test
    public void testFlyForward() {
        drone.moveForward();
        assertEquals(dir, drone.getHeading());
        Coordinate droneCoord = drone.getLocation();
        assertEquals(coord1.getX(), droneCoord.getX());
        assertEquals(coord1.getY(), droneCoord.getY());
    }

    @Test
    public void testTurnRight() {
        drone.turnRight();
        assertEquals(Direction.SOUTH, drone.getHeading());
        Coordinate droneCoord = drone.getLocation();
        assertEquals(droneCoord.getX(), coord2.getX());
        assertEquals(droneCoord.getY(), coord2.getY());
    }

    @Test
    public void testTurnLeft() {
        drone.turnLeft();
        assertEquals(Direction.NORTH, drone.getHeading());
        Coordinate droneCoord = drone.getLocation();
        assertEquals(droneCoord.getX(), coord3.getY());
    }
}