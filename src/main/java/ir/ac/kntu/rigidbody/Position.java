package ir.ac.kntu.rigidbody;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public Position sum(Vector v) {
        return new Position(this.x + v.getX(), this.y + v.getY());
    }

    public void move(Vector v) {
        this.x += v.getX();
        this.y += v.getY();
    }

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")";
    }
}