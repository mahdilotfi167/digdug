package ir.ac.kntu.models;

import ir.ac.kntu.map.Map;
import javafx.scene.image.ImageView;

public class Object extends GameObject {
    public Object(Map map, int gridX, int gridY, int width, int height, ImageView mask, int gridCode) {
        super(map, gridX, gridY, width, height, gridCode, mask);
    }

}