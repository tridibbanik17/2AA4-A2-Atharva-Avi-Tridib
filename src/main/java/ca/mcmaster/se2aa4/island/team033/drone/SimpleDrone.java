package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// SimpleDrone is a concrete implementation of the Drone interface.
// It encapsulates the drone's state (battery level, heading direction, and location) and movement logic.
public class SimpleDrone implements Drone {

    private Integer batteryLevel;
    private Direction headingDirection;
    private final Coordinate location;

    public SimpleDrone(Integer batteryLevel, Direction headingDirection) {
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
        this.batteryLevel -= cost;
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
        switch (this.headingDirection) {
            case Direction.NORTH -> location.setY(location.getY() + 1);
            case Direction.EAST -> location.setX(location.getX() + 1);
            case Direction.SOUTH -> location.setY(location.getY() - 1);
            case Direction.WEST -> location.setX(location.getX() - 1);
            default -> throw new IllegalStateException();
        }
    }

    @Override
    public void turnRight() {
        // Update direction to the right and adjust the coordinates
        switch (this.headingDirection) {
            case Direction.NORTH -> {
                location.setY(location.getY() + 1);
                location.setX(location.getX() + 1);
            }
            case Direction.EAST -> {
                location.setY(location.getY() - 1);
                location.setX(location.getX() + 1);
            }
            case Direction.SOUTH -> {
                location.setY(location.getY() - 1);
                location.setX(location.getX() - 1);
            }
            case Direction.WEST -> {
                location.setY(location.getY() + 1);
                location.setX(location.getX() - 1);
            }
            default -> throw new IllegalStateException();
        }
        headingDirection = headingDirection.getRight();
    }

    @Override
    public void turnLeft() {
        // Update direction to the left and adjust the coordinates
        switch (this.headingDirection) {
            case Direction.NORTH -> {
                location.setY(location.getY() + 1);
                location.setX(location.getX() - 1);
            }
            case Direction.EAST -> {
                location.setY(location.getY() + 1);
                location.setX(location.getX() + 1);
            }
            case Direction.SOUTH -> {
                location.setY(location.getY() - 1);
                location.setX(location.getX() + 1);
            }
            case Direction.WEST -> {
                location.setY(location.getY() - 1);
                location.setX(location.getX() - 1);
            }
            default -> throw new IllegalStateException();
        }
        headingDirection = headingDirection.getLeft();
    }
}