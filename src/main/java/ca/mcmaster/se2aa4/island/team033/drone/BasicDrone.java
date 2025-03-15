package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// BasicDrone is a concrete implementation of the Drone interface.
// It encapsulates the drone's state (battery level, heading direction, and location) and movement logic.
public class BasicDrone implements Drone {

    private Integer batteryLevel;
    private Direction headingDirection;
    private final Coordinate location;

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
        this.batteryLevel = this.batteryLevel - cost;
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
        int deltaX = (headingDirection == Direction.EAST) ? 1 : (headingDirection == Direction.WEST) ? -1 : 0;
        int deltaY = (headingDirection == Direction.NORTH) ? 1 : (headingDirection == Direction.SOUTH) ? -1 : 0;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);
    }


    @Override
    public void turnRight() {
        // Update direction to the right and adjust the coordinates
        int deltaX = (headingDirection == Direction.EAST || headingDirection == Direction.SOUTH) ? 1 : -1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.WEST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getRight();
    }


    @Override
    public void turnLeft() {
        // Update direction to the left and adjust the coordinates
        int deltaX = (headingDirection == Direction.WEST || headingDirection == Direction.NORTH) ? -1 : 1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.EAST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getLeft();
    }

}