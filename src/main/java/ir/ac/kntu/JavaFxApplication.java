package ir.ac.kntu;

import static ir.ac.kntu.Constants.BLOCK_SCALE;
import static ir.ac.kntu.Constants.CONSTRUCTORS;
import static ir.ac.kntu.Constants.FILLERS;

import ir.ac.kntu.core.Map;
import ir.ac.kntu.models.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class JavaFxApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        // Pane root = new Pane();
        // int[][] grid = {
        // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,32,0,0,0,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,0,0,0,0,8,0,0,0,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,0,0,0,16,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        // };
        // HashMap<Integer,GameObjectConstructor> constructors = new HashMap<>();
        // constructors.put(PLAYER_GRID_CODE, Player::new);
        // constructors.put(NORMAL_BALLOON_GRID_CODE, NormalBalloon::new);
        // constructors.put(DRAGON_BALLOON_GRID_CODE, DragonBalloon::new);
    
        // Map root = new Map(grid,CONSTRUCTORS,FILLERS,BLOCK_SCALE,Color.BLACK);
        // Scene scene = new Scene(root, 800, 600, Color.rgb(240, 240, 240));
        // Player player = root.collect(Player.class).get(0);
        
        // ArrayList<Balloon> balloons = new ArrayList<>();
        // balloons.addAll(root.collect(DragonBalloon.class));
        // balloons.addAll(root.collect(NormalBalloon.class));

        // for (Balloon balloon : balloons) {
            // balloon.setTarget(player);
        // }

        // PlayerController pc = new PlayerController(player, KeyCode.UP ,KeyCode.RIGHT ,KeyCode.DOWN,KeyCode.LEFT, KeyCode.SPACE);
        // scene.addEventHandler(KeyEvent.KEY_PRESSED, pc::keyHandler);
        // ArrayList<DragonBalloon> res = root.collect(DragonBalloon.class);
        // System.out.println(res);
        // scene.setOnMouseClicked(e->{
            // root.printGrid();
        // });
        // MapSerializer.export(root);
        // GameObject.startLoop();
        //*========================================
        // MapSerializer.load();
        //*========================================
        MenuHandler mh = new MenuHandler(stage);

        // new Thread(()->{
            // for (int i = 0;i<10;i++) {
                // System.out.println(i);
                // try {
                    // Thread.sleep(1000);
                // } catch (Exception e) {
                    // e.printStackTrace();
                // }
            // }
        // }).start();
        // Setting stage properties
        // stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("DigDig");
        stage.show();
        // stage.setOnCloseRequest(e->{
            // System.exit(0);
        // });
    }
}
