package ir.ac.kntu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import ir.ac.kntu.components.PathFinder;
import ir.ac.kntu.components.PlayerController;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.models.DragonBalloon;
import ir.ac.kntu.models.GameObject;
import ir.ac.kntu.models.NormalBalloon;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.Wall;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.util.GameObjectConstructor;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,2,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        };
        HashMap<Integer,GameObjectConstructor> constructors = new HashMap<>();
        constructors.put(2, Player::new);
        // constructors.put(4, NormalBalloon::new);
        // constructors.put(5, DragonBalloon::new);
        
        HashMap<Integer,Color> fillers = new HashMap<>();
        fillers.put(1, Color.rgb(214, 200, 49));
        Map root = new Map(grid,constructors,fillers,BLOCK_SCALE,Color.BLACK);
        // PathFinder pathFinder = new PathFinder(root);
        // PathFinder.Path p = pathFinder.find(new Position(2, 2), new Position(12, 5));
        // System.out.println(p.lenght());
        // while(p.hasNext()) {
            // System.out.println(p.next());
        // }
        // root.setStyle("-fx-border-width: 0 0 5 0;-fx-background-color:black");
        Scene scene = new Scene(root, 800, 600, Color.rgb(240, 240, 240));
        // try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("test.map")))) {
        //     out.writeObject(root);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("test.map")))) {
        //     Map m = (Map)in.readObject();
        //     System.out.println(m.getStyle());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // Player p2 = (Player)root.getObject(1, 1);
        // scene.setOnKeyPressed(player::keyHandler);
        Player player = (Player)root.getObject(2, 2);
        DragonBalloon db = (DragonBalloon)root.getObject(13, 5);
        db.setTarget(player);
        PlayerController pc = new PlayerController(player, KeyCode.UP ,KeyCode.RIGHT ,KeyCode.DOWN,KeyCode.LEFT, KeyCode.SPACE);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, pc::keyHandler);
        player.setSpeed(2);

        scene.setOnMouseClicked(e->{
            root.printGrid();
        });
        GameObject.startLoop();
        // PlayerController pc2 = new PlayerController(p2, KeyCode.W ,KeyCode.D ,KeyCode.S,KeyCode.A, KeyCode.ENTER);
        // scene.setOnKeyPressed(pc::keyHandler);
        // scene.addEventHandler(KeyEvent.KEY_PRESSED, pc2::keyHandler);
        //*========================================
        //*========================================

        // Setting stage properties
        // stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("DigDig");
        
        stage.setScene(scene);
        stage.show();
        // stage.setOnCloseRequest(e->{
            // System.exit(0);
        // });
    }
}
