package ca.mcmaster.se2aa4.island.team033;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.island.team033.position.Direction;

public class DirectionTest {
    private final Direction dir1 = Direction.EAST;
    private final Direction dir2 = Direction.SOUTH;

    @Test
    public void testGetRight() {
        assertEquals(Direction.SOUTH, dir1.getRight());
        assertEquals(Direction.WEST, dir2.getRight());
    }

    @Test
    public void testGetLeft() {
        assertEquals(Direction.NORTH, dir1.getLeft());
        assertEquals(Direction.EAST, dir2.getLeft());
    }

    @Test
    public void testGetSymbol() {
        assertEquals("E", dir1.getSymbol());
        assertEquals("S", dir2.getSymbol());
    }

    @Test
    public void testToSymbol() {
        assertEquals(Direction.NORTH, Direction.fromSymbol("N"));
        assertEquals(Direction.EAST, Direction.fromSymbol("E"));
        assertEquals(Direction.SOUTH, Direction.fromSymbol("S"));
        assertEquals(Direction.WEST, Direction.fromSymbol("W"));
    }
}