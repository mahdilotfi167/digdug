package ir.ac.kntu;

import java.util.HashMap;

import ir.ac.kntu.models.DragonBalloon;
import ir.ac.kntu.models.NormalBalloon;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.utils.GameObjectConstructor;
import javafx.scene.paint.Color;

public class Constants {
    final public static int BLOCK_SCALE = 30;
    final public static int GRID_X_SIZE = 25;
    final public static int GRID_Y_SIZE = 20;

    final public static int EMPTY_GRID_CODE = 0;
    
    final public static int YELLO_WALL_GRID_CODE = 1;
    final public static int ORANGE_WALL_GRID_CODE = 2;
    final public static int RED_WALL_GRID_CODE = 4;
    
    final public static int PLAYER_GRID_CODE = 8;
    final public static int NORMAL_BALLOON_GRID_CODE = 16;
    final public static int DRAGON_BALLOON_GRID_CODE = 32;
    final public static int STONE_GRID_CODE = 64;

    final public static HashMap<Integer,GameObjectConstructor> CONSTRUCTORS = new HashMap<>();
    final public static HashMap<Integer,Color> FILLERS = new HashMap<>();
    static {
        CONSTRUCTORS.put(PLAYER_GRID_CODE, Player::new);
        CONSTRUCTORS.put(NORMAL_BALLOON_GRID_CODE, NormalBalloon::new);
        CONSTRUCTORS.put(DRAGON_BALLOON_GRID_CODE, DragonBalloon::new);

        FILLERS.put(YELLO_WALL_GRID_CODE, Color.rgb(214, 200, 49));
        FILLERS.put(RED_WALL_GRID_CODE, Color.rgb(191, 95, 71));
        FILLERS.put(ORANGE_WALL_GRID_CODE, Color.rgb(255, 171, 25));
    }

    final public static int[][] DEFAULT_GRID = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    };
    final public static int PUMP_GRID_CODE = 9;
}
