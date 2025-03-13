package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Define the behaviours for interacting with the drone.
public interface Controller {

    String fly(); // Command to fly forward
    String movingDirection(Direction dir); // Command to change the heading direction
    String echo(Direction dir); // Command to echo the environment in a specific direction
    String scan(); // Command to scan the surroundings
    String stop(); // Command to stop
}