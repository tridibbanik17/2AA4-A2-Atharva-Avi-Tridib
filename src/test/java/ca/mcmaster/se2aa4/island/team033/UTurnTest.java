package ca.mcmaster.se2aa4.island.team033;

import java.util.List;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.stage.UTurn;
import ca.mcmaster.se2aa4.island.team033.position.Direction;


public class UTurnTest {
    private final Integer battery = 7000;
    private Direction dir = Direction.EAST;
    private Drone drone;
    private DroneController controller;
    private UTurn p1;

    private List<String> leftTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}"
    );

    private List<String> rightTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}"
    );

    private List<String> inwardLeftTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}"
    );
}