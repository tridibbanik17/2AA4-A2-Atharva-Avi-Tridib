package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// BasicDrone is a concrete implementation of the Drone interface.
// It encapsulates the drone's state (battery level, heading direction, and location) and movement logic.
public class BasicDrone implements Drone {

    private Integer batteryLevel; // Current Battery level of the drone
    private Direction headingDirection; // Current heading direction
    private Coordinate location; // Current location

    public BasicDrone(Integer batteryLevel, Direction headingDirection) {
        this.batteryLevel = batteryLevel;
        this.headingDirection = headingDirection;
        this.location = new Coordinate(0, 0); // Initial position at origin
    }

    @Override
    public Integer getBatteryLevel() {
        return this.batteryLevel;
    }

    @Override
    public void drainBattery(Integer cost) {
        this.batteryLevel = this.batteryLevel - cost; // Reduces the battery level by a specified cost depending on the action
    }

    @Override
    public Direction getHeading() {
        return this.headingDirection;
    }

    @Override
    public Coordinate getLocation() {
        return new Coordinate(location.getX(), location.getY());
    }

    @Override
    public void moveForward() {
        // Move the drone forward by one unit in the current heading direction
        int deltaX = (headingDirection == Direction.EAST) ? 1 : (headingDirection == Direction.WEST) ? -1 : 0;
        int deltaY = (headingDirection == Direction.NORTH) ? 1 : (headingDirection == Direction.SOUTH) ? -1 : 0;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);
    }


    @Override
    public void turnRight() {
        // Rotate the drone 90 degrees to the right and updates the location accordingly
        int deltaX = (headingDirection == Direction.EAST || headingDirection == Direction.SOUTH) ? 1 : -1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.WEST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getRight();
    }


    @Override
    public void turnLeft() {
        // Rotate the drone 90 degrees to the left and updates the location accordingly
        int deltaX = (headingDirection == Direction.WEST || headingDirection == Direction.NORTH) ? -1 : 1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.EAST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getLeft();
    }

}
