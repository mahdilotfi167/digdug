package ir.ac.kntu.core.rigidbody;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VectorTest {
    @Test
    public void rotationTest() {
        Vector v; 
        v = new Vector(1, 0);
        assertEquals(0 , v.getRotation(), 0.0);
        v = new Vector(0, 1);
        assertEquals(90 , v.getRotation(), 0.0);
        v = new Vector(-1, 0);
        assertEquals(180 , v.getRotation(), 0.0);
        v = new Vector(0, -1);
        assertEquals(-90 , v.getRotation(), 0.0);
    }
    
    @Test
    public void directionTest() {
        Vector v;
        v = new Vector(3, 4); 
        assertEquals(new Vector(0.6, 0.8) , v.getDirection());
        assertEquals(new Vector(6, 8) , v.getDirection(10));
    }
    
    @Test
    public void lengthTest() {
        Vector v;
        v = new Vector(3, 4);
        assertEquals(5, v.lenght(),0.0); 
    }
    
    @Test
    public void multiplyTest() {
        Vector v;
        v = new Vector(3, 4);
        assertEquals(new Vector(12,16), v.multiply(4));
    }
}