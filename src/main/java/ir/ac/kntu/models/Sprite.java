package ir.ac.kntu.models;

import ir.ac.kntu.components.SpriteRenderer;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.rigidbody.Vector;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Sprite extends GameObject {

    ImageView spriteSheet;
    SpriteRenderer spriteRenderer;

    public Sprite(Map map, int gridX, int gridY, int width, int height, String spriteSheetPath, int gridCode) {
        super(map, gridX, gridY, width, height, gridCode);
        this.spriteSheet = new ImageView(spriteSheetPath);
        this.spriteSheet.setLayoutX(getCurrentLayoutX());
        this.spriteSheet.setLayoutY(getCurrentLayoutY());
        this.spriteSheet.setFitWidth(width);
        this.spriteSheet.setFitHeight(height);
        getChildren().add(this.spriteSheet);
        spriteRenderer = new SpriteRenderer(this.spriteSheet, Duration.millis(100), 2, 1, 0, 0,
                (int) spriteSheet.getImage().getWidth(), (int) spriteSheet.getImage().getHeight() / 2);
        spriteRenderer.setCycleCount(1);
        spriteRenderer.play();
    }

    @Override
    public void setGridX(int gridX) {
        super.setGridX(gridX);
        this.spriteSheet.setLayoutX(getCurrentLayoutX());
        this.spriteRenderer.play();
    }

    @Override
    public void setGridY(int gridY) {
        super.setGridY(gridY);
        this.spriteSheet.setLayoutY(getCurrentLayoutY());
        this.spriteRenderer.play();
    }

    @Override
    public void move(Vector movement) {
        if (movement.getX() < 0) {
            spriteSheet.setScaleX(-1);
            spriteSheet.setRotate(0);
        } else {
            spriteSheet.setScaleX(1);
            spriteSheet.setRotate(movement.getRotation());
        }
        super.move(movement);
        spriteRenderer.play();
        spriteSheet.setLayoutX(this.getCurrentLayoutX());
        spriteSheet.setLayoutY(this.getCurrentLayoutY());
    }
}
