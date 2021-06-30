package ir.ac.kntu.models;
import static ir.ac.kntu.Constants.*;

import ir.ac.kntu.map.Map;
import javafx.scene.image.ImageView;
public class Wall extends Object {

    public Wall(Map map,int gridX, int gridY) {
        super(map,gridX, gridY,BLOCK_SCALE,BLOCK_SCALE, new ImageView("/assets/block1.png"),YELLO_WALL_GRID_CODE);
        getMask().setFitWidth(getMask().getFitWidth()+5);
        getMask().setFitHeight(getMask().getFitHeight()+5);
        getMask().setLayoutX(getMask().getLayoutX()-2.5);
        getMask().setLayoutY(getMask().getLayoutY()-2.5);
    }
    
}
