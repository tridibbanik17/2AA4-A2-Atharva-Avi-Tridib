package ca.mcmaster.se2aa4.island.team033.position;

// Enum representing the four cardinal directions
public enum Direction {
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

    private final String symbol;

    // Constructor to associate the direction with its symbol
    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    // Get the next direction in clockwise order
    public Direction getRight() {
        return values()[(ordinal() + 1) % values().length];
    }

    // Get the next direction in counterclockwise order
    public Direction getLeft() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    // Retrieve a Direction enum from its symbol
    public static Direction fromSymbol(String s) {
        for (Direction dir : values()) {
            if (dir.symbol.equals(s)) {
                return dir;
            }
        }
        throw new IllegalArgumentException("Invalid direction symbol: " + s);
    }
}