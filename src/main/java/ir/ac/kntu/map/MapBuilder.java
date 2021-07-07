package ir.ac.kntu.map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.GameObjectConstructor;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.data.MapSerializer;
import static ir.ac.kntu.Constants.*;

public class MapBuilder extends Pane {
    private Map map;
    private Stage stage;
    private Scene scene;
    private Pane pane;
    private int currentFiller = 0;

    public MapBuilder() {
        this.stage = new Stage();
        this.pane = new Pane();
        this.scene = new Scene(pane,900,627);
        this.stage.setScene(scene);
        this.map = new Map(new int[20][25], CONSTRUCTORS, FILLERS, BLOCK_SCALE, Color.BLACK);
        this.pane.getChildren().add(map);
        drawPane();
        stage.show();
        this.pane.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e) {
                if (map.isBlock(map.layoutToGrid(e.getX()), map.layoutToGrid(e.getY()))) {
                    map.clearBlock(map.layoutToGrid(e.getX()), map.layoutToGrid(e.getY()));
                } else if (currentFiller != 0) {
                    map.fill(currentFiller, map.layoutToGrid(e.getX()), map.layoutToGrid(e.getY()));
                }
            }
        });
        this.stage.setOnCloseRequest(e->{
            Platform.runLater(()->{
                int lastname = getLastId()+1;    
                try {
                    File file = new File("src/main/resources/map/custom/"+lastname+".png");
                    file.getParentFile().mkdirs();
                    ImageIO.write(SwingFXUtils.fromFXImage(map.snapshot(new SnapshotParameters(), null), null), "png", file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                new MapSerializer("src/main/resources/map/custom/"+lastname).export(this.map);
            });
        });
    }
    private int getLastId() {
        File folder = new File("src/main/resources/map/custom/");
        if (folder == null || folder.list()==null) {
            return 1;
        }
        return List.of(folder.list()).stream()
            .filter(filename->filename.endsWith(".map"))
            .map(filename->Integer.parseInt(filename.replaceAll(".map$", "")))
            .max(Integer::compareTo).get();
    }

    public void drawPane() {
        pane.setPrefWidth(900);
        pane.setPrefHeight(627);
        this.map.setLayoutX(14);
        this.map.setLayoutY(14);
        VBox box = new VBox();
        pane.getChildren().add(box);
        box.setAlignment(Pos.TOP_CENTER);
        box.setLayoutX(775);
        box.setLayoutY(14);
        box.setPrefHeight(600);
        box.setPrefWidth(110);
        box.setStyle("-fx-background-color: black;");
        for (java.util.Map.Entry<Integer,GameObjectConstructor> entry : CONSTRUCTORS.entrySet()) {
            Button btn = getButton(entry.getValue().getObject(this.map, 0, 0).getClass().getSimpleName(), e->{
                GameObject draggable = new Draggable(entry.getValue().getObject(map, 12, 10));
                draggable.getMask().addEventFilter(MouseEvent.MOUSE_PRESSED,event->{
                    currentFiller = 0;
                });
                this.map.addObject(draggable);
                currentFiller = 0;
            });
            box.getChildren().add(btn);
            VBox.setMargin(btn, new Insets(2, 0, 2, 0));
        }
        for (java.util.Map.Entry<Integer,Color> entry : FILLERS.entrySet()) {
            Button btn = getButton(entry.getValue().toString(), e->{
                this.currentFiller = entry.getKey();
            });
            box.getChildren().add(btn);
            VBox.setMargin(btn, new Insets(2, 0, 2, 0));
        }
    }

    private Button getButton(String text,EventHandler<ActionEvent> handler) {
        Button btn = new Button();
        btn.setText(text);
        btn.setMnemonicParsing(false);
        btn.setPrefHeight(20);
        btn.setPrefWidth(104);
        btn.getStylesheets().add("/css/main.css");
        btn.setStyle("-fx-font-size:10px");
        btn.setOnAction(handler);
        return btn;
    }
}