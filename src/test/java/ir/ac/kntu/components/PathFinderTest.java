package ir.ac.kntu.components;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import ir.ac.kntu.components.PathFinder.Path;
import static ir.ac.kntu.Constants.*;

public class PathFinderTest {
    @Test
    public void aStarTest1() {
        int w = YELLO_WALL_GRID_CODE;
        int grid[][] = { 
            { 0, 0, 0, w, w }, 
            { w, w, 0, w, w }, 
            { w, w, 0, w, w }, 
            { w, w, 0, 0, 0 },
            { w, w, w, w, 0 }, 
        };
        Map map = new Map(grid, CONSTRUCTORS, FILLERS, BLOCK_SCALE, BACKGROUND);
        PathFinder pf = new PathFinder(map);
        Path p = pf.find(new Position(0, 0), new Position(4, 4));
        Vector[] path = { 
            new Vector(1, 0), new Vector(1, 0), new Vector(0, 1), new Vector(0, 1),
            new Vector(0, 1), new Vector(1, 0), new Vector(1, 0), new Vector(0, 1), 
        };
        for (int i = 0;p.hasNext();i++) {
            assertEquals(path[i], p.next());
        }
    }

    @Test
    public void aStarTest2() {
        int w = YELLO_WALL_GRID_CODE;
        int grid[][] = { 
            { 0, 0, 0, 0, 0 }, 
            { w, w, 0, w, 0 }, 
            { 0, 0, 0, w, 0 }, 
            { 0, w, w, w, 0 },
            { 0, 0, 0, 0, 0 }, 
        };
        Map map = new Map(grid, CONSTRUCTORS, FILLERS, BLOCK_SCALE, BACKGROUND);
        PathFinder pf = new PathFinder(map);
        Path p = pf.find(new Position(0, 0), new Position(4, 4));
        Vector[] path = { 
            new Vector(1, 0), new Vector(1, 0), new Vector(1, 0), new Vector(1, 0),
            new Vector(0, 1), new Vector(0, 1), new Vector(0, 1), new Vector(0, 1), 
        };
        for (int i = 0;p.hasNext();i++) {
            assertEquals(path[i], p.next());
        }
    }

    @Test
    public void aStarTest3() {
        int w = YELLO_WALL_GRID_CODE;
        int grid[][] = { 
            { 0, 0, 0, 0, 0 }, 
            { w, w, w, w, 0 }, 
            { 0, 0, 0, 0, 0 }, 
            { 0, w, w, w, w },
            { 0, 0, 0, 0, 0 }, 
        };
        Map map = new Map(grid, CONSTRUCTORS, FILLERS, BLOCK_SCALE, BACKGROUND);
        PathFinder pf = new PathFinder(map);
        Path p = pf.find(new Position(0, 0), new Position(4, 4));
        Vector[] path = {  
            new Vector(1, 0), new Vector(1, 0), new Vector(1, 0), new Vector(1, 0), 
            new Vector(0, 1), new Vector(0, 1), new Vector(-1, 0), new Vector(-1, 0), 
            new Vector(-1, 0), new Vector(-1, 0), new Vector(0, 1), new Vector(0, 1), 
            new Vector(1, 0), new Vector(1, 0), new Vector(1, 0), new Vector(1, 0),
        };
        for (int i = 0;p.hasNext();i++) {
            assertEquals(path[i], p.next());
        }
    }

    @Test
    public void bfsTest1() {
        int w = YELLO_WALL_GRID_CODE;
        int grid[][] = { 
            { w, w, 0, w, w }, 
            { w, w, 0, w, w }, 
            { w, w, 0, 0, w }, 
            { 0, 0, 0, w, w }, 
            { w, w, 0, 0, 0 },
        };
        Map map = new Map(grid, CONSTRUCTORS, FILLERS, BLOCK_SCALE, BACKGROUND);
        PathFinder pf = new PathFinder(map);
        Position free = pf.bfs(new Position(2,0), p->p.getX()!=2);
        assertEquals(new Position(3,2), free);
    }

    @Test
    public void bfsTest2() {
        int w = YELLO_WALL_GRID_CODE;
        int grid[][] = { 
            { w, w, 0, w, w }, 
            { w, w, 0, w, w }, 
            { w, w, 0, w, w }, 
            { 0, 0, 0, w, w }, 
            { w, w, 0, 0, 0 },
        };
        Map map = new Map(grid, CONSTRUCTORS, FILLERS, BLOCK_SCALE, BACKGROUND);
        PathFinder pf = new PathFinder(map);
        Position free = pf.bfs(new Position(2,0), p->p.getX()!=2 && p.getY()<3);
        assertNull(free);
    }
}