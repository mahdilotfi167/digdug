package ir.ac.kntu;

import java.io.File;
import java.util.ArrayList;
import ir.ac.kntu.components.PlayerController;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.data.BinaryWriter;
import ir.ac.kntu.data.MapSerializer;
import ir.ac.kntu.data.Person;
import ir.ac.kntu.data.SerializedPersonDao;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.balloon.Balloon;
import ir.ac.kntu.models.balloon.DragonBalloon;
import ir.ac.kntu.models.balloon.NormalBalloon;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static ir.ac.kntu.Constants.*;


public class Engine {
    private static Engine current;
    private int playerHealth;
    private int hiScore;
    private boolean onGame;
    private Stage stage;
    private Scene scene;
    private Pane pane;
    private Map map;
    private boolean arcade;
    private int remainingTime;
    private Label timeLabel;
    private int round;
    private Label roundLabel;
    private Label scoreLabel;
    private Person person;
    private int enemiesCount;
    public Engine(Person person) {
        this.person = person;
        current = this;
        this.remainingTime = 180;
        this.round = 1;
        this.playerHealth = 3;
        // this.playerHealth = 3;
        drawPane();

        File dat = new File("save/"+this.person.getName()+".dat");
        if (dat.exists()) {
            bootMap(new MapSerializer("save/"+this.person.getName()).load(CONSTRUCTORS, FILLERS, BLOCK_SCALE ,Color.BLACK));
            int[] data = new BinaryWriter("save/"+this.person.getName()).get();
            this.remainingTime = data[0];
            this.round = data[1];
            this.hiScore = data[2];
            this.playerHealth = data[3];
            new File("save/"+this.person.getName()+".map").delete();
            dat.delete();
        } else {
            bootMap(new MapSerializer("src/main/resources/map/arcade/"+round).load(CONSTRUCTORS, FILLERS, BLOCK_SCALE ,Color.BLACK));
        }
        setPlayerHealth(playerHealth);
        startTimer();

        arcade = true;
        onGame = true;
        stage.show();
        firstStartGame();
        stage.setOnCloseRequest(e->{
            if (onGame) {
                new File("save/").mkdirs();
                new MapSerializer("save/"+this.person.getName()).export(this.map);
                new BinaryWriter("save/"+this.person.getName()).write(new int[]{remainingTime,round,hiScore,playerHealth});
            }
            onGame = false;
            map.stopLoop();
        });

        scoreLabel.setText(""+this.hiScore);
    }
    public Engine(Map map) {
        current = this;
        drawPane();
        this.remainingTime = 180;
        setPlayerHealth(3);
        arcade = false;
        onGame = true;
        stage.setOnCloseRequest(e->{
            onGame = false;
            map.stopLoop();
        });
        stage.show();
        bootMap(map);
        firstStartGame();
        startTimer();
    }
    private HBox health = new HBox();
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
        scoreLabel = getLabel("0", 30, 10);
        box.getChildren().add(scoreLabel);
        health.setAlignment(Pos.CENTER_LEFT);
        health.setPrefHeight(39);
        health.setPrefWidth(134);
        roundLabel = getLabel("ROUND "+this.round, 30, 40);
        Label time = getLabel("TIME", 30, 109);
        this.timeLabel = getLabel("3:00", 30, 139);
        box.getChildren().addAll(health,roundLabel,time,timeLabel);
        // startTimer();
    }

    private void bootMap(Map map) {
        if (this.map != null) {
            this.map.stopLoop();
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

    private Label index = new Label();
    private int startTime;
    private void firstStartGame() {//* start 5 seconds ...............
        new Thread(()->{
            Platform.runLater(()->{
                index.setLayoutX(100);
                index.setLayoutY(100);
                index.setStyle("-fx-text-fill: #ffffff;-fx-font-size: 100px");    
                pane.getChildren().add(index);
            });
            for (startTime = 5;startTime>0;startTime--) {
                try {
                    Platform.runLater(()->{
                        index.setText(""+startTime);
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(()->{
                pane.getChildren().remove(index);
            });
            startMap();
        }).start();
    }

    private RandomCreator rc;
    private void startMap() {//* after 5 seconds .........................
        ArrayList<Balloon> balloons = new ArrayList<>();
        balloons.addAll(this.map.collect(DragonBalloon.class));
        balloons.addAll(this.map.collect(NormalBalloon.class));
        this.enemiesCount = balloons.size();
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
        Thread randomer = new Thread(()->{
            while (onGame) {
                Platform.runLater(()->{rc.createRandom();});
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        randomer.setDaemon(true);
        randomer.start();
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
            finishGame();
        });
        timerThread.start();
    }

    private void finishGame() {
        if (this.arcade) {
            if (this.person.getHiScore() < this.getHiScore()) {
                person.setHiScore(this.hiScore);
                new SerializedPersonDao("persons").update(person);
            }
        }
        this.onGame = false;
        map.stopLoop();
        Platform.runLater(()->{
            this.stage.close();
        });
    }

    private ImageView getLogo() {
        ImageView res = new ImageView("/assets/play.png");
        res.setFitWidth(28);
        res.setFitHeight(29);
        return res;
    }
    private void setPlayerHealth(int health) {
        current.health.getChildren().clear();
        for (int i = 0;i<health;i++) {
            current.health.getChildren().add(current.getLogo());
            // increasePlayerHealth();
        }
        current.playerHealth = health;
    }
    public static void losePlayer() {
        // current.playerHealth--;
        // current.health.getChildren().clear();
        // for (int i = 0;i<current.playerHealth;i++) {
        //     current.health.getChildren().add(current.getLogo());
        // }
        current.setPlayerHealth(current.playerHealth-1);

        if (current.playerHealth == 0) {
            current.finishGame();
        }
        current.map.resetSpawnPositions();
    }
    public static void increasePlayerHealth() {
        // current.playerHealth++;
        current.setPlayerHealth(current.playerHealth+1);
    }
    public void setHiScore(int hiScore) {
        this.hiScore = hiScore;
        scoreLabel.setText(""+hiScore);
    }
    public int getHiScore() {
        return hiScore;
    }
    public static void increaseScore(int score) {
        current.setHiScore(current.getHiScore()+score);
    }
    public void winedPlayer() {
        if (!this.arcade) {
            finishGame();
            return;
        }
        this.round++;
        this.roundLabel.setText("ROUND "+round);
        this.remainingTime = 180;
        if (new File("src/main/resources/map/arcade/"+round+".map").exists()) {
            bootMap(new MapSerializer("src/main/resources/map/arcade/"+round).load(CONSTRUCTORS, FILLERS, BLOCK_SCALE ,Color.BLACK));
            firstStartGame();
        } else {
            finishGame();
        }
    }
    public static void deadBalloon() {
        current.enemiesCount--;
        if (current.enemiesCount==0) {
            current.winedPlayer();
        }
    }
}
