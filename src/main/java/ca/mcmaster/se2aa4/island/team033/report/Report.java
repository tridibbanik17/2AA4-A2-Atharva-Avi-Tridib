package ca.mcmaster.se2aa4.island.team033.report;

import ca.mcmaster.se2aa4.island.team033.map.Map;

// Defines the contract for generating reports based on a Map.
// This interface abstracts the implementation details of report generation.
public interface Report {
    // Generates a report as a String for the given Map.
    String generateReport(Map map);
}
