package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class CoordinateTest {
    private Coordinate origin;
    private Coordinate distantPoint;
    private Coordinate adjacentPoint;

    @BeforeEach
    public void setUp() {
        origin = new Coordinate(0, 0);
        distantPoint = new Coordinate(16, -37);
        adjacentPoint = new Coordinate(1, 0);
    }

    @Test
    public void testDistanceBetweenOriginAndDistantPoint() {
        assertEquals(Math.sqrt(1625), origin.distanceTo(distantPoint));
    }

    @Test
    public void testDistanceBetweenOriginAndAdjacentPoint() {
        assertNotEquals(0, origin.distanceTo(adjacentPoint));
    }

    @Test
    public void testGetXFromAdjacentPoint() {
        assertEquals(1, adjacentPoint.getX());
    }

    @Test
    public void testGetYFromDistantPoint() {
        assertEquals(-37, distantPoint.getY());
    }

    @Test
    public void testSetXForOrigin() {
        origin.setX(27);
        assertEquals(27, origin.getX());
    }

    @Test
    public void testSetYForOrigin() {
        origin.setY(-42);
        assertEquals(-42, origin.getY());
    }
}
