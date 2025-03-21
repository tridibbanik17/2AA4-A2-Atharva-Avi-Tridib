package ca.mcmaster.se2aa4.island.team033.drone;

import ca.mcmaster.se2aa4.island.team033.position.Coordinate;
import ca.mcmaster.se2aa4.island.team033.position.Direction;

// BasicDrone is a concrete implementation of the Drone interface.
// It encapsulates the drone's state (battery level, heading direction, and location) and movement logic.
public class BasicDrone implements Drone {

    private Integer batteryLevel; // Current Battery level of the drone
    private Direction headingDirection; // Current heading direction
    private Coordinate location; // Current location

    public BasicDrone(Integer batteryLevel, Direction headingDirection) {
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
        this.batteryLevel = this.batteryLevel - cost; // Reduces the battery level by a specified cost depending on the action
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
        // Move the drone forward by one unit in the current heading direction
        int deltaX = (headingDirection == Direction.EAST) ? 1 : (headingDirection == Direction.WEST) ? -1 : 0;
        int deltaY = (headingDirection == Direction.NORTH) ? 1 : (headingDirection == Direction.SOUTH) ? -1 : 0;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);
    }


    @Override
    public void turnRight() {
        // Rotate the drone 90 degrees to the right and updates the location accordingly
        // For example, if the drone's current heading direction is NORTH,
        // the drone will move 1 unit in NORTH and 1 unit in EAST. Also, the next heading direction will be EAST. 
        int deltaX = (headingDirection == Direction.EAST || headingDirection == Direction.SOUTH) ? 1 : -1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.WEST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getRight();
    }

    //Make Uturn right
    @Override
    public void MakeUTurnRight() {
        int[][] moves = {
            {1, 1, -1, 1},  // Move 1: Turn right
            {1, -1, -1, -1}, // Move 2: Turn left
            {1, 1, -1, 1},  // Move 3: Turn right
            {1, 0, -1, 0},  // Move 4: Move forward
            {1, 1, -1, 1},  // Move 5: Turn right
            {1, 0, -1, 0}   // Move 6: Move forward
        };

        Direction[] turnSequence = {
            headingDirection.getRight(),
            headingDirection.getLeft(),
            headingDirection.getRight(),
            headingDirection.getStraight(),
            headingDirection.getRight(),
            headingDirection.getStraight()
        };

        for (int i = 0; i < moves.length; i++) {
            if (headingDirection == Direction.WEST) {
                location.setX(location.getX() + moves[i][0]);
                location.setY(location.getY() + moves[i][1]);
            } else if (headingDirection == Direction.EAST) {
                location.setX(location.getX() + moves[i][2]);
                location.setY(location.getY() + moves[i][3]);
            } else if (headingDirection == Direction.NORTH) {
                location.setX(location.getX() + moves[i][2]);
                location.setY(location.getY() + moves[i][0]);
            } else if (headingDirection == Direction.SOUTH) {
                location.setX(location.getX() + moves[i][0]);
                location.setY(location.getY() + moves[i][2]);
            }

            headingDirection = turnSequence[i];
        }
    }


    // // Turn right
    // @Override 
    // public void MakeUTurnRightFirstMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getRight();
    // }

    // // Turn left
    // public void MakeUTurnRightSecondMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getLeft();
    // }

    // // Turn right
    // public void MakeUTurnRighttThirdMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getRight();
    // }

    // // Move forward
    // public void MakeUTurnRightFourthMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getStraight();
    // }

    // // Turn right
    // public void MakeUTurnRightFifthMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getRight();
    // }

    // // Move forward
    // public void MakeUTurnRightSixthMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getStraight();
    // }

    @Override
    public void turnLeft() {
        // Rotate the drone 90 degrees to the left and updates the location accordingly
        // For example, if the drone's current heading direction is NORTH,
        // the drone will move 1 unit in NORTH and 1 unit in WEST. Also, the next heading direction will be WEST.
        int deltaX = (headingDirection == Direction.WEST || headingDirection == Direction.NORTH) ? -1 : 1;
        int deltaY = (headingDirection == Direction.NORTH || headingDirection == Direction.EAST) ? 1 : -1;

        location.setX(location.getX() + deltaX);
        location.setY(location.getY() + deltaY);

        headingDirection = headingDirection.getLeft();
    }

    // Turn left
    // @Override 
    // public void MakeUTurnLeftFirstMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getLeft();
 
    // }

    // // Turn right
    // public void MakeUTurnRightSecondMove() {
    //    if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getRight();
    // }

    // // Turn left
    // public void MakeUTurnRighttThirdMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getLeft();
    // }

    // // Move forward
    // public void MakeUTurnRightFourthMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getStraight();
    // }

    // // Turn left
    // public void MakeUTurnRightFifthMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getLeft();
    // }

    // // Move forward
    // public void MakeUTurnRightSixthMove() {
    //     if (headingDirection == Direction.WEST) {
    //         location.setX(location.getX() - 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.EAST) {
    //         location.setX(location.getX() + 1);
    //         location.setY(location.getY());
    //     }

    //     else if (headingDirection == Direction.NORTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() + 1);
    //     }

    //     else if (headingDirection == Direction.SOUTH) {
    //         location.setX(location.getX());
    //         location.setY(location.getY() - 1);
    //     }

    //     headingDirection = headingDirection.getStraight();
    // }

    @Override
    public void MakeUTurnLeft() {
        int[][] moves = {
            {-1, -1, 1, 1},  // Move 1: Turn left
            {-1, 1, 1, -1},  // Move 2: Turn right
            {-1, -1, 1, 1},  // Move 3: Turn left
            {-1, 0, 1, 0},   // Move 4: Move forward
            {-1, -1, 1, 1},  // Move 5: Turn left
            {-1, 0, 1, 0}    // Move 6: Move forward
        };

        Direction[] turnSequence = {
            headingDirection.getLeft(),
            headingDirection.getRight(),
            headingDirection.getLeft(),
            headingDirection.getStraight(),
            headingDirection.getLeft(),
            headingDirection.getStraight()
        };

        for (int i = 0; i < moves.length; i++) {
            if (headingDirection == Direction.WEST) {
                location.setX(location.getX() + moves[i][0]);
                location.setY(location.getY() + moves[i][1]);
            } else if (headingDirection == Direction.EAST) {
                location.setX(location.getX() + moves[i][2]);
                location.setY(location.getY() + moves[i][3]);
            } else if (headingDirection == Direction.NORTH) {
                location.setX(location.getX() + moves[i][2]);
                location.setY(location.getY() + moves[i][0]);
            } else if (headingDirection == Direction.SOUTH) {
                location.setX(location.getX() + moves[i][0]);
                location.setY(location.getY() + moves[i][2]);
            }
            
            headingDirection = turnSequence[i];
        }
    }

}
