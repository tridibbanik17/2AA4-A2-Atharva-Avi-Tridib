package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// BasicDrone is a concrete implementation of the Drone interface.
// Encapsulates the drone's state (battery level, heading direction, and location) and movement logic
public class BasicDrone implements Drone {

    private Integer batteryLevel; // Current Battery level of the drone
    private Direction headingDirection; // Current heading direction
    private final Coordinate location; // Current location

    // Initializes drone with battery level, heading, and starting position at origin
    public BasicDrone(Integer batteryLevel, Direction headingDirection) {
        this.batteryLevel = batteryLevel;
        this.headingDirection = headingDirection;
        this.location = new Coordinate(0, 0); // Initial position at origin
    }

    @Override
    public int getBatteryLevel() {
        return this.batteryLevel; // Returns current battery level
    }

    @Override
    public void drainBattery(int cost) {
        this.batteryLevel = this.batteryLevel - cost; // Reduces the battery level by a specified cost depending on the action
    }

    @Override
    public Direction getHeading() {
        return this.headingDirection; // Returns current heading direction
    } 

    @Override
    public Coordinate getLocation() {
        return new Coordinate(location.getX(), location.getY()); // Returns defensive copy of location
    } 

    @Override
    public void moveForward() {
        // Moves the drone forward by one unit in the current heading direction
        int deltaX = (headingDirection == Direction.EAST) ? 1 : (headingDirection == Direction.WEST) ? -1 : 0;
        int deltaY = (headingDirection == Direction.NORTH) ? 1 : (headingDirection == Direction.SOUTH) ? -1 : 0;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);
    }


    @Override
    public void turnRight() {
        // Rotates the drone 90 degrees to the right and updates the location accordingly
        // For example, if the drone's current heading direction is NORTH,
        // the drone will move 1 unit in NORTH and 1 unit in EAST. Also, the next heading direction will be EAST. 
        int deltaX = (headingDirection == Direction.EAST || headingDirection == Direction.SOUTH) ? 1 : -1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.WEST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getRight();
    }

    @Override
    public void turnLeft() {
        // Rotates the drone 90 degrees to the left and updates the location accordingly
        // For example, if the drone's current heading direction is NORTH,
        // the drone will move 1 unit in NORTH and 1 unit in WEST. Also, the next heading direction will be WEST.
        int deltaX = (headingDirection == Direction.WEST || headingDirection == Direction.NORTH) ? -1 : 1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.EAST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getLeft();
    }

}
