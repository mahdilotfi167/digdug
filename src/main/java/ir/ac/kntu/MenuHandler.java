package ir.ac.kntu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuHandler {
    Stage stage;
    Scene scene;
    // private GridPane customMenu = new GridPane();
    private GridPane mainMenu = new GridPane();
    public MenuHandler(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(mainMenu);
        this.stage.setScene(this.scene);
        drawMainMenu();
    }
    private void drawMainMenu() {
        mainMenu = getMainGrid();
        this.scene.setRoot(mainMenu);
        // * components
        ImageView img = new ImageView("/img/Logo.png");
        img.setFitHeight(553);
        img.setFitWidth(431);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        Button arcade = getButton("Arcade", e->{drawArcadeMenu();});
        Button custom = getButton("Custom", e->{drawBuildMenu();});
        mainMenu.add(arcade, 1, 0);
        mainMenu.add(custom, 1, 1);
        mainMenu.add(img, 0, 1);
        //*---------------------------
    }
    private Button getButton(String text,EventHandler<ActionEvent> handler) {
        Button btn = new Button();
        btn.setText(text);
        btn.setMnemonicParsing(false);
        btn.setPrefHeight(64);
        btn.setPrefWidth(216);
        btn.getStylesheets().add("/css/main.css");
        btn.setOnAction(handler);
        return btn;
    }
    private GridPane getMainGrid() {
        GridPane res = new GridPane();
        res.setPrefHeight(550);
        res.setPrefWidth(674);
        // * columns
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setHgrow(Priority.SOMETIMES);
        cc1.setMinWidth(10);
        cc1.setPrefWidth(100);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHalignment(HPos.CENTER);
        cc2.setHgrow(Priority.SOMETIMES);
        cc2.setMinWidth(10);
        cc2.setPrefWidth(100);
        res.getColumnConstraints().add(cc1);
        res.getColumnConstraints().add(cc2);
        // * rows
        RowConstraints rc1 = new RowConstraints();
        rc1.setMinHeight(10);
        rc1.setPrefHeight(30);
        rc1.setVgrow(Priority.SOMETIMES);
        RowConstraints rc2 = new RowConstraints();
        rc2.setMinHeight(10);
        rc2.setPrefHeight(30);
        rc2.setVgrow(Priority.SOMETIMES);
        RowConstraints rc3 = new RowConstraints();
        rc3.setMinHeight(10);
        rc3.setPrefHeight(30);
        rc3.setVgrow(Priority.SOMETIMES);
        res.getRowConstraints().add(rc1);
        res.getRowConstraints().add(rc2);
        res.getRowConstraints().add(rc3);
        return res;
    }
    private GridPane getSubGrid() {
        GridPane res = new GridPane();
        res.setPrefHeight(550);
        res.setPrefWidth(674);
        // * columns
        ColumnConstraints cc1 = new ColumnConstraints();
        cc1.setHgrow(Priority.SOMETIMES);
        cc1.setMinWidth(10);
        cc1.setPrefWidth(100);
        ColumnConstraints cc2 = new ColumnConstraints();
        cc2.setHalignment(HPos.CENTER);
        cc2.setHgrow(Priority.SOMETIMES);
        cc2.setMinWidth(10);
        cc2.setPrefWidth(100);
        res.getColumnConstraints().add(cc1);
        res.getColumnConstraints().add(cc2);
        // * rows
        RowConstraints rc1 = new RowConstraints();
        rc1.setMinHeight(10);
        rc1.setPrefHeight(30);
        rc1.setVgrow(Priority.SOMETIMES);
        res.getRowConstraints().add(rc1);
        return res;
    }
    private void drawArcadeMenu() {
        GridPane arcade = getSubGrid();
        this.scene.setRoot(arcade);
        ListView<Text> lv = new ListView<>();
        lv.setPrefHeight(573);
        lv.setPrefWidth(337);
        VBox box = new VBox();
        TextField tf = new TextField();
        tf.setPrefHeight(49);
        tf.setPrefWidth(297);
        tf.setPromptText("Enter name");
        tf.getStylesheets().add("/css/main.css");
        Button add = getButton("Add", e->{});
        Button back = getButton("Back", e->{drawMainMenu();});
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(tf);
        box.getChildren().add(add);
        box.getChildren().add(back);
        VBox.setMargin(tf, new Insets(20,20, 20, 20));
        VBox.setMargin(back, new Insets(20, 0, 20, 0));
        arcade.add(lv, 0, 0);
        arcade.add(box, 1, 0);
    } 
    private void drawBuildMenu() {
        GridPane build = getSubGrid();
        this.scene.setRoot(build);
        VBox box1 = new VBox();
        box1.setAlignment(Pos.CENTER);
        ImageView img = new ImageView();
        img.setFitHeight(427);
        img.setFitWidth(335);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        Button next = getButton("Next", e->{});
        Button prev = getButton("Prev", e->{});
        box1.getChildren().add(next);
        box1.getChildren().add(img);
        box1.getChildren().add(prev);

        VBox box2 = new VBox();
        box2.setAlignment(Pos.CENTER);
        Button add = getButton("New map", e->{});
        Button play = getButton("Play", e->{});
        Button back = getButton("Back", e->{drawMainMenu();});
        box2.getChildren().add(add);
        box2.getChildren().add(play);
        box2.getChildren().add(back);
        VBox.setMargin(play, new Insets(20, 0, 20, 0));

        build.add(box1, 0, 0);
        build.add(box2, 1, 0);
    }
}