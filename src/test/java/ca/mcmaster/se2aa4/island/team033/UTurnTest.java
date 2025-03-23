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
    private UTurn test_var;

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

    @BeforeEach
    public void setup() {
        drone = new BasicDrone(battery, dir);
        controller = new DroneController(drone);
    }

    private void runSequence(List<String> sequence) {
        String command;
        Iterator<String> expected = sequence.iterator();
        while (!test_var.isFinished() || expected.hasNext()) {
            command = test_var.getDroneCommand(controller, drone.getHeading());
            assertEquals(command, expected.next());
        }
    }

    @Test
    public void turnRightTest() {
        test_var = new UTurn(false, true);
        runSequence(rightTurnSequence);
    }

    @Test
    public void turnInwardTest() {
        test_var = new UTurn(true, false);
        runSequence(inwardLeftTurnSequence);
    }
}