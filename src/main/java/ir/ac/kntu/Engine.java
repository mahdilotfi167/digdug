package ir.ac.kntu;

import ir.ac.kntu.map.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Engine {
    private Stage stage;
    private Scene scene;
    private Pane pane;
    public Engine() {
        this.stage = new Stage();
        this.pane = new Pane();
        this.scene = new Scene(pane,832,528);
        this.stage.setScene(scene);
        drawPane();
        stage.show();
    }
    public void drawPane() {
        pane.setPrefWidth(832);
        pane.setPrefHeight(528);
        VBox box = new VBox();
        pane.getChildren().add(box);
        box.setAlignment(Pos.TOP_CENTER);
        box.setLayoutX(684);
        box.setLayoutY(14);
        box.setPrefHeight(501);
        box.setPrefWidth(134);
        box.setStyle("-fx-background-color: black;");
        // Label hiScoreLbl = new Label("HI-SCORE");
        Label hiScoreLbl = getLabel("HI-SCORE", 0, 0);
        box.getChildren().add(hiScoreLbl);
        hiScoreLbl.setStyle("-fx-text-fill: red; -fx-font-size: 20px;");
        VBox.setMargin(hiScoreLbl, new Insets(10,10,10,10));
        Label hiScore = getLabel("0", 30, 10);
        box.getChildren().add(hiScore);

        HBox health = new HBox();
        health.setAlignment(Pos.CENTER_LEFT);
        health.setPrefHeight(39);
        health.setPrefWidth(134);
        health.getChildren().add(getLogo());
        health.getChildren().add(getLogo());
        health.getChildren().add(getLogo());
        Label round = getLabel("ROUND", 30, 40);
        Label time = getLabel("TIME", 30, 109);
        Label timer = getLabel("3:00", 30, 139);
        box.getChildren().addAll(health,round,time,timer);
    }
    private Label getLabel(String text,double x,double y) {
        Label res = new Label(text);
        res.setPrefHeight(30);
        res.setPrefWidth(94);
        res.setLayoutX(x);
        res.setLayoutY(y);
        res.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        res.getStylesheets().add("css/main.css");
        return res;
    }
    private ImageView getLogo() {
        ImageView res = new ImageView("/assets/play.png");
        res.setFitWidth(28);
        res.setFitHeight(29);
        return res;
    }
}
