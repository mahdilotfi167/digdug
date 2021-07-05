package ir.ac.kntu.models;
import static ir.ac.kntu.Constants.*;

import ir.ac.kntu.Engine;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player extends Sprite {
    // ScaleTransition st;
    // TranslateTransition tt;
    // private Position nextGridPos;
    private double speed;
    private Pump pump;
    private Timeline movation;
    private Vector movement;
    public Player(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, BLOCK_SCALE,BLOCK_SCALE,new ImageView("/assets/player.png"),PLAYER_GRID_CODE);
        // this.speed = 2;
        setSpeed(2);
        // this.movation = new Timeline(new KeyFrame(Duration.millis(1000/speed), e->{
        //     setDirection(movement.getDirection());
        //     if ((getMap().getData(this.getPosition().sum(movement)) & STONE_GRID_CODE) == 0) {
        //         super.move(movement);
        //         getMap().clearBlock((int)this.getPosition().getX(), (int)this.getPosition().getY());
        //     }
        // }));
        // this.nextGridPos = new Position(gridX, gridY);
        // this.pump = new ImageView("/assets/pump.png");
        // this.st = new ScaleTransition(Duration.millis(1000), pump);
        // pump.setFitWidth(BLOCK_SCALE);
        // pump.setFitHeight(BLOCK_SCALE);
        // this.st.setCycleCount(1);
        // st.setByX(3);
        this.pump = new Pump(getMap());
        getMap().addObject(this.pump);
        // this.pump.setVisible(false);
        // this.getChildren().add(pump);
        // tt = new TranslateTransition(Duration.millis(1000),pump);
        // tt.setCycleCount(1);
        this.lastPos = new Position(this.getPosition());
    }
    public void shoot() {
        // pump.setScaleX(1);
        // pump.setVisible(true);
        // Position p = new Position(getLayoutCenterX(), getLayoutCenterY()).sum(getDirection().getDirection(BLOCK_SCALE));
        // Rectangle2D collider = new Rectangle2D(p.getX()-BLOCK_SCALE/2, p.getY()-BLOCK_SCALE/2, arg2, arg3)
        // pump.setLayoutX(p.getX()-BLOCK_SCALE/2);
        // pump.setLayoutY(p.getY()-BLOCK_SCALE/2);
        // pump.setRotate(getDirection().getRotation());
        pump.shoot(this.getPosition().sum(this.getDirection()), getDirection());
        // pump.setVisible(false);
    }
    private Position lastPos;
    @Override
    public void move(Vector movement) {
        if (this.pump.isActive()) {
            return;
        }
        this.movement = movement;
        movation.play();
    }

    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
        this.movation = new Timeline(new KeyFrame(Duration.millis(1000/speed), e->{
            setDirection(movement.getDirection());
            if ((getMap().getData(this.getPosition().sum(movement)) & STONE_GRID_CODE) == 0) {
                super.move(movement);
                getMap().clearBlock((int)this.getPosition().getX(), (int)this.getPosition().getY());
            }
        }));
        this.movation.setCycleCount(1);
    }

    @Override
    public void onCollision(GameObject collider) {
        // System.out.println(collider.getClass().getName());
    }

    @Override
    public void kill() {
        Engine.losePlayer();
        this.getMask().setFitWidth(BLOCK_SCALE);
        this.getMask().setFitHeight(BLOCK_SCALE);
    }

    public Pump getPump() {
        return pump;
    }
}
