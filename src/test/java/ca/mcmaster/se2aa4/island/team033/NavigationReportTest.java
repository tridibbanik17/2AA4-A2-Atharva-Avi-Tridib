package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.map.ListMap;
import ca.mcmaster.se2aa4.island.team033.map.Map;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterest;
import ca.mcmaster.se2aa4.island.team033.map.PointOfInterestType;
import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.report.NavigationReport;
import ca.mcmaster.se2aa4.island.team033.report.Report;

public class NavigationReportTest {
    private Map map;
    private Report report; 

    @BeforeEach
    public void setUp() {
        map = new ListMap();
    }

    @Test
    public void testGenerateReport() {
        map.addPointOfInterest(new PointOfInterest("site", PointOfInterestType.EMERGENCY_SITE, new Coordinate(0, 0)));
        map.addPointOfInterest(new PointOfInterest("creek", PointOfInterestType.CREEK, new Coordinate(0, 0)));
        report = new NavigationReport();
        String result = "Emergency Site ID: site\nClosest Creek ID: creek\n";
        assertEquals(result, report.generateReport(map)); 
    }

    @Test
    public void testGenerateReportNull() {
        report = new NavigationReport();
        String result = "Emergency Site ID: Could not locate emergency site.\nClosest Creek ID: Could not locate creek.\n";
        assertEquals(result, report.generateReport(map)); 
    }
}