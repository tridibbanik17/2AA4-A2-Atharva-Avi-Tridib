package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Defines the core operations for managing drone state and actions.
public interface Drone {
    int getBatteryLevel(); // Returns current battery level.
    void drainBattery(int cost); // Reduces battery by specified cost.
    Direction getHeading(); // Returns current heading direction.
    Coordinate getLocation(); // Returns current location.
    void moveForward(); // Moves drone forward in current direction.
    void turnRight(); // Turns drone right, updating direction.
    void turnLeft(); // Turns drone left, updating direction.
}
