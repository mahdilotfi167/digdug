package ir.ac.kntu.models;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.scene.image.ImageView;
import static ir.ac.kntu.Constants.*;

import java.util.ArrayList;

public class Stone extends Object {
    private boolean falling;
    private Vector gravity;
    private ArrayList<Sprite> crushers;
    private int counter;
    private int deadCounter;

    public Stone(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, new ImageView("/assets/stone.png"), STONE_GRID_CODE);
        this.gravity = new Vector(0, 1);
        this.counter = 0;
        this.deadCounter = 0;
        this.crushers = new ArrayList<>();
    }

    @Override
    public void update() {
        if (!getMap().isBlock(this.getPosition().sum(gravity.getDirection()))) {
            if (counter++ > 25) {
                falling = true;
                move(gravity);
                for (Sprite crusher : crushers) {
                    crusher.move(gravity);
                }
            }
        } else if (falling) {
            if (deadCounter++ > 15) {
                getMap().removeObject(this);
                for (Sprite crusher : crushers) {
                    crusher.kill();
                }
            }
        }
    }

    @Override
    public void onCollision(GameObject collider) {
        if (falling && deadCounter == 0) {
            if (collider instanceof Sprite) {
                collider.setWidth(collider.getWidth() / 2);
                if (!crushers.contains(collider)) {
                    this.crushers.add((Sprite) collider);
                }
            }
        }
    }

    public boolean getFalling() {
        return this.falling;
    }
}