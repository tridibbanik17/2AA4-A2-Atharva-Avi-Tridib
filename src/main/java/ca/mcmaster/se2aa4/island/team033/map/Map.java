package ca.mcmaster.se2aa4.island.team033.map;

// Defines the contract for managing points of interest on a map.
// This interface abstracts the implementation details of how points of interest are stored and retrieved.
public interface Map {
    void addPointOfInterest(PointOfInterest poi); // Adds a point of interest to the map.
    String getEmergencySiteID(); // Retrieves the ID of the emergency site.
    String getClosestCreekID(); // Retrieves the ID of the closest creek to the emergency site.
}
