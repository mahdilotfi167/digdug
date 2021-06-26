package ir.ac.kntu.map;

import java.io.Serializable;
import java.util.HashMap;

import ir.ac.kntu.models.GameObject;
import ir.ac.kntu.models.Wall;
import ir.ac.kntu.util.GameObjectConstructor;
import javafx.scene.layout.Pane;
import static ir.ac.kntu.Constants.*;
public class Map extends Pane implements Serializable {
    private int[][] grid = {
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1},
        };
    private GameObject[][] pointers;
    private HashMap<Integer,GameObjectConstructor> constructores;
    public Map() {
        pointers = new GameObject[grid.length][grid[0].length];
        constructores = new HashMap<>();
        constructores.put(1, Wall::new);
        render();
    }
    private void render() {
        GameObject current;
        for (int i = 0;i<grid.length;i++) {
            for (int j = 0;j<grid.length;j++) {
                current = constructores.get(grid[i][j]).getObject(j, i);
                pointers[i][j] = current;
                getChildren().add(current);
            }
        }
    }
    public static int gridToLayout(int pos) {
        return pos*BLOCK_SCALE;
    }
    public static int layoutToGrid(int pos) {
        return pos/BLOCK_SCALE;
    }
}
