package ca.mcmaster.se2aa4.island.team033.position;

// Represents a 2D coordinate with mutable x and y values.
public class Coordinate {
    private int x; // X-coordinate.
    private int y; // Y-coordinate.

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Calculates the Euclidean distance to another coordinate.
    public double distanceTo(Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
