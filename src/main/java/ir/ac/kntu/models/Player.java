package ir.ac.kntu.models;
import static ir.ac.kntu.Constants.*;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.rigidbody.Vector;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player extends Sprite {
    ImageView pump;
    ScaleTransition st;
    TranslateTransition tt;
    private Position nextGridPos;
    private int speed;
    public Player(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, BLOCK_SCALE,BLOCK_SCALE,"/assets/player.png",PLAYER_GRID_CODE);
        this.speed = 1;
        this.nextGridPos = new Position(gridX, gridY);
        this.pump = new ImageView("/assets/pump.png");
        this.st = new ScaleTransition(Duration.millis(1000), pump);
        pump.setFitWidth(BLOCK_SCALE);
        pump.setFitHeight(BLOCK_SCALE);
        this.st.setCycleCount(1);
        st.setByX(3);
        this.pump.setVisible(false);
        this.getChildren().add(pump);
        tt = new TranslateTransition(Duration.millis(1000),pump);
        tt.setCycleCount(1);
    }
    public void shoot() {
        pump.setScaleX(1);
        pump.setVisible(true);
        Position p = new Position(getLayoutCenterX(), getLayoutCenterY()).sum(getDirection().getDirection(BLOCK_SCALE));
        pump.setLayoutX(p.getX()-BLOCK_SCALE/2);
        pump.setLayoutY(p.getY()-BLOCK_SCALE/2);
        pump.setRotate(getDirection().getRotation());
        
        pump.setVisible(false);

    }

    @Override
    public void move(Vector movement) {
        // moveX(movement.getX());
        // moveY(movement.getY());
        int length = (int)movement.lenght();
        while (length-->0) {
            if (movement.getX() == 0 && movement.getY() != 0) {
                if (this.getGridPos().getX()%1 == 0) {
                    super.move(movement.getDirection());
                } else {
                    super.move(this.getDirection());
                }
            }
            if (movement.getY() == 0 && movement.getX() !=0) {
                if (this.getGridPos().getY()%1 == 0) {
                    super.move(movement.getDirection());
                } else {
                    super.move(this.getDirection());
                }
            }
        }
        getMap().getGraphicsContext().setFill(Color.BLACK);
        getMap().getGraphicsContext().fillRoundRect(this.getLayoutPos().getX(),this.getLayoutPos().getY(),this.getWidth(),this.getHeight(),10,10);
        // getMap().getGraphicsContext().setFill(Color.BLUE);
        // getMap().getGraphicsContext().fillRect(this.nextGridPos.getX(),this.nextGridPos.getY(),this.getWidth(),this.getHeight());
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    // public void moveX(double dx) {
    //     if (dx == 0) {
    //         return;
    //     }
    //     if (getMap().layoutToGrid(this.getCurrentLayoutY()) == (int)nextGridPos.getY()) {
    //         // double absoluteDelta = dx/Math.abs(dx);
    //         double absoluteDelta = dx;
    //         // if(this.getCurrentLayoutX() == getMap().gridToLayout((int)nextGridPos.getX())) {
    //         if(getMap().layoutToGrid(this.getCurrentLayoutX()) == (int)nextGridPos.getX()) {
    //             nextGridPos.move(absoluteDelta,0);
    //         }
    //         // if(((getMap().gridToLayout((int)(nextGridPos.getX())))-getCurrentLayoutX()) * dx < 0) {
    //         if (this.getDirection().getY() * dx < 0) {
    //             nextGridPos.move(absoluteDelta,0);
    //         }
    //         System.out.println(this.getGridPos());
    //         System.out.println(this.getDirection());
    //         // setCurrentLayoutX(getCurrentLayoutX() + absoluteDelta);
    //     } else {
    //         // double absoluteDelta = getMap().gridToLayout(nextGridPos.getY())-this.getCurrentLayoutY();
    //         moveY(getDirection().getDirection().getY());
    //         // moveY(getDirection().getDirection().getY()*Math.abs(dx));
    //     }
    // }
    // public void moveY(double dy) {
    //     if (dy == 0) {
    //         return;
    //     }
    //     if (getMap().layoutToGrid(this.getCurrentLayoutX()) == (int)nextGridPos.getX()) {
    //         double absoluteDelta = dy/Math.abs(dy);
    //         // if(this.getCurrentLayoutX() == getMap().gridToLayout((int)nextGridPos.getX())) {
    //         if(getMap().layoutToGrid(this.getCurrentLayoutY()) == (int)nextGridPos.getY()) {
    //             nextGridPos.move(0,absoluteDelta);
    //         }
    //         if((getMap().gridToLayout((int)(nextGridPos.getY()))-getCurrentLayoutY()) * dy < 0) {
    //             nextGridPos.move(0,absoluteDelta);
    //         }
    //         System.out.println(this.getGridPos());
    //         System.out.println(this.getDirection());
    //         // setCurrentLayoutX(getCurrentLayoutX() + absoluteDelta);
    //     } else {
    //         // double absoluteDelta = getMap().gridToLayout(nextGridPos.getY())-this.getCurrentLayoutY();
    //         moveX(getDirection().getDirection().getX()*Math.abs(dy));
    //     }
    // }
    // public void moveY(double dy) {
    //     if (dy == 0){
    //         return;
    //     }
    //     if (this.getCurrentLayoutX() == Map.gridToLayout(nextGridX)) {
    //         int absoluteDelta = dy/Math.abs(dy);
    //         if(this.getCurrentLayoutY() == Map.gridToLayout(nextGridY)) {
    //             setCurrentGridY(nextGridY);
    //             nextGridY+=absoluteDelta;
    //         }
    //         if((Map.gridToLayout(nextGridY)-getCurrentLayoutY()) * dy < 0) {
    //             nextGridY+=absoluteDelta;
    //         }
    //         setCurrentLayoutY(getCurrentLayoutY() + absoluteDelta);
    //     } else {
    //         moveX(Map.gridToLayout(nextGridX)-this.getCurrentLayoutX());
    //     }
    // }
}
