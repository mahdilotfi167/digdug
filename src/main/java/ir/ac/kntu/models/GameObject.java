package ir.ac.kntu.models;

import ir.ac.kntu.map.Map;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.rigidbody.Vector;
import javafx.scene.Parent;
import static ir.ac.kntu.Constants.*;
public abstract class GameObject extends Parent {
    private Position gridPos;
    private Vector direction;
    private Position layoutPos;
    // private int layoutX;
    // private int layoutY;
    private int width;
    private int height;
    private Map map;
    private int gridCode;
    public GameObject(Map map ,int gridX,int gridY,int width,int height,int gridCode) {
        // this.gridX = gridX;
        // this.gridY = gridY;
        this.gridPos = new Position(gridX, gridY);
        this.layoutPos = new Position(map.gridToLayout(gridX), map.gridToLayout(gridY));
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.width = width;
        this.height = height;
        this.map = map;
        this.direction = new Vector(1, 0);
    }
    public double getCurrentLayoutX() {
        return this.layoutPos.getX();
    }
    public double getCurrentLayoutY() {
        return this.layoutPos.getY();
    }
    public double getLayoutCenterX() {
        return this.layoutPos.getX()+width/2;
    }
    public double getLayoutCenterY() {
        return this.layoutPos.getY()+height/2;
    }
    /**
     * this method is out of support.
     * don't use it!
     * @param gridX
     */
    public void setGridX(int gridX) {
        // this.gridPos.setX(gridX);

        // this.layoutX = Map.gridToLayout(gridX);
        // this.gridPos.setX(gridX);
    }
    /**
     * this method is out of support.
     * don't use it!
     * @param gridY
     */
    public void setGridY(int gridY) {
        // this.layoutY = Map.gridToLayout(gridY);
        // this.gridPos.setY(gridY);
    }
    public int getGridX() {
        return (int)gridPos.getX();
        // return this.gridPos.getX();
    }
    public int getGridY() {
        return (int)gridPos.getY();
        // return this.gridPos.getY();
    }
    public int getGridCode() {
        return gridCode;
    }
    public Map getMap() {
        return map;
    }
    private boolean xNormallized = true;
    private boolean yNormallized = true;
    // private Position lasPosition;
    public void move(Vector movement) {
        this.direction = movement.getDirection();
        if (!map.contains(this.gridPos.sum(movement))) {
            return;
        }
        Position nextPos = this.gridPos.sum(movement.multiply(1.0/BLOCK_SCALE));
        normilizePair(nextPos);
        this.gridPos = nextPos;//!danger
        this.layoutPos.move(movement);
        if (xNormallized) {
            this.layoutPos.setX(getMap().gridToLayout(this.getGridX()));
        }
        if (yNormallized) {
            this.layoutPos.setY(getMap().gridToLayout(this.getGridY()));
        }
        // int oldX = this.getGridX(),oldY = this.getGridY();
        // setGridX(this.gridPos.getX());
        // setGridY(this.gridPos.getY());
        // map.updateObjectPos(this, oldX, oldY);
    }
    private void normilizePair(Position position) {
        final double accurancy = 1;
        int round = (int)Math.round(position.getX());
        if (xNormallized) {
            xNormallized = false;
        } else {
            if (round-accurancy/BLOCK_SCALE<=position.getX() && position.getX() <= round+accurancy/BLOCK_SCALE) {
                position.setX(round);
                xNormallized = true;
            }
        }
        round = (int)Math.round(position.getY());
        if (yNormallized) {
            yNormallized = false;
        } else {
            if (round-accurancy/BLOCK_SCALE<=position.getY() && position.getY() <= round+accurancy/BLOCK_SCALE) {
                position.setY(round);
                yNormallized = true;
            }
        }
    }
    public Vector getDirection() {
        return direction;
    }
    public Position getGridPos() {
        return gridPos;
    }
    public Position getLayoutPos() {
        return layoutPos;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
