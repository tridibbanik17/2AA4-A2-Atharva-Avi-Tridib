package ca.mcmaster.se2aa4.island.team033.report;

import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team033.map.Map;

public class NavigationReport implements Report {

    // generateReport() method generates a navigation report as a formatted String based on the provided Map.
    @Override
    public String generateReport(Map map) {
        StringBuilder report = new StringBuilder();
        report.append("Emergency Site ID: ").append(emergencySiteID(map)).append("\n");
        report.append("Closest Creek ID: ").append(closestCreekID(map)).append("\n");
        return report.toString();
    }

    // Retrieve the id of the closest creek from the provided Map.
    // If no creek is found, return a default message.
    private String closestCreekID(Map map) {
        String creekID;

        try {
            creekID = map.getClosestCreekID();
        } catch (NoSuchElementException e) {
            creekID = "Could not locate a creek.";
        }

        return creekID;
    }

    // Retrieve the id of the emergency site from the provided Map.
    // If no emergency site is found, return a default message.
    private String emergencySiteID(Map map) {
        String emergencySiteID;

        try {
            emergencySiteID = map.getEmergencySiteID();
        } catch (NoSuchElementException e) {
            emergencySiteID = "Could not locate emergency site.";
        }

        return emergencySiteID;
    }
}