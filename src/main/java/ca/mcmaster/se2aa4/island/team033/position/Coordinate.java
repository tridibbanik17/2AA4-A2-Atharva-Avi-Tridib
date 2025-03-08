package ca.mcmaster.se2aa4.island.team033.position;

public class Coordinate {
    private int x;
    private int y;

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

    public double distanceTo(Coordinate other) {
        return Math.sqrt(Math.pow((double)other.getX() - this.x, 2) + Math.pow((double)other.getY() - this.y, 2));
    }
}