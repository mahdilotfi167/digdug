package ir.ac.kntu.models.balloon;

import static ir.ac.kntu.Constants.*;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ir.ac.kntu.Engine;

public class ExplosiveBalloon extends Balloon {

    public ExplosiveBalloon(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/explosive.png"), EXPLOSIVE_BALLOON_GRID_CODE);
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
        clearNeighbors();
        Engine.increaseScore(15);
        super.kill();
    }

    private void clearNeighbors() {
        Vector[] directions = {new Vector(0, -1),new Vector(1, 0),new Vector(0, 1),new Vector(-1, 0)};
        for (int i = 0;i<4;i++) {
            getMap().clearBlock(this.getPosition().sum(directions[i]));
            getMap().clearBlock(this.getPosition().sum(directions[i].multiply(2)));
            getMap().clearBlock(this.getPosition().sum(directions[i].sum(directions[(i+1)%4])));
            getMap().clearBlock(this.getPosition().sum(directions[i].sum(directions[(i+1)%4]).multiply(2)));
            getMap().clearBlock(this.getPosition().sum(directions[i].sum(directions[(i+1)%4].multiply(2))));
            getMap().clearBlock(this.getPosition().sum(directions[i].multiply(2).sum(directions[(i+1)%4])));
        }
    }
}