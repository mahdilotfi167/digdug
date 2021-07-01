package ir.ac.kntu.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import ir.ac.kntu.map.Map;
import ir.ac.kntu.util.GameObjectConstructor;
import javafx.scene.paint.Color;

public class MapSerializer {
    String filename;
    public MapSerializer(String filename) {
        this.filename = filename;
    }
    public static Map load(HashMap<Integer,GameObjectConstructor> constructors,HashMap<Integer,Color> fillers,int blockScale,Color background) {
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("map.map"))) {
            IntBuffer buffer = ByteBuffer.wrap(in.readAllBytes()).asIntBuffer();
            int grid[][] = new int[buffer.get(0)][buffer.get(1)];
            for (int i = 0;i<grid.length;i++) {
                for (int j = 0;j<grid[0].length;j++) {
                    grid[i][j] = buffer.get(i*grid[0].length+j+2);
                }
            }
            return new Map(grid, constructors, fillers, blockScale, background);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void export(Map map) {
        int[][] grid = map.getGrid();
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename+".map"))) {
            out.write(intToByte(grid.length));
            out.write(intToByte(grid[0].length));
            for (int[] row : grid) {
                for (int n : row) {
                    out.write(intToByte(n));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static byte[] intToByte(int n) {
        return ByteBuffer.allocate(4).putInt(n).array();
    }
}
