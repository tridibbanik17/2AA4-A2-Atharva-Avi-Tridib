package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class DirectionTest {
    private final Direction dirEast = Direction.EAST;
    private final Direction dirSouth = Direction.SOUTH;

    @Test
    public void testGetRight() {
        assertEquals(Direction.SOUTH, dirEast.getRight());
        assertEquals(Direction.WEST, dirSouth.getRight());
    }

    @Test
    public void testGetLeft() {
        assertEquals(Direction.NORTH, dirEast.getLeft());
        assertEquals(Direction.EAST, dirSouth.getLeft());
    }

    @Test
    public void testGetSymbol() {
        assertEquals("E", dirEast.getSymbol());
        assertEquals("S", dirSouth.getSymbol());
    }

    @Test
    public void testToSymbol() {
        assertEquals(Direction.NORTH, Direction.fromSymbol("N"));
        assertEquals(Direction.EAST, Direction.fromSymbol("E"));
        assertEquals(Direction.SOUTH, Direction.fromSymbol("S"));
        assertEquals(Direction.WEST, Direction.fromSymbol("W"));
    }
}