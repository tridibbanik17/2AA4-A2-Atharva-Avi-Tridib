package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Define the behaviours for interacting with the drone.
public interface Controller {

    String flyCommand(); // Command to fly forward
    String headingCommand(Direction dir); // Command to change the heading direction
    String echoCommand(Direction dir); // Command to echo the environment in a specific direction
    String scanCommand(); // Command to scan the surroundings
    String stopCommand(); // Command to stop
}