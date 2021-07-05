package ir.ac.kntu.models.balloon;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.models.Object;
import ir.ac.kntu.models.Player;
import javafx.scene.image.ImageView;

import static ir.ac.kntu.Constants.*;

public class Fire extends Object {
    public Fire(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, new ImageView("/assets/fire.png"), 0);
        counter = 0;
    }

    private int counter;

    @Override
    public void update() {
        if (counter++ > 15) {
            getMap().removeObject(this);
        }
    }

    @Override
    public void onCollision(GameObject collider) {
        if (collider instanceof Player) {
            ((Player) collider).kill();
        }
    }

    public boolean isActive() {
        return this.counter < 15;
    }
}