package ir.ac.kntu.models;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.rigidbody.Vector;
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
public class Pump extends Object {
    private Vector lastDir;
    private Circle circle;
    public Pump(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, new ImageView("/assets/pump.png"), PUMP_GRID_CODE);
        this.getMask().setVisible(false);
        this.getChildren().remove(getMap());
        circle = new Circle(this.getLayoutX(), this.getLayoutY(), 3, Color.RED);
        getChildren().add(circle);
    }

    @Override
    public void setCurrentLayoutX(double x) {
        this.circle.setLayoutX(x);
        super.setCurrentLayoutX(x);
    }
    @Override
    public void setCurrentLayoutY(double y) {
        this.circle.setLayoutY(y);
        super.setCurrentLayoutY(y);
    }
    private boolean isActive;
    // private Position pos = new Position(0,0);
    public void shoot(Position position,Vector direction) {
        circle.setVisible(true);
        this.setCurrentLayoutX(position.getX());
        this.setCurrentLayoutY(position.getY());
        Timeline tl = new Timeline(
            new KeyFrame(Duration.millis(10),e->{
                setCurrentLayoutX(getCurrentLayoutX()+direction.getX());
                setCurrentLayoutY(getCurrentLayoutY()+direction.getY());
            }) 
        );
        tl.setOnFinished(e -> {
            // getChildren().add(new Circle(view.getLayoutX(),view.getLayoutY(),5, Color.RED));
            circle.setVisible(false);
        });
        tl.setCycleCount(3*BLOCK_SCALE);
        tl.play();   
        // this.getMask().setRotate(direction.getRotation());
        // this.lastDir = direction;
        // pos.setX(position.getX()-BLOCK_SCALE/2);
        // pos.setY(position.getY()-BLOCK_SCALE/2);
    }
}
