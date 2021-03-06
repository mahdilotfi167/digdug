package ir.ac.kntu.models.random;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.models.Player;
import javafx.scene.image.ImageView;

public class Sniper extends RandomObject {
    public Sniper(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/gun.png"));
    }

    @Override
    public void onCollision(GameObject collider) {
        if (collider instanceof Player) {
            ((Player) collider).getPump().setRange(5);
            getMap().removeObject(this);
        }
    }
}