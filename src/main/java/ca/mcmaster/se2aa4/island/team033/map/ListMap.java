package ca.mcmaster.se2aa4.island.team033.map;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;

public class ListMap implements Map {
    private final List<PointOfInterest> creeks = new ArrayList<>(); // Stores creeks
    private PointOfInterest emergencySite; // Stores emergency site

    @Override
    public void addPointOfInterest(PointOfInterest poi) {
        validatePointOfInterest(poi);
        addPointOfInterestByType(poi);
    }

    private void validatePointOfInterest(PointOfInterest poi) {
        if (poi.getType() == null) {
            throw new IllegalArgumentException("Invalid PointOfInterest type");
        }
    }

    private void addPointOfInterestByType(PointOfInterest poi) {
        switch (poi.getType()) {
            case CREEK -> creeks.add(poi);
            case EMERGENCY_SITE -> emergencySite = poi;
            default -> throw new IllegalArgumentException("Unsupported PointOfInterest type: " + poi.getType());
        }
    }

    @Override
    public String getEmergencySiteID() {
        return Optional.ofNullable(emergencySite)
                .map(PointOfInterest::getId)
                .orElseThrow(() -> new NoSuchElementException("No emergency site found"));
    }

    @Override
    public String getClosestCreekID() {
        if (creeks.isEmpty()) {
            throw new NoSuchElementException("No creeks found");
        }

        Coordinate referenceLocation = getReferenceLocation();
        return findClosestCreek(referenceLocation).getId();
    }

    private Coordinate getReferenceLocation() {
        return Optional.ofNullable(emergencySite)
                .map(PointOfInterest::getLocation)
                .orElse(new Coordinate(0, 0));
    }

    private PointOfInterest findClosestCreek(Coordinate referenceLocation) {
        return creeks.stream()
                .min((c1, c2) -> Double.compare(
                        referenceLocation.distanceTo(c1.getLocation()),
                        referenceLocation.distanceTo(c2.getLocation())
                ))
                .orElseThrow(); // This won't happen as we already checked for empty creeks
    }
}