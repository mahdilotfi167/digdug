package ir.ac.kntu.models.random;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.models.Player;
import javafx.scene.image.ImageView;

public class Mushroom extends RandomObject {

    public Mushroom(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/mushroom.png"));
    }

    @Override
    public void onCollision(GameObject collider) {
        if (collider instanceof Player) {
            getMap().removeObject(this);
            Player p = (Player) collider;
            p.setSpeed(p.getSpeed() * 1.5);
        }
    }
}
