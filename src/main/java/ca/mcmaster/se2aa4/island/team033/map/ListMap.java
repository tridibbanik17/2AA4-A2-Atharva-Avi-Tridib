package ca.mcmaster.se2aa4.island.team033.map;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class ListMap implements Map {
    private final List<PointOfInterest> creeks = new ArrayList<>();
    private PointOfInterest emergencySite;

    @Override
    public void addPointOfInterest(PointOfInterest poi) {
        if (null == poi.getType()) {
            throw new IllegalArgumentException("Invalid PointOfInterest type");
        } else switch (poi.getType()) {
            case CREEK -> creeks.add(poi);
            case EMERGENCY_SITE -> emergencySite = poi;
            default -> throw new IllegalArgumentException("Invalid PointOfInterest type");
        }
    }

    @Override
    public String getEmergencySiteID() {
        if (emergencySite == null) {
            throw new NoSuchElementException("Cannot find emergency site: missing data");
        }
        return emergencySite.getID();
    }

    @Override
    public String getClosestCreekID() {
        if (creeks.isEmpty()) {
            throw new NoSuchElementException("No creeks available");
        }

        Coordinate referenceLocation = emergencySite != null ? emergencySite.getLocation() : new Coordinate(0, 0);
        PointOfInterest closestCreek = creeks.get(0);
        double minDistance = referenceLocation.distanceTo(closestCreek.getLocation());

        for (PointOfInterest creek : creeks) {
            double distance = referenceLocation.distanceTo(creek.getLocation());
            if (distance < minDistance) {
                minDistance = distance;
                closestCreek = creek;
            }
        }
        return closestCreek.getID();
    }
}