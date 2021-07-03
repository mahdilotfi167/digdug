package ir.ac.kntu.models.random;

import ir.ac.kntu.core.Map;
import javafx.scene.image.ImageView;

public class Heart extends RandomObject {

    public Heart(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/heart.png"));
    }
}
