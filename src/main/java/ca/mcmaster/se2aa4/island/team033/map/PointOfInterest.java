package ca.mcmaster.se2aa4.island.team033.map;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

// Represents a point of interest with a unique ID, type, and location.
// This class is a domain model and represents a real-world concept.
public class PointOfInterest {

    private final String id;     // Unique identifier for the point of interest.
    private final PointOfInterestType type; // Type of the point of interest.
    private final Coordinate location; // Location of the point of interest.

    // Constructor to initialize the point of interest with its attributes.
    public PointOfInterest(String id, PointOfInterestType type, Coordinate location) {
        this.id = id;
        this.type = type;
        this.location = location;
    }

    // Getters for immutable attributes, following encapsulation principles.
    public String getId() {
        return id;
    }

    public PointOfInterestType getType() {
        return type;
    }

    public Coordinate getLocation() {
        return location;
    }
}
