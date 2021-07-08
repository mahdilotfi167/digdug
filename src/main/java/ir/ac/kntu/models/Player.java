package ir.ac.kntu.models;

import static ir.ac.kntu.Constants.*;
import ir.ac.kntu.Engine;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Player extends Sprite {
    private double speed;
    private Pump pump;
    private Timeline movation;
    private Vector movement;

    public Player(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, new ImageView("/assets/player.png"), PLAYER_GRID_CODE);
        setSpeed(3);
        this.pump = new Pump(getMap());
        getMap().addObject(this.pump);
    }

    public void shoot() {
        pump.shoot(this.getPosition().sum(this.getDirection()), getDirection());
    }

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
        this.movation = new Timeline(new KeyFrame(Duration.millis(1000 / speed), e -> {
            setDirection(movement.getDirection());
            if ((getMap().getData(this.getPosition().sum(movement)) & STONE_GRID_CODE) == 0) {
                super.move(movement);
                getMap().clearBlock((int) this.getPosition().getX(), (int) this.getPosition().getY());
            }
        }));
        this.movation.setCycleCount(1);
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