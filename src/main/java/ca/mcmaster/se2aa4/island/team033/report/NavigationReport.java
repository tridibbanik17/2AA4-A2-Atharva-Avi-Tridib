package ca.mcmaster.se2aa4.island.team033.report;

import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team033.map.Map;

// NavigationReport is a pure fabrication that implements the Report interface.
// It generates a navigation report based on the data from the Map.
// This class does not represent a real-world report but is designed to handle report generation efficiently.
public class NavigationReport implements Report {

    @Override
    // Generates a navigation report containing the emergency site ID and the closest creek ID.
    public String generateReport(Map map) {
        StringBuilder report = new StringBuilder();
        report.append("Emergency Site ID: ").append(getIdWithDefault(map, this::getEmergencySiteID, "emergency site")).append("\n");
        report.append("Closest Creek ID: ").append(getIdWithDefault(map, this::getClosestCreekID, "creek")).append("\n");
        return report.toString();
    }

    // Helper method to retrieve an ID using the provided Map method and handle exceptions with default messages.
    private String getIdWithDefault(Map map, IdRetriever idRetriever, String itemName) {
        try {
            return idRetriever.getId(map);
        } catch (NoSuchElementException e) {
            return "Could not locate " + itemName + ".";
        }
    }

    // Retrieves the ID of the closest creek from the Map.
    private String getClosestCreekID(Map map) {
        return map.getClosestCreekID();
    }

    // Retrieves the ID of the emergency site from the Map.
    private String getEmergencySiteID(Map map) {
        return map.getEmergencySiteID();
    }

    // Functional interface for retrieving IDs, improving code readability and modularity.
    @FunctionalInterface
    private interface IdRetriever {
        String getId(Map map) throws NoSuchElementException;
    }
}
