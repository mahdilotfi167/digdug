package ir.ac.kntu.core;

import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
/**
 * A simple Class for working on game objects in javafx games
 * for create your own gameobjects compatible with map model just inherit from this model
 */
public abstract class GameObject extends Parent {
    private ImageView mask;
    private Position position;
    private Vector direction;
    private int width;
    private int height;
    private Map map;
    private int gridCode;
/**
 * Create a new GameObject
 * @param map map that this gameobject should add on it
 * @param gridX x position that this object should stay on it
 * @param gridY y position that this object should stay on it
 * @param width width that this object mask render on it
 * @param height height that this object mask render on it
 * @param gridCode unique grid code for this object
 * @param mask mask for this object that shows on map
 * <p><b>notice :</b> for destroying side effect of this object on map just use 0 for grid code<p>
 * <p><b>notice :</b> for creating maskless gameobjects just use {@code new ImageView()} for mask argument<p>
 */
    public GameObject(Map map, int gridX, int gridY, int width, int height, int gridCode, ImageView mask) {
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
        setDirection(movement.getDirection());
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

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public void setWidth(int width) {
        this.mask.setFitWidth(width);
        this.width = width;
    }

    public void setHeight(int height) {
        this.mask.setFitHeight(height);
        this.height = height;
    }

    public void onCollision(GameObject collider) {}

    public void update() {}
}
