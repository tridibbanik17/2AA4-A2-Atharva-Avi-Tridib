package ca.mcmaster.se2aa4.island.team033.map;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

// ListMap is a pure fabrication that implements the Map interface.
// It manages points of interest (creeks and emergency sites) using a list-based structure.
// This class does not represent a real-world map but is designed to handle specific responsibilities efficiently.
public class ListMap implements Map {

    // Stores creeks as points of interest.
    private final List<PointOfInterest> creeks = new ArrayList<>();

    // Stores the emergency site as a point of interest.
    private PointOfInterest emergencySite;

    @Override
    // Adds a point of interest to the map after validation.
    public void addPointOfInterest(PointOfInterest poi) {
        validatePointOfInterest(poi); // Ensures the point of interest is valid.
        addPointOfInterestByType(poi); // Adds the point of interest based on its type.
    }

    // Validates the point of interest to ensure it has a valid type.
    private void validatePointOfInterest(PointOfInterest poi) {
        if (poi.getType() == null) {
            throw new IllegalArgumentException("Invalid PointOfInterest type");
        }
    }

    // Adds the point of interest to the appropriate collection based on its type.
    private void addPointOfInterestByType(PointOfInterest poi) {
        switch (poi.getType()) {
            case CREEK -> creeks.add(poi); // Adds creek to the creeks list.
            case EMERGENCY_SITE -> emergencySite = poi; // Sets the emergency site.
            default -> throw new IllegalArgumentException("Unsupported PointOfInterest type: " + poi.getType());
        }
    }

    @Override
    // Retrieves the ID of the emergency site, throwing an exception if not found.
    public String getEmergencySiteID() {
        return Optional.ofNullable(emergencySite)
                .map(PointOfInterest::getId)
                .orElseThrow(() -> new NoSuchElementException("No emergency site found"));
    }

    @Override
    // Retrieves the ID of the closest creek to the emergency site, throwing an exception if no creeks are found.
    public String getClosestCreekID() {
        if (creeks.isEmpty()) {
            throw new NoSuchElementException("No creeks found");
        }

        Coordinate referenceLocation = getReferenceLocation(); // Gets the reference location for distance calculation.
        return findClosestCreek(referenceLocation).getId(); // Finds and returns the closest creek's ID.
    }

    // Determines the reference location for distance calculations, defaulting to (0, 0) if no emergency site exists.
    private Coordinate getReferenceLocation() {
        return Optional.ofNullable(emergencySite)
                .map(PointOfInterest::getLocation)
                .orElse(new Coordinate(0, 0));
    }

    // Finds the closest creek to the reference location using Java Streams and a comparator.
    private PointOfInterest findClosestCreek(Coordinate referenceLocation) {
        return creeks.stream()
                .min((c1, c2) -> Double.compare(
                        referenceLocation.distanceTo(c1.getLocation()),
                        referenceLocation.distanceTo(c2.getLocation())
                ))
                .orElseThrow(); // This won't happen as we already checked for empty creeks.
    }
}
