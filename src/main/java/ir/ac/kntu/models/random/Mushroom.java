package ir.ac.kntu.models.random;

import ir.ac.kntu.core.Map;
import javafx.scene.image.ImageView;

public class Mushroom extends RandomObject {

    public Mushroom(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/mushroom.png"));
    }
}
