package ir.ac.kntu.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import ir.ac.kntu.core.rigidbody.Position;
import javafx.scene.paint.Color;
public class MapTest {
    public Map getMap() {
        HashMap<Integer,GameObjectConstructor> cons = new HashMap<>();
        cons.put(2, Foo::new);
        HashMap<Integer,Color> fill = new HashMap<>();
        fill.put(1, Color.YELLOW);
        int[][] grid = {
            {2,1,1,1,0,0,},
            {1,1,1,1,1,0,},
            {1,1,1,1,1,1,},
            {1,1,1,1,1,1,},
            {0,1,1,1,1,1,},
            {0,0,1,1,1,1,},
        };
        return new Map(grid.clone(), cons, fill, 10, Color.BLACK);
    }
    
    @Test
    public void isBlockTest() {
        Map map = getMap();
        assertTrue(map.isBlock(1,1));
        assertFalse(map.isBlock(0,0));
        assertFalse(map.isBlock(5,0));
    }
    
    @Test
    public void clearBlockTest() {
        Map map = getMap();
        map.clearBlock(1,1);
        assertEquals(0, map.getData(new Position(1,1)));
        map.clearBlock(0,0);
        assertEquals(2, map.getData(new Position(0,0)));
        map.clearBlock(5,0);
        assertEquals(0, map.getData(new Position(5,0)));
    }
    
    @Test
    public void addObjectTest() {
        Map map = getMap();
        Foo f;
        f = new Foo(map, 0, 0);
        map.addObject(f);
        assertEquals(4, map.getData(new Position(0,0)));
        f = new Foo(map, 5, 0);
        map.addObject(f);
        assertEquals(2, map.getData(new Position(5,0)));
        f = new Foo(map, 1, 1);
        map.addObject(f);
        assertEquals(3, map.getData(new Position(1,1)));
    }
    
    @Test
    public void removeObjectTest() {
        Map map = getMap();
        Foo f = map.collect(Foo.class).get(0);
        map.removeObject(f);
        assertEquals(0, map.getData(new Position(0,0)));
    }
    
    @Test
    public void simpleCollectTest() {
        Map map = getMap();
        ArrayList<Foo> foos;
        foos = map.collect(Foo.class);
        assertEquals(1, foos.size());
        Foo f = new Foo(map, 0, 1);
        map.addObject(f);
        foos = map.collect(Foo.class);
        assertEquals(2, foos.size());
        assertTrue(foos.contains(f));
    }
    
    @Test
    public void subClassingCollectTest() {
        Map map = getMap();
        ArrayList<GameObject> foos;
        foos = map.collect(GameObject.class);
        assertEquals(1, foos.size());
        Foo f = new Foo(map, 0, 1);
        map.addObject(f);
        foos = map.collect(GameObject.class);
        assertEquals(2, foos.size());
        assertTrue(foos.contains(f));
    }
}