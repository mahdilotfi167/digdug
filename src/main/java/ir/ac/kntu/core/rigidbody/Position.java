package ir.ac.kntu.core.rigidbody;

import java.util.ArrayList;
import java.util.Objects;

public class Position {
    private double x;
    private double y;
    private ArrayList<ChangeListener<Double>> xListeners;
    private ArrayList<ChangeListener<Double>> yListeners;

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
        if (xListeners != null){
            for (ChangeListener<Double> listener : xListeners) {
                listener.onChange(x-v.getX(), x);
            }
        }
        if (yListeners != null){
            for (ChangeListener<Double> listener : yListeners) {
                listener.onChange(y-v.getY(), y);
            }
        }
    }

    public void setX(double x) {
        double old = this.x;
        this.x = x;
        if (this.xListeners != null) {
            for (ChangeListener<Double> listener : xListeners) {
                listener.onChange(old, x);
            }
        }
    }

    public void setY(double y) {
        double old = this.y;
        this.y = y;
        if (this.yListeners != null) {
            for (ChangeListener<Double> listener : yListeners) {
                listener.onChange(old, y);
            }
        }
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

    @Override
    public boolean equals(Object obj) {
        return this.x == ((Position)obj).x && this.y==((Position)obj).y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y);
    }
    public void addXListener(ChangeListener<Double> listener) {
        if (this.xListeners == null) {
            this.xListeners = new ArrayList<>();
        }
        this.xListeners.add(listener);
    }
    public void addYListener(ChangeListener<Double> listener) {
        if (this.yListeners == null) {
            this.yListeners = new ArrayList<>();
        }
        this.yListeners.add(listener);
    }
}