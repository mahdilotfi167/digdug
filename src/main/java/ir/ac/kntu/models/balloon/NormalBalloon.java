package ir.ac.kntu.models.balloon;

import static ir.ac.kntu.Constants.*;
import ir.ac.kntu.Engine;
import ir.ac.kntu.core.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NormalBalloon extends Balloon {

    public NormalBalloon(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/balloon.png"), NORMAL_BALLOON_GRID_CODE);
    }

    @Override
    protected void setInflateOrder(int inflateOrder) {
        if (0 < inflateOrder && inflateOrder < 5) {
            this.getInflateMask().setImage(new Image("/assets/balloon/order" + inflateOrder + ".png"));
        }
        super.setInflateOrder(inflateOrder);
    }

    @Override
    public void kill() {
        Engine.increaseScore(5);
        super.kill();
    }
}
