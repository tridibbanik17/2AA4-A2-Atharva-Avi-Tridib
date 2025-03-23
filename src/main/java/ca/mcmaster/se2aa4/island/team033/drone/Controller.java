package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

// Defines the command interface for interacting with the drone.
public interface Controller {
    String flyCommand(); // Executes fly command and returns response.
    String headingCommand(Direction dir); // Executes heading change command and returns response.
    String echoCommand(Direction dir); // Executes echo command and returns response.
    String scanCommand(); // Executes scan command and returns response.
    String stopCommand(); // Executes stop command and returns response.
}
