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
    private PointOfInterest creek1, creek2, creek3, creek4, creek5, creek6, creek7, creek8, creek9, creek10, site;

    @BeforeEach
    public void setup() {
        map = new ListMap();
        creek1 = new PointOfInterest("1", PointOfInterestType.CREEK, new Coordinate(7, 13));
        creek2 = new PointOfInterest("2", PointOfInterestType.CREEK, new Coordinate(2, 40));
        creek3 = new PointOfInterest("3", PointOfInterestType.CREEK, new Coordinate(40, 2));
        creek4 = new PointOfInterest("4", PointOfInterestType.CREEK, new Coordinate(35, 35));
        creek5 = new PointOfInterest("5", PointOfInterestType.CREEK, new Coordinate(17, 20));
        creek6 = new PointOfInterest("6", PointOfInterestType.CREEK, new Coordinate(69, 25));
        creek7 = new PointOfInterest("7", PointOfInterestType.CREEK, new Coordinate(34, -21));
        creek8 = new PointOfInterest("8", PointOfInterestType.CREEK, new Coordinate(-2, 13));
        creek9 = new PointOfInterest("9", PointOfInterestType.CREEK, new Coordinate(16, -45));
        creek10 = new PointOfInterest("10", PointOfInterestType.CREEK, new Coordinate(13, -20));
        site = new PointOfInterest("site", PointOfInterestType.EMERGENCY_SITE, new Coordinate(35, -17));
    }

    @Test
    public void testGetClosetCreek() {
        map.addPointOfInterest(creek1);
        map.addPointOfInterest(creek2);
        map.addPointOfInterest(creek3);
        map.addPointOfInterest(creek4);
        map.addPointOfInterest(creek5);
        map.addPointOfInterest(creek6);
        map.addPointOfInterest(creek7);
        map.addPointOfInterest(creek8);
        map.addPointOfInterest(creek9);
        map.addPointOfInterest(creek10);
        map.addPointOfInterest(site);
        assertEquals("7", map.getClosestCreekID());
    }

    @Test
    public void testGetEmergencySite(){
        map.addPointOfInterest(site);
        assertEquals("site", map.getEmergencySiteID());
    }
}