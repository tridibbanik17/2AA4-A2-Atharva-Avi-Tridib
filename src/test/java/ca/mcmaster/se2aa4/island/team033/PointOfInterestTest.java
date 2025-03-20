package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.map.PointOfInterest;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterestType;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class PointOfInterestTest {
    private final PointOfInterest poi = new PointOfInterest("1234567890", PointOfInterestType.CREEK, new Coordinate(7, 19));

    @Test
    public void testGetKind() {
        assertEquals(PointOfInterestType.CREEK, poi.getType());
    }

    @Test
    public void testGetLocation() {
        Coordinate coord = new Coordinate(7, 19);
        assertEquals(coord.getX(), poi.getLocation().getX());
        assertEquals(coord.getY(), poi.getLocation().getY());
    }

    @Test
    public void testGetID() {
        assertEquals("1234567890", poi.getId());
    }
}