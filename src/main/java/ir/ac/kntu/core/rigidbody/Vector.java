package ir.ac.kntu.core.rigidbody;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vector(Position origin,Position dest) {
        this.x = dest.getX()-origin.getX();
        this.y = dest.getY()-origin.getY();
    }
    public double getRotation() {
        double res = Math.atan(y / x) * 180 / Math.PI;
        return x >= 0 ? res : 180 - res;
    }

    public Vector getXPair() {
        return new Vector(this.x, 0);
    }

    public Vector getYPair() {
        return new Vector(0, this.y);
    }

    public Vector multiply(double k) {
        return new Vector(this.x*k, this.y*k);
    }

    public Vector getDirection(double lenght) {
        return new Vector(x / lenght() * lenght, y / lenght() * lenght);
    }

    public Vector getDirection() {
        return getDirection(1);
    }

    public double lenght() {
        return Math.sqrt(x * x + y * y);
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
