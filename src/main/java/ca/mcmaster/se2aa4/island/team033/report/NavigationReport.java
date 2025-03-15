package ca.mcmaster.se2aa4.island.team033.report;

import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team033.map.Map;

public class NavigationReport implements Report {

    @Override
    public String generateReport(Map map) {
        StringBuilder report = new StringBuilder();
        report.append("Emergency Site ID: ").append(getIdWithDefault(map, this::getEmergencySiteID, "emergency site")).append("\n");
        report.append("Closest Creek ID: ").append(getIdWithDefault(map, this::getClosestCreekID, "creek")).append("\n");
        return report.toString();
    }

    // Retrieve the ID using the provided Map method and handle NoSuchElementException with a specific message for the item
    private String getIdWithDefault(Map map, IdRetriever idRetriever, String itemName) {
        try {
            return idRetriever.getId(map);
        } catch (NoSuchElementException e) {
            return "Could not locate " + itemName + ".";
        }
    }

    // Retrieve the ID of the closest creek
    private String getClosestCreekID(Map map) {
        return map.getClosestCreekID();
    }

    // Retrieve the ID of the emergency site
    private String getEmergencySiteID(Map map) {
        return map.getEmergencySiteID();
    }

    // Functional interface for retrieving IDs
    @FunctionalInterface
    private interface IdRetriever {
        String getId(Map map) throws NoSuchElementException;
    }
}
