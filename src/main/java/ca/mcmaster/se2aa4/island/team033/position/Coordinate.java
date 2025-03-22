package ca.mcmaster.se2aa4.island.team033.position;

public class Coordinate {
    private int x; // X-coordinate
    private int y; // Y-coordinate

    public Coordinate(int x, int y) {
        this.x = x; 
        this.y = y; 
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Calculates the Euclidean distance between this coordinate and another
    public double distanceTo(Coordinate other) {
        if (other == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }
        return Math.sqrt(Math.pow((double)other.getX() - this.x, 2) + Math.pow((double)other.getY() - this.y, 2));
    }
}