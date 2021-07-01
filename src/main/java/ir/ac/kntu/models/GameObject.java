package ir.ac.kntu.models;

import ir.ac.kntu.map.Map;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.rigidbody.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import static ir.ac.kntu.Constants.*;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class GameObject extends Parent implements Serializable {
    private static ArrayList<GameObject> world;
    private static Timeline collisionChecker;
    static {
        world = new ArrayList<>();
        collisionChecker = new Timeline();
        collisionChecker.getKeyFrames().add(new KeyFrame(Duration.millis(40), e -> {
            for (int i = 0; i < world.size(); i++) {
                world.get(i).update();
                for (int j = i+1; j < world.size(); j++) {
                    if (world.get(i).getPosition().equals(world.get(j).position)) {
                        world.get(i).onCollision(world.get(j));
                        world.get(j).onCollision(world.get(i));
                    }
                }
            }
        }));
    }

    public static void startLoop() {
        collisionChecker.setCycleCount(Timeline.INDEFINITE);
        collisionChecker.play();
    }

    private ImageView mask;
    private Position position;
    private Vector direction;
    private int width;
    private int height;
    private Map map;
    private int gridCode;

    public GameObject(Map map, int gridX, int gridY, int width, int height, int gridCode, ImageView mask) {
        world.add(this);
        this.position = new Position(gridX, gridY);
        this.width = width;
        this.height = height;
        this.map = map;
        this.gridCode = gridCode;
        this.direction = new Vector(1, 0);
        this.mask = mask;
        // this.mask.setLayoutX(0);
        // this.mask.setLayoutY(0);
        this.mask.setFitWidth(width);
        this.mask.setFitHeight(height);
        getChildren().add(mask);

        position.addXListener((oldval, newval) -> {
            this.setLayoutX(getMap().gridToLayout(newval.intValue()));
            // this.mask.setLayoutX(0);
            getMap().updateObjectPos(this, oldval.intValue(), this.getGridY());
        });
        position.addYListener((oldval, newval) -> {
            this.setLayoutY(getMap().gridToLayout(newval.intValue()));
            // this.mask.setLayoutY(0);
            getMap().updateObjectPos(this, this.getGridX(), oldval.intValue());
        });
        this.position.setX(gridX);
        this.position.setY(gridY);
    }

    // protected void setX() {

    // }

    public double getCurrentLayoutX() {
        return getMap().gridToLayout((int) this.position.getX());
    }

    public double getCurrentLayoutY() {
        return getMap().gridToLayout((int) this.position.getY());
    }

    public Position getCenterPos() {
        return new Position(getLayoutCenterX(), getLayoutCenterY());
    }

    public double getLayoutCenterX() {
        return this.getCurrentLayoutX() + width / 2;
    }

    public double getLayoutCenterY() {
        return this.getCurrentLayoutY() + height / 2;
    }

    public int getGridX() {
        return (int) position.getX();
    }

    public int getGridY() {
        return (int) position.getY();
    }

    public int getGridCode() {
        return this.gridCode;
    }

    public Map getMap() {
        return map;
    }

    public void move(Vector movement) {
        this.direction = movement.getDirection();
        if (!map.contains(this.position.sum(movement))) {
            return;
        }
        this.getPosition().move(movement);
    }

    public Vector getDirection() {
        return direction;
    }

    public Position getPosition() {// todo return copy
        return position;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImageView getMask() {
        return mask;
    }

    public void onCollision(GameObject collider) {}

    public void update() {}
}
