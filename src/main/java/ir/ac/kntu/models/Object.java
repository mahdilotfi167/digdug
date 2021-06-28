package ir.ac.kntu.models;

import ir.ac.kntu.map.Map;
import javafx.scene.image.ImageView;

public class Object extends GameObject {
    ImageView mask;
    public Object(Map map,int gridX, int gridY,int width,int height, String maskPath,int gridCode) {
        super(map,gridX, gridY,width,height,gridCode);
        this.mask = new ImageView(maskPath);
        this.mask.setLayoutX(getCurrentLayoutX());
        this.mask.setLayoutY(getCurrentLayoutY());
        this.mask.setFitWidth(width);
        this.mask.setFitHeight(height);
        getChildren().add(mask);
    }
    @Override
    public void setGridX(int gridX) {
        super.setGridX(gridX);
        this.mask.setLayoutX(getCurrentLayoutX());
    }
    @Override
    public void setGridY(int gridY) {
        super.setGridY(gridY);
        this.mask.setLayoutY(getCurrentLayoutY());
    }
    public ImageView getMask() {
        return mask;
    }
}