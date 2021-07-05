package ir.ac.kntu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

import ir.ac.kntu.components.PathFinder;
import ir.ac.kntu.components.PlayerController;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.GameObjectConstructor;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.data.MapSerializer;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.balloon.Balloon;
import ir.ac.kntu.models.balloon.DragonBalloon;
import ir.ac.kntu.models.balloon.NormalBalloon;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static ir.ac.kntu.Constants.*;
public class JavaFxApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        // Pane root = new Pane();
        int[][] grid = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,32,0,0,0,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,0,0,0,0,8,0,0,0,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,16,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        };
        // HashMap<Integer,GameObjectConstructor> constructors = new HashMap<>();
        // constructors.put(PLAYER_GRID_CODE, Player::new);
        // constructors.put(NORMAL_BALLOON_GRID_CODE, NormalBalloon::new);
        // constructors.put(DRAGON_BALLOON_GRID_CODE, DragonBalloon::new);
    
        Map root = new Map(grid,CONSTRUCTORS,FILLERS,BLOCK_SCALE,Color.BLACK);
        Scene scene = new Scene(root, 800, 600, Color.rgb(240, 240, 240));
        Player player = root.collect(Player.class).get(0);
        
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
        
        // stage.setScene(scene);
        stage.show();
        // stage.setOnCloseRequest(e->{
            // System.exit(0);
        // });
    }
}
