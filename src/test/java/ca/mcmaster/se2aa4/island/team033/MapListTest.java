package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.map.ListMap;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterest;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterestType;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class MapListTest {
    private ListMap map;
    private PointOfInterest creek1, creek2, creek3, creek4, creek5, site;

    @BeforeEach
    public void setup() {
        map = new ListMap();
        creek1 = new PointOfInterest("1", PointOfInterestType.CREEK, new Coordinate(0, 0));
        creek2 = new PointOfInterest("2", PointOfInterestType.CREEK, new Coordinate(0, 50));
        creek3 = new PointOfInterest("3", PointOfInterestType.CREEK, new Coordinate(50, 0));
        creek4 = new PointOfInterest("4", PointOfInterestType.CREEK, new Coordinate(50, 50));
        creek5 = new PointOfInterest("5", PointOfInterestType.CREEK, new Coordinate(15, 15));
        site = new PointOfInterest("site", PointOfInterestType.EMERGENCY_SITE, new Coordinate(25, 25));
    }

    @Test
    public void testGetClosetCreek() {
        map.addPointOfInterest(creek1);
        map.addPointOfInterest(creek2);
        map.addPointOfInterest(creek3);
        map.addPointOfInterest(creek4);
        map.addPointOfInterest(creek5);
        map.addPointOfInterest(site);
        assertEquals("5", map.getClosestCreekID());
    }

    @Test
    public void testGetEmergencySite(){
        map.addPointOfInterest(site);
        assertEquals("site", map.getEmergencySiteID());
    }
}