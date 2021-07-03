package ir.ac.kntu.models;

import javafx.scene.image.ImageView;
import static ir.ac.kntu.Constants.*;

import ir.ac.kntu.core.Map;
public class DragonBalloon extends Balloon {

    public DragonBalloon(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/dragon.png"), DRAGON_BALLOON_GRID_CODE);
    }
    
}
