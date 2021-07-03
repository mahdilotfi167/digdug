package ir.ac.kntu.models;

import ir.ac.kntu.components.SpriteRenderer;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends GameObject {

    SpriteRenderer spriteRenderer;

    public Sprite(Map map, int gridX, int gridY, int width, int height, ImageView spriteSheet, int gridCode) {
        super(map, gridX, gridY, width, height, gridCode, spriteSheet);
        spriteRenderer = new SpriteRenderer(this.getMask(), Duration.millis(100), 2, 1, 0, 0,
                (int) this.getMask().getImage().getWidth(), (int) getMask().getImage().getHeight() / 2);
        spriteRenderer.setCycleCount(1);
        spriteRenderer.play();
    }

    @Override
    public void move(Vector movement) {
        if (movement.getX() < 0) {
            getMask().setScaleX(-1);
            getMask().setRotate(0);
        } else {
            getMask().setScaleX(1);
            getMask().setRotate(movement.getRotation());
        }
        super.move(movement);
        spriteRenderer.play();
    }
    public SpriteRenderer getSpriteRenderer() {
        return spriteRenderer;
    }
}
