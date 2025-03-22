package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.map.PointOfInterest;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterestType;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class PointOfInterestTest {
    private final PointOfInterest poi1 = new PointOfInterest("01829792322", PointOfInterestType.EMERGENCY_SITE, new Coordinate(13, -16));
    private final PointOfInterest poi2 = new PointOfInterest("2#01715170", PointOfInterestType.CREEK, new Coordinate(19, -23));

    @Test
    public void testGetID() {
        assertEquals("01829792322", poi1.getId());
        assertEquals("2#01715170", poi2.getId());
    }

    @Test
    public void testGetKind() {
        assertEquals(PointOfInterestType.EMERGENCY_SITE, poi1.getType());
        assertEquals(PointOfInterestType.CREEK, poi2.getType());
    }

    @Test
    public void testGetLocation() {
        Coordinate coord1 = new Coordinate(13, -16);
        Coordinate coord2 = new Coordinate(19, -23);
        assertEquals(coord1.getX(), poi1.getLocation().getX());
        assertEquals(coord1.getY(), poi1.getLocation().getY());
        assertEquals(coord2.getX(), poi2.getLocation().getX());
        assertEquals(coord2.getY(), poi2.getLocation().getY());
    }
}