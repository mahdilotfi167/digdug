package ir.ac.kntu.models.random;

import ir.ac.kntu.Engine;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.models.Player;
import javafx.scene.image.ImageView;

public class Heart extends RandomObject {

    public Heart(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/heart.png"));
    }

    @Override
    public void onCollision(GameObject collider) {
        if (collider instanceof Player) {
            Engine.increasePlayerHealth();
            getMap().removeObject(this);
        }
    }
}
