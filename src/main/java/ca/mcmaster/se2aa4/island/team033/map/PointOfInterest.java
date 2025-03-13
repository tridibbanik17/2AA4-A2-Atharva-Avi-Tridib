package ca.mcmaster.se2aa4.island.team033.map;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class PointOfInterest {

    private final String id;     // unique identifier for poi
    private final PointOfInterestType type; // type of poi
    private final Coordinate location; // location of poi

    public PointOfInterest(String id, PointOfInterestType type, Coordinate location) {
        this.id = id;
        this.type = type;
        this.location = location;
    }

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
