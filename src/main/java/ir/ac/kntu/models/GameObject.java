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
    // private static ArrayList<GameObject> world;
    // private static Timeline collisionChecker;
    // static {
    //     int[] xMoves = {1,-1,0,0};
    //     int[] yMoves = {0,0,1,-1};
    //     world = new ArrayList<>();
    //     collisionChecker = new Timeline();
    //     collisionChecker.getKeyFrames().add(new KeyFrame(Duration.millis(50),e->{
    //         GameObject other;
    //         for (GameObject go : world) {
    //             go.update();
    //             for (int i=0;i<4;i++) {
    //                 if ((other = go.getMap().getObject(go.getGridX()+xMoves[i], go.getGridY()+yMoves[i])) != null) {
    //                     if (other == go) {
    //                         continue;
    //                     }
    //                     if (periodCollision(go.getCurrentLayoutX(), go.getCurrentLayoutX()+go.getWidth(), other.getCurrentLayoutX(), other.getCurrentLayoutX()+other.getWidth()) 
    //                     && periodCollision(go.getCurrentLayoutY(), go.getCurrentLayoutY()+go.getHeight(), other.getCurrentLayoutY(), other.getCurrentLayoutY()+other.getHeight())) {
    //                         go.onCollision(other);
    //                     }
    //                 }
    //             }
    //         }
    //     }));
    // }
    public static void startLoop() {
        // collisionChecker.setCycleCount(Timeline.INDEFINITE);
        // collisionChecker.play();
    }

    private ImageView mask;
    private Position gridPos;
    private Vector direction;
    private int width;
    private int height;
    private Map map;
    private int gridCode;
    public GameObject(Map map ,int gridX,int gridY,int width,int height,int gridCode,ImageView mask) {
        // world.add(this);
        this.gridPos = new Position(gridX, gridY);
        this.width = width;
        this.height = height;
        this.map = map;
        this.direction = new Vector(1, 0);
        this.mask = mask;
        this.mask.setLayoutX(0);
        this.mask.setLayoutY(0);
        this.mask.setFitWidth(width);
        this.mask.setFitHeight(height);

        c1 = new Circle(0,0,3,Color.RED);
        c2 = new Circle(width,height,3,Color.BLUE);
        getChildren().add(c1);
        getChildren().add(c2);
        getChildren().add(mask);
        this.layoutPos.addXListener((oldval,newval)->{
            this.setLayoutX(newval);
            mask.setLayoutX(0);
            c1.setLayoutX(0);
            c2.setLayoutX(0);
        });
        this.layoutPos.addYListener((oldval,newval)->{
            this.setLayoutY(newval);
            mask.setLayoutY(0);
            c1.setLayoutY(0);
            c2.setLayoutY(0);
        });
    }
    public double getCurrentLayoutX() {
        return this.layoutPos.getX();
    }
    public double getCurrentLayoutY() {
        return this.layoutPos.getY();
    }
    public Position getCenterPos() {
        return new Position(getLayoutCenterX(),getLayoutCenterY());
    }
    public void setCurrentLayoutX(double x) {
        this.mask.setLayoutX(x);
        this.layoutPos.setX(x);
    }
    public void setCurrentLayoutY(double y) {
        this.mask.setLayoutY(y);
        this.layoutPos.setY(y);
    }
    
    public double getLayoutCenterX() {
        return this.layoutPos.getX()+width/2;
    }
    public double getLayoutCenterY() {
        return this.layoutPos.getY()+height/2;
    }
    /**
     * this method is out of support.
     * don't use it!
     * @param gridX
     */
    public void setGridX(int gridX) {
        // super.setGridX(gridX);
        this.mask.setLayoutX(getCurrentLayoutX());
    }

    /**
     * this method is out of support.
     * don't use it!
     * @param gridY
     */
    public void setGridY(int gridY) {
        // super.setGridY(gridY);
        this.mask.setLayoutY(getCurrentLayoutY());
    }
    public int getGridX() {
        return (int)gridPos.getX();
        // return this.gridPos.getX();
    }
    public int getGridY() {
        return (int)gridPos.getY();
        // return this.gridPos.getY();
    }
    public int getGridCode() {
        return gridCode;
    }
    public Map getMap() {
        return map;
    }
    private boolean xNormallized = true;
    private boolean yNormallized = true;
    // private Position lasPosition;
    public void move(Vector movement) {
        this.direction = movement.getDirection();
        if (!map.contains(this.layoutPos.sum(movement),this.width,this.height)) {
            return;
        }
        Position nextPos = this.gridPos.sum(movement.multiply(1.0/BLOCK_SCALE));
        normilizePair(nextPos);
        this.gridPos = nextPos;//!danger
        this.layoutPos.move(movement);
        if (xNormallized) {
            this.layoutPos.setX(getMap().gridToLayout(this.getGridX()));
        }
        if (yNormallized) {
            this.layoutPos.setY(getMap().gridToLayout(this.getGridY()));
        }
        // this.setLayoutX(this.getCurrentLayoutX());
        // this.setLayoutY(this.getCurrentLayoutY());
        // coll.setLayoutX(this.getCurrentLayoutX());
        // coll.setLayoutY(this.getCurrentLayoutY());
        // int oldX = this.getGridX(),oldY = this.getGridY();
        // setGridX(this.gridPos.getX());
        // setGridY(this.gridPos.getY());
        // map.updateObjectPos(this, oldX, oldY);
    }
    private void normilizePair(Position position) {
        final double accurancy = 1;
        int round = (int)Math.round(position.getX());
        if (xNormallized) {
            xNormallized = false;
        } else {
            if (round-accurancy/BLOCK_SCALE<=position.getX() && position.getX() <= round+accurancy/BLOCK_SCALE) {
                position.setX(round);
                xNormallized = true;
            }
        }
        round = (int)Math.round(position.getY());
        if (yNormallized) {
            yNormallized = false;
        } else {
            if (round-accurancy/BLOCK_SCALE<=position.getY() && position.getY() <= round+accurancy/BLOCK_SCALE) {
                position.setY(round);
                yNormallized = true;
            }
        }
    }
    public Vector getDirection() {
        return direction;
    }
    public Position getGridPos() {//todo return copy
        return gridPos;
    }
    public Position getLayoutPos() {
        return layoutPos;
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
