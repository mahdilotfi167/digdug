package ir.ac.kntu.models;

import ir.ac.kntu.map.Map;
import javafx.scene.Parent;

public abstract class GameObject extends Parent {
    int currentGridX;
    int currentGridY;
    int currentLayoutX;
    int currentLayoutY;
    int width;
    int height;
    public GameObject(int gridX,int gridY,int width,int height) {
        this.currentGridX = gridX;
        this.currentGridY = gridY;
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.currentLayoutX = Map.gridToLayout(gridX);
        this.currentLayoutY = Map.gridToLayout(gridY);
        this.width = width;
        this.height = height;
    }
    public int getCurrentLayoutX() {
        return currentLayoutX;
    }
    public int getCurrentLayoutY() {
        return currentLayoutY;
    }
}
