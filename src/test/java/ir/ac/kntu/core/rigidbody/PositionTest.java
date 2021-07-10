package ir.ac.kntu.core.rigidbody;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PositionTest {
    public double x;
    public double y;
    @Test
    public void listenTest1() {
        Position p = new Position(0,0);
        p.addXListener((oldval,newval)->{x = newval;});
        p.addYListener((oldval,newval)->{y = newval;});
        p.setX(5);
        assertEquals(5.0, x, 0.0);
        p.setY(10); 
        assertEquals(10.0, y, 0.0);
    }
    @Test
    public void listenTest2() {
        Position p = new Position(0,0);
        p.addXListener((oldval,newval)->{x = newval;});
        p.addYListener((oldval,newval)->{y = newval;});
        p.move(new Vector(5, 10));
        assertEquals(5.0, x, 0.0);
        assertEquals(10.0, y, 0.0);
    }
}