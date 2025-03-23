package ca.mcmaster.se2aa4.island.team033.position;

import java.util.HashMap;
import java.util.Map;

// Enum representing the four cardinal directions (Value Object pattern).
public enum Direction {
    NORTH("N"),
    EAST("E"),
    SOUTH("S"),
    WEST("W");

    // Lookup map for symbol-based direction retrieval.
    private static final Map<String, Direction> SYMBOL_MAP = new HashMap<>();

    static {
        for (Direction dir : values()) {
            SYMBOL_MAP.put(dir.symbol, dir);
        }
    }

    private final String symbol;

    // Associates a direction with its symbol.
    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    // Returns the next direction in clockwise order.
    public Direction getRight() {
        return values()[(ordinal() + 1) % values().length];
    }

    // Returns the next direction in counterclockwise order.
    public Direction getLeft() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    // Factory method to retrieve a Direction enum from its symbol.
    public static Direction fromSymbol(String symbol) {
        Direction dir = SYMBOL_MAP.get(symbol);
        if (dir == null) {
            throw new IllegalArgumentException("Invalid direction symbol: " + symbol);
        }
        return dir;
    }
}
