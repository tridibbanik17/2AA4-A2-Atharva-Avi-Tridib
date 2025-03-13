package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Define the operations for the drone state and the actions.
public interface Drone {

    Integer getBatteryLevel(); // get current battery level
    void drainBattery(Integer cost); // drain battery by a specific cost
    Direction getMovingDirection(); // get current heading direction
    Coordinate getLocation(); // Get current location
    void moveForward(); // Move forward by one unit in current direction
    void turnRight(); // Turn right and adjust direction
    void turnLeft(); // Turn left and adjust direction
}