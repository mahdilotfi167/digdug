package ir.ac.kntu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ir.ac.kntu.map.Map;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JavaFxApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        // Pane root = new Pane();
        Pane root = new Map();
        root.setStyle("-fx-border-width: 0 0 5 0;");
        Scene scene = new Scene(root, 800, 600, Color.rgb(240, 240, 240));
        // try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("test.map")))) {
        //     out.writeObject(root);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("test.map")))) {
            Map m = (Map)in.readObject();
            System.out.println(m.getStyle());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Setting stage properties
        // stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("DigDig");
        
        stage.setScene(scene);
        stage.show();
    }
}
