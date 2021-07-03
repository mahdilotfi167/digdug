package ir.ac.kntu;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import ir.ac.kntu.data.MapSerializer;
import ir.ac.kntu.data.Person;
import ir.ac.kntu.data.PersonDao;
import ir.ac.kntu.data.SerializedPersonDao;
import ir.ac.kntu.map.MapBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static ir.ac.kntu.Constants.*;
public class MenuHandler {
    private Stage stage;
    private Scene scene;
    private PersonDao dao;
    // private GridPane customMenu = new GridPane();
    private GridPane mainMenu = new GridPane();
    public MenuHandler(Stage stage) {
        this.dao = new SerializedPersonDao("persons");
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
        // ListView<Text> lv = new ListView<>();
        TableView<Person> lv = new TableView<>();
        lv.setPrefHeight(573);
        lv.setPrefWidth(337);
        lv.setStyle("-fx-background-color: #BECBDE;");
        TableColumn<Person,String> tc1 = new TableColumn<>("Name");
        tc1.setPrefWidth(223);
        tc1.setStyle("-fx-font-size: 18px;");
        tc1.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Person,Integer> tc2 = new TableColumn<>("Record");
        tc2.setPrefWidth(112);
        tc2.setStyle("-fx-font-size: 18px;");
        tc2.setCellValueFactory(new PropertyValueFactory<>("hiScore"));
        lv.getColumns().add(tc1);
        lv.getColumns().add(tc2);
        List<Person> persons = dao.all();
        Collections.sort(persons);
        lv.getItems().addAll(persons);
        lv.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent e) {
                new Engine(new MapSerializer("src/main/resources/map/arcade/1").load(CONSTRUCTORS, FILLERS, BLOCK_SCALE ,Color.BLACK));
            }
        });
        VBox box = new VBox();
        TextField tf = new TextField();
        tf.setPrefHeight(49);
        tf.setPrefWidth(297);
        tf.setPromptText("Enter name");
        tf.getStylesheets().add("/css/main.css");
        Button add = getButton("Add", e->{
            Person p = new Person(tf.getText(), 0);
            saveNew(p);
            lv.getItems().add(p);
        });
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
    private void saveNew(Person person) {
        this.dao.add(person);
    }
    private List<Image> images;
    private List<String> filenumbers;
    private int imageIndex;
    private void drawBuildMenu() {
        filenumbers = getImages();
        images = filenumbers.stream()
        .map(filenumber->new Image("/map/custom/"+filenumber+".png"))//todo bug
        .collect(Collectors.toList());
        imageIndex = 100*images.size();
        GridPane build = getSubGrid();
        this.scene.setRoot(build);
        VBox box1 = new VBox();
        box1.setAlignment(Pos.CENTER);
        ImageView img = new ImageView();
        img.setFitHeight(427);
        img.setFitWidth(335);
        img.setPickOnBounds(true);
        img.setPreserveRatio(true);
        img.setImage(images.get(imageIndex%images.size()));
        Button next = getButton("Next", e->{img.setImage(images.get(++imageIndex%images.size()));});
        Button prev = getButton("Prev", e->{img.setImage(images.get(--imageIndex%images.size()));});
        box1.getChildren().add(next);
        box1.getChildren().add(img);
        box1.getChildren().add(prev);

        VBox box2 = new VBox();
        box2.setAlignment(Pos.CENTER);
        Button add = getButton("New map", e->{new MapBuilder();});
        Button play = getButton("Play", e->{
            new Engine(new MapSerializer("src/main/resources/map/custom/"+filenumbers.get(imageIndex%images.size())).load(CONSTRUCTORS, FILLERS, BLOCK_SCALE ,Color.BLACK));
            // System.out.println(images.get(imageIndex%images.size()).getUrl());
        });
        Button back = getButton("Back", e->{drawMainMenu();});
        box2.getChildren().add(add);
        box2.getChildren().add(play);
        box2.getChildren().add(back);
        VBox.setMargin(play, new Insets(20, 0, 20, 0));

        build.add(box1, 0, 0);
        build.add(box2, 1, 0);
    }

    private List<String> getImages() {
        File folder = new File("src/main/resources/map/custom/");
        if (folder == null || folder.list()==null) {
            return new ArrayList<>();
        }
        return List.of(folder.list()).stream()
            .filter(filename->filename.endsWith(".png"))
            .map(filename->filename.replaceAll(".png", ""))
            .collect(Collectors.toList());
    }
}