package ir.ac.kntu.models.random;

import javafx.scene.image.ImageView;
import static ir.ac.kntu.Constants.*;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;

public class RandomObject extends GameObject {
    private int counter;

    public RandomObject(Map map, int gridX, int gridY, ImageView mask) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, 0, mask);
        this.counter = 0;
    }

    @Override
    public void update() {
        if (counter++ > 250) {
            getMap().removeObject(this);
        }
    }
}
