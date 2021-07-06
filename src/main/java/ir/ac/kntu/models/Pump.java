package ir.ac.kntu.models;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import static ir.ac.kntu.Constants.*;

import java.util.Timer;
import java.util.TimerTask;

import ir.ac.kntu.ThreadPool;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import ir.ac.kntu.models.balloon.Balloon;
public class Pump extends Object {
    private Circle circle;
    private int range;
    private boolean active;
    private int counter;
    private int lenght;
    private int dir;
    private boolean attached;
    // private Timeline shooter = new Timeline();
    public Pump(Map map) {
        super(map, 0,0, BLOCK_SCALE, BLOCK_SCALE,new ImageView(), 0);
        circle = new Circle(this.getLayoutX(), this.getLayoutY(), 3, Color.RED);
        getChildren().add(circle);
        circle.setLayoutX(BLOCK_SCALE/2);
        circle.setLayoutY(BLOCK_SCALE/2);
        circle.setVisible(false);
        // shooter.getKeyFrames().add(new KeyFrame(Duration.millis(100), e->move(this.getDirection())));//!danger collision detection
        // shooter.setOnFinished(e->{this.active=false;circle.setVisible(false);});
        setRange(3);
    }
    public void setRange(int range) {
        this.range = range;
        // this.shooter.setCycleCount(range);
    }
    public void shoot(Position position,Vector direction) {
        if (!this.active) {
            this.lenght = 0;
            this.getPosition().setX(position.getX());
            this.getPosition().setY(position.getY());
            this.setDirection(direction.getDirection());
            this.active = true;
            circle.setVisible(true);
            this.dir=1;
            attached = false;
            // shooter.play();
        }
        // this.setCurrentLayoutX(position.getX());
        // this.setCurrentLayoutY(position.getY());
            // getChildren().add(new Circle(view.getLayoutX(),view.getLayoutY(),5, Color.RED));
        // this.getMask().setRotate(direction.getRotation());
        // this.lastDir = direction;
        // pos.setX(position.getX()-BLOCK_SCALE/2);
        // pos.setY(position.getY()-BLOCK_SCALE/2);
    }
    @Override
    public void update() {
        // if (counter++%1 == 0) {
            if (this.active) {
                if (lenght == range-1 && dir>0) {
                    this.setDirection(getDirection().multiply(-1));
                    dir=-dir;
                }
                this.move(this.getDirection());
                lenght+=dir;
                if (lenght == 0 && dir<0) {
                    this.active = false;
                    this.circle.setVisible(false);
                }
            }
        // }
    }
    @Override
    public void onCollision(GameObject collider) {
        if (this.active && !attached) {
                if (collider instanceof Balloon) {
                    Balloon b = (Balloon)collider;
                    b.inflate();
                    this.setDirection(getDirection().multiply(-1));
                    dir=-dir;
                    attached = true;
                    // this.move(this.getDirection().multiply(-1));
                    // lenght+=dir;
                }
            }
        }
    public boolean isActive() {
        return this.active;
    }
}
