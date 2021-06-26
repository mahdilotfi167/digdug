package ir.ac.kntu.models;

import javafx.scene.image.ImageView;

public class Object extends GameObject {
    ImageView mask;
    public Object(int gridX, int gridY,int width,int height, String maskPath) {
        super(gridX, gridY,width,height);
        this.mask = new ImageView(maskPath);
        this.mask.setLayoutX(getCurrentLayoutX());
        this.mask.setLayoutY(getCurrentLayoutY());
        this.mask.setFitWidth(width);
        this.mask.setFitHeight(height);
        getChildren().add(mask);
    }

}
