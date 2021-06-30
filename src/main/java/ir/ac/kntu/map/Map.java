package ir.ac.kntu.map;

import java.io.Serializable;
import java.util.HashMap;

import ir.ac.kntu.models.GameObject;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.Wall;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.util.GameObjectConstructor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static ir.ac.kntu.Constants.*;
/**
 * a tool for rendering game maps
 */
public class Map extends Pane implements Serializable {
    private int[][] grid;
    private GameObject[][] pointers;
    private HashMap<Integer,GameObjectConstructor> constructors;
    private HashMap<Integer,Color> fillers;
    private int blockScale;
    GraphicsContext graphicsContext;
    public Map(int[][] gridCodes,HashMap<Integer,GameObjectConstructor> constructors,HashMap<Integer,Color> fillers,int blockScale,Color background) {
        this.blockScale = blockScale;
        this.grid = gridCodes.clone();
        this.pointers = new GameObject[grid.length][grid[0].length];
        this.constructors = constructors;
        this.fillers = fillers;
        Canvas canvas = new Canvas(grid[0].length * blockScale, grid.length*blockScale);
        getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(background);
        graphicsContext.fillRect(0, 0, grid[0].length*blockScale, grid.length*blockScale);
        this.setPrefWidth(grid[0].length*blockScale);
        this.setPrefHeight(grid.length*blockScale);
        render();
    }
    private Player player;
    private void render() {
        GameObjectConstructor goc;
        GameObject gameObject;
        Color color;
        for (int i = 0;i<grid.length;i++) {
            for (int j = 0;j<grid[0].length;j++) {
                if ((color = fillers.get(grid[i][j])) != null) {
                    graphicsContext.setFill(color);
                    graphicsContext.fillRect(blockScale*j,blockScale*i,blockScale,blockScale);
                }
                if ((goc = constructors.get(grid[i][j]))!= null) {
                    gameObject = goc.getObject(this, j, i);
                    pointers[i][j] = gameObject;
                    getChildren().add(gameObject);
                    if (gameObject instanceof Player) {//todo this is bad :(
                        this.player = (Player)gameObject;
                    }
                }
            }
        }
    }
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
    public void updateObjectPos(GameObject gameObject , int oldX, int oldY) {
        // graphicsContext.setFill(Color.BLUE);
        // graphicsContext.fillRoundRect(gridToLayout(oldX),gridToLayout(oldY), gameObject.getWidth() , gameObject.getHeight(), 5, 5);
        grid[oldY][oldX] = 0;
        pointers[oldY][oldX] = null;
        // GameObject pastObject = pointers[gameObject.getGridY()][gameObject.getGridX()];
        // getChildren().remove(pastObject);
        grid[gameObject.getGridY()][gameObject.getGridX()] = gameObject.getGridCode();
        pointers[gameObject.getGridY()][gameObject.getGridX()] = gameObject;
    }
    public void removeObject(int x,int y) {
        
    }
    public GameObject getObject(int x,int y) {
        if (x<0 || y<0 || x >=grid[0].length || y>=grid.length) {
            return null;
        }
        return pointers[y][x];
    }
    public int getData(int x,int y) {
        return grid[y][x];
    }
    public void setData(int x,int y,int data) {
        grid[y][x] = data;
    }
    public double gridToLayout(int pos) {
        return pos*blockScale;
    }
    public int layoutToGrid(double pos) {
        return (int)(pos/blockScale);
    }
    public boolean contains(Position position,int width,int height) {
        return position.getX() >= 0
            && position.getY() >= 0
            && position.getX()+width <= grid[0].length*blockScale
            && position.getY()+height <= grid.length*blockScale;
    }
    public static Map load(String path) {
        return null;
    }

    public int getGridWidth() {
        return grid[0].length;
    }
    public int getGridHeight() {
        return grid.length;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void printGrid() {
        for (int i = 0;i<grid.length;i++) {
            for (int j = 0;j<grid[0].length;j++) {
                System.out.print(grid[i][j]+" ");
            }
            System.out.println();
        }
    }
}
