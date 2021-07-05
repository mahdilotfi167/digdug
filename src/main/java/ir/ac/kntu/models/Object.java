package ir.ac.kntu.models;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.scene.image.ImageView;

public class Object extends GameObject {
    public Object(Map map, int gridX, int gridY, int width, int height, ImageView mask, int gridCode) {
        super(map, gridX, gridY, width, height, gridCode, mask);
    }

    @Override
    public void setDirection(Vector direction) {
        if (direction.getX() < 0) {
            getMask().setScaleX(-1);
            getMask().setRotate(0);
        } else {
            getMask().setScaleX(1);
            getMask().setRotate(direction.getRotation());
        }
        super.setDirection(direction);
    }

}