package ca.mcmaster.se2aa4.island.team033.map;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class ListMap implements Map {
    private final List<PointOfInterest> creeks = new ArrayList<>(); // Stores creeks
    private PointOfInterest emergencySite; // Stores emergency site

    @Override
    public void addPointOfInterest(PointOfInterest poi) {
        // Check if the provided PointOfInterest has a valid type
        if (poi.getType() == null) {
            throw new IllegalArgumentException("Invalid PointOfInterest type");
        } else switch (poi.getType()) {         // Add the PointOfInterest to the appropriate list
            case CREEK -> creeks.add(poi);
            case EMERGENCY_SITE -> emergencySite = poi;
            default -> throw new IllegalArgumentException("Invalid PointOfInterest type");
        }
    }

    // Returns the ID of the emergency site, or throws an exception if not found
    @Override
    public String getEmergencySiteID() {
        if (emergencySite == null) {
            throw new NoSuchElementException("Cannot find emergency site: missing data");
        }
        return emergencySite.getId();
    }

    // Finds and returns the ID of the closest creek to the emergency site
    @Override
    public String getClosestCreekID() {
        if (creeks.isEmpty()) {
            throw new NoSuchElementException("No creeks available");
        }

        Coordinate referenceLocation = emergencySite != null ? emergencySite.getLocation() : new Coordinate(0, 0);
        PointOfInterest closestCreek = creeks.get(0);
        double minDistance = referenceLocation.distanceTo(closestCreek.getLocation());

        // Iterate through creeks to find the closest one
        for (PointOfInterest creek : creeks) {
            double distance = referenceLocation.distanceTo(creek.getLocation());
            if (distance < minDistance) {
                minDistance = distance;
                closestCreek = creek;
            }
        }
        return closestCreek.getId();
    }
}