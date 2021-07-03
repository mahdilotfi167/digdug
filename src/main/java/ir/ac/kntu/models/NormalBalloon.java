package ir.ac.kntu.models;
import static ir.ac.kntu.Constants.*;

import ir.ac.kntu.core.Map;
import javafx.scene.image.ImageView;
public class NormalBalloon extends Balloon {

    public NormalBalloon(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, new ImageView("/assets/balloon.png"), NORMAL_BALLOON_GRID_CODE);
        //TODO Auto-generated constructor stub
    }
    
}
