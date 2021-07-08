package ir.ac.kntu.models.balloon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static ir.ac.kntu.Constants.*;
import ir.ac.kntu.Engine;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.utils.Random;

public class DragonBalloon extends Balloon {
    private int counter;

    public DragonBalloon(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/dragon.png"), DRAGON_BALLOON_GRID_CODE);
    }

    private Fire fire;

    @Override
    public void update() {
        if (counter++ % 75 == 0) {
            Position nextPos = this.getPosition().sum(this.getDirection());
            if (Random.getBoolean() && !getMap().isBlock(nextPos)) {
                fire = new Fire(getMap(), (int) nextPos.getX(), (int) nextPos.getY());
                fire.setDirection(this.getDirection());
                getMap().addObject(fire);
            }
        }
        if (fire == null || !fire.isActive()) {
            super.update();
        }
    }

    @Override
    protected void setInflateOrder(int inflateOrder) {
        if (0 < inflateOrder && inflateOrder < 5) {
            this.getInflateMask().setImage(new Image("/assets/dragon/order" + inflateOrder + ".png"));
        }
        super.setInflateOrder(inflateOrder);
    }

    @Override
    public void kill() {
        Engine.increaseScore(10);
        super.kill();
    }
}