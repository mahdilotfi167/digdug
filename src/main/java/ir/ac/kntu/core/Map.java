package ir.ac.kntu.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import ir.ac.kntu.core.rigidbody.Position;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
/**
 * A micro tool for rendering game maps using javafx panes
 * @author Mahdi Lotfi
 */
public class Map extends Pane {
    private int[][] grid;
    private HashMap<Integer,GameObjectConstructor> constructors;
    private HashMap<Integer,Color> fillers;
    private ArrayList<GameObject> pointers;
    private int blockScale;
    private GraphicsContext graphicsContext;
    private HashMap<GameObject,Position> spawnPositions;
    private Set<java.util.Map.Entry<Integer,Color>> fillSet;
    private Timeline collisionChecker;

    public Map(int[][] gridCodes,HashMap<Integer,GameObjectConstructor> constructors,HashMap<Integer,Color> fillers,int blockScale,Color background) {
        this.blockScale = blockScale;
        this.spawnPositions = new HashMap<>();
        this.grid = gridCodes.clone();
        this.constructors = constructors;
        this.fillers = fillers;
        this.fillSet = fillers.entrySet();
        this.pointers = new ArrayList<>();
        Canvas canvas = new Canvas(grid[0].length * blockScale, grid.length*blockScale);
        getChildren().add(canvas);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(background);
        graphicsContext.fillRect(0, 0, grid[0].length*blockScale, grid.length*blockScale);
        this.setPrefWidth(grid[0].length*blockScale);
        this.setPrefHeight(grid.length*blockScale);
        collisionChecker = new Timeline();
        collisionChecker.getKeyFrames().add(new KeyFrame(Duration.millis(40), e -> {
            for (int i = 0; i < pointers.size(); i++) {
                for (int j = i+1; j < pointers.size(); j++) {
                    if (pointers.get(i).getPosition().equals(pointers.get(j).getPosition())) {
                        pointers.get(i).onCollision(pointers.get(j));
                        pointers.get(j).onCollision(pointers.get(i));
                    }
                }
            }
            for (int i = 0;i < pointers.size(); i++) {
                pointers.get(i).update();
            }
        }));
        render();
    }
    
    private void render() {
        GameObject gameObject;
        Color color;
        Set<java.util.Map.Entry<Integer,GameObjectConstructor>> eSet = this.constructors.entrySet();
        for (int i = 0;i<grid.length;i++) {
            for (int j = 0;j<grid[0].length;j++) {
                //*colorize...
                if ((color = fillers.get(grid[i][j])) != null) {
                    graphicsContext.setFill(color);
                    graphicsContext.fillRect(blockScale*j,blockScale*i,blockScale,blockScale);
                }
                //*gameobjects...
                for (java.util.Map.Entry<Integer,GameObjectConstructor> entry : eSet) {
                    if ((entry.getKey() & grid[i][j]) != 0) {
                        gameObject = entry.getValue().getObject(this, j, i);
                        getChildren().add(gameObject);
                        pointers.add(gameObject);
                        spawnPositions.put(gameObject, new Position(gameObject.getPosition()));
                    }
                }
            }
        }
    }
    
    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }
    
    public void updateObjectPos(GameObject gameObject, int oldX, int oldY) {
        grid[oldY][oldX]-=gameObject.getGridCode();
        grid[gameObject.getGridY()][gameObject.getGridX()]+=gameObject.getGridCode();
    }

    public void removeObject(GameObject gameObject) {
        this.pointers.remove(gameObject);
        this.getChildren().remove(gameObject);
        grid[gameObject.getGridY()][gameObject.getGridX()]-=gameObject.getGridCode();
    }

    public void fill(int colorCode,int x,int y) {
        graphicsContext.setFill(fillers.get(colorCode));
        graphicsContext.fillRect(x*blockScale,y*blockScale,blockScale,blockScale);
        this.grid[y][x] = colorCode;//todo bug it's better +=
    }

    public boolean isBlock(int x,int y) {
        if (!contains(new Position(x,y))) {
            return true;
        }
        return this.fillers.get(grid[y][x])!=null;
    }

    public boolean isBlock(Position position) {
        return isBlock((int)position.getX(),(int)position.getY());
    }

    public int getData(Position position) {
        if (!this.contains(position)) {
            return 0;
        }
        return grid[(int)position.getY()][(int)position.getX()];
    }

    public void clearBlock(int x,int y) {
        for (java.util.Map.Entry<Integer,Color> entry : fillSet) {
            if ((grid[y][x] & entry.getKey()) != 0) {
                grid[y][x] -= entry.getKey();
            }
        }
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(x*blockScale,y*blockScale,blockScale,blockScale);
    }

    public double gridToLayout(int pos) {
        return pos*blockScale;
    }

    public int layoutToGrid(double pos) {
        return (int)(pos/blockScale);
    }

    public boolean contains(Position position) {
        return position.getX() >= 0
            && position.getY() >= 0
            && position.getX() < grid[0].length
            && position.getY() < grid.length;
    }

    public int getGridWidth() {
        return grid[0].length;
    }
    
    public int getGridHeight() {
        return grid.length;
    }

    public <T> ArrayList<T> collect(Class<T> clazz) {
        ArrayList<T> res = new ArrayList<>();
        for (GameObject go : pointers) {
            if (go != null && go.getClass() == clazz) {
                res.add((T) go);
            }
        }
        return res;
    }

    public int[][] getGrid() {
        return grid.clone();
    }

    public void addObject(GameObject go) {
        this.pointers.add(go);
        this.getChildren().add(go);
        this.grid[go.getGridY()][go.getGridX()] += go.getGridCode();
    }

    public ArrayList<Position> getFreePoints() {
        ArrayList<Position> res = new ArrayList<>();
        for (int i = 0;i<grid.length;i++) {
            for (int j = 0;j<grid[0].length;j++) {
                if (!isBlock(j, i)) {
                    res.add(new Position(j,i));
                }
            }
        }
        return res;
    }

    public void startLoop() {
        collisionChecker.setCycleCount(Timeline.INDEFINITE);
        collisionChecker.play();
    }

    public void stopLoop() {
        collisionChecker.stop();
    }

    public void resetSpawnPositions() {
        for (GameObject go : this.pointers) {
            if (spawnPositions.get(go)!=null) {
                go.getPosition().setX(spawnPositions.get(go).getX());   
                go.getPosition().setY(spawnPositions.get(go).getY());
            }
        }
    }
}