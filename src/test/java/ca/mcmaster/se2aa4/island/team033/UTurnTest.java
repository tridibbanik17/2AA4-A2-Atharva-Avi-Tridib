package ca.mcmaster.se2aa4.island.team033;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.drone.BasicDrone;
import ca.mcmaster.se2aa4.island.team033.drone.Drone;
import ca.mcmaster.se2aa4.island.team033.drone.DroneController;
import ca.mcmaster.se2aa4.island.team033.position.Direction;
import ca.mcmaster.se2aa4.island.team033.stage.UTurn;


public class UTurnTest {
    private final Integer battery = 7000;
    private final Direction dir = Direction.NORTH;
    private Drone drone;
    private DroneController controller;
    private UTurn testVar;

    // If current heading direction is NORTH, for the left turnLeftTest(), the following sequence of steps is followed:
    // 1. Change direction to WEST, 2. Fly one unit forward, 3. Change to SOUTH, 4. Change to EAST, 5. Change to SOUTH.
    private final List<String> leftTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}"
    );

    // If current heading direction is NORTH, for the right turnLeftTest(), the following sequence of steps is followed:
    // 1. Change direction to WEST, 2. Change to NORTH, 3. Change to EAST, 4. Fly one unit forward, 5. Change to SOUTH.
    private final List<String> rightTurnSequence = List.of(
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"W\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"N\"}}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"E\"}}",
        "{\"action\":\"fly\"}",
        "{\"action\":\"heading\",\"parameters\":{\"direction\":\"S\"}}"
    );


    @BeforeEach
    public void setup() {
        drone = new BasicDrone(battery, dir);
        controller = new DroneController(drone);
    }

    private void runSequence(List<String> sequence) {
        String command;
        Iterator<String> expected = sequence.iterator();
        while (!testVar.isFinished() || expected.hasNext()) {
            command = testVar.getDroneCommand(controller, drone.getHeading());
            assertEquals(command, expected.next());
        }
    }

    @Test
    public void turnLeftTest() {
        testVar = new UTurn(true, false);
        runSequence(leftTurnSequence);
    }

    @Test
    public void turnRightTest() {
        testVar = new UTurn(false, true);
        runSequence(rightTurnSequence);
    }
}