package ca.mcmaster.se2aa4.island.team033.position;

public enum Direction {
    NORTH("N"), EAST("E"), SOUTH("S"), WEST("W");

    private final String symbol;

    Direction(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public Direction getRight() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Direction getLeft() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    public static Direction fromSymbol(String s) {
        for (Direction dir : values()) {
            if (dir.symbol.equals(s)) {
                return dir;
            }
        }
        throw new IllegalArgumentException("Invalid direction symbol: " + s);
    }
}