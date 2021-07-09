package ir.ac.kntu.core.rigidbody;

/**
 * A simple class for using vector calculations
 */
public class Vector {
    private double x;
    private double y;

    /**
     * create new vector in x and y dimentions
     * @param x
     * @param y
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * create new vector between origin and dest positions
     * @param origin
     * @param dest
     */
    public Vector(Position origin,Position dest) {
        this.x = dest.getX()-origin.getX();
        this.y = dest.getY()-origin.getY();
    }

    /**
     * returns the angle vector with positive direction of x axis
     * @return the angle in degree unit
     */
    public double getRotation() {
        double res = Math.atan(y / x) * 180 / Math.PI;
        return x >= 0 ? res : 180 - res;
    }

    /**
     * returns x pair of this vector
     * @return
     */
    public Vector getXPair() {
        return new Vector(this.x, 0);
    }

    /**
     * returns y pair of this vector
     * @return
     */
    public Vector getYPair() {
        return new Vector(0, this.y);
    }

    /**
     * returns new vector that is multiplied of this vector in k
     * @param k
     * @return
     */
    public Vector multiply(double k) {
        return new Vector(this.x*k, this.y*k);
    }

    /**
     * returns new vector in selected length and this vector direction
     * @param lenght
     * @return
     */
    public Vector getDirection(double lenght) {
        return new Vector(x / lenght() * lenght, y / lenght() * lenght);
    }

    /**
     * returns unit vector of this vector
     * @return
     */
    public Vector getDirection() {
        return getDirection(1);
    }

    /**
     * returns the length of this vector
     * @return
     */
    public double lenght() {
        return Math.sqrt(x * x + y * y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector sum(Vector other) {
        return Vector.sum(this, other);
    }

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")";
    }

    public static Vector sum(Vector v1,Vector v2) {
        return new Vector(v1.x+v2.x, v1.y+v2.y);
    }

    public static double dot(Vector v1,Vector v2) {
        return v1.x*v2.x+v1.y*v2.y;
    }
}