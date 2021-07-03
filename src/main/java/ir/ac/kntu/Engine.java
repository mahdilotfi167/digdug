package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Timer;

import ir.ac.kntu.components.PlayerController;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.data.Person;
import ir.ac.kntu.models.Balloon;
import ir.ac.kntu.models.DragonBalloon;
import ir.ac.kntu.models.NormalBalloon;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.random.RandomCreator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Engine {
    private static Engine current;
    private static int playerHealth;
    private static int hiScore;
    private boolean onGame;
    private Stage stage;
    private Scene scene;
    private Pane pane;
    private Map map;
    private static boolean arcade;
    private int remainingTime;
    private Label timeLabel;
    private int round;
    private Label roundLabel;
    public Engine(Person person) {
        current = this;
        drawPane();
        arcade = true;
        onGame = true;
        stage.setOnCloseRequest(e->{
            onGame = false;
        });
        stage.show();
    }
    public Engine(Map map) {
        current = this;
        drawPane();
        arcade = false;
        onGame = true;
        stage.setOnCloseRequest(e->{
            onGame = false;
            map.stopLoop();
        });
        stage.show();
        bootMap(map);
        firstStartGame();
    }
    public void drawPane() {
        this.stage = new Stage();
        this.pane = new Pane();
        this.scene = new Scene(pane,923,627);
        this.stage.setScene(scene);
        pane.setPrefWidth(923);
        pane.setPrefHeight(627);
        VBox box = new VBox();
        pane.getChildren().add(box);
        box.setAlignment(Pos.TOP_CENTER);
        box.setLayoutX(775);
        box.setLayoutY(14);
        box.setPrefHeight(600);
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
        this.timeLabel = getLabel("3:00", 30, 139);
        box.getChildren().addAll(health,round,time,timeLabel);
        // startTimer();
    }

    private void bootMap(Map map) {
        this.remainingTime = 180;
        if (this.map != null) {
            this.pane.getChildren().remove(this.map);
        }
        this.map = map;
        this.pane.getChildren().add(this.map);
        this.map.setLayoutX(14);
        this.map.setLayoutY(14);
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
    private void firstStartGame() {
        new Thread(()->{
            for (int i = 5;i>0;i--) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            startTimer();
            startMap();
        }).start();
    }

    private RandomCreator rc;
    private void startMap() {//* after 5 seconds .........................
        ArrayList<Balloon> balloons = new ArrayList<>();
        balloons.addAll(this.map.collect(DragonBalloon.class));
        balloons.addAll(this.map.collect(NormalBalloon.class));
        Player player = this.map.collect(Player.class).get(0);
        for (Balloon balloon : balloons) {
            balloon.setTarget(player);
        }
        map.startLoop();
        PlayerController pc = new PlayerController(player, KeyCode.UP ,KeyCode.RIGHT ,KeyCode.DOWN,KeyCode.LEFT, KeyCode.SPACE);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, pc::keyHandler);
        // Platform.runLater(()->{
        //     map.addObject(new RandomCreator(map));
        // });
        rc = new RandomCreator(map);
        new Thread(()->{
            while (onGame) {
                Platform.runLater(()->{rc.createRandom();});
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void startTimer() {
        Thread timerThread = new Thread(()->{
            while(onGame && remainingTime-->0) {
                Platform.runLater(()->{
                    this.timeLabel.setText((remainingTime/60)+":"+(remainingTime%60));
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //finish game
        });
        timerThread.start();
    }
    private ImageView getLogo() {
        ImageView res = new ImageView("/assets/play.png");
        res.setFitWidth(28);
        res.setFitHeight(29);
        return res;
    }
    private static void setPlayerHealth(int health) {
        playerHealth = 0;
        for (int i = 0;i<health;i++) {
            increasePlayerHealth();
        }
    }
    public static void losePlayer() {

    }
    public static void increasePlayerHealth() {
        
    }
    public static void increaseScore() {

    }
    public static void winedPlayer() {

    }
}
