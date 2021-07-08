package ir.ac.kntu.models;

import ir.ac.kntu.components.SpriteRenderer;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends GameObject {

    private SpriteRenderer spriteRenderer;

    public Sprite(Map map, int gridX, int gridY, int width, int height, ImageView spriteSheet, int gridCode) {
        super(map, gridX, gridY, width, height, gridCode, spriteSheet);
        spriteRenderer = new SpriteRenderer(this.getMask(), Duration.millis(100), 2, 1, 0, 0,
                (int) this.getMask().getImage().getWidth(), (int) getMask().getImage().getHeight() / 2);
        spriteRenderer.setCycleCount(1);
        spriteRenderer.play();
    }

    @Override
    public void move(Vector movement) {
        super.move(movement);
        spriteRenderer.play();
    }

    public SpriteRenderer getSpriteRenderer() {
        return spriteRenderer;
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

    /**
     * kill method that calls by other gameobject that may kill this sprite
     */
    public void kill() {
        getMap().removeObject(this);
    }
}