package ir.ac.kntu.models;

import static ir.ac.kntu.Constants.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import ir.ac.kntu.models.balloon.Balloon;

public class Pump extends Object {
    private Circle circle;
    private int range;
    private boolean active;
    private int lenght;
    private int dir;
    private boolean attached;

    public Pump(Map map) {
        super(map, 0, 0, BLOCK_SCALE, BLOCK_SCALE, new ImageView(), 0);
        circle = new Circle(this.getLayoutX(), this.getLayoutY(), 3, Color.RED);
        getChildren().add(circle);
        circle.setLayoutX(BLOCK_SCALE / 2);
        circle.setLayoutY(BLOCK_SCALE / 2);
        circle.setVisible(false);
        setRange(3);
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void shoot(Position position, Vector direction) {
        if (!this.active) {
            this.lenght = 0;
            this.getPosition().setX(position.getX());
            this.getPosition().setY(position.getY());
            this.setDirection(direction.getDirection());
            this.active = true;
            circle.setVisible(true);
            this.dir = 1;
            attached = false;
        }
    }

    @Override
    public void update() {
        if (this.active) {
            if (lenght == range - 1 && dir > 0) {
                this.setDirection(getDirection().multiply(-1));
                dir = -dir;
            }
            this.move(this.getDirection());
            lenght += dir;
            if (lenght == 0 && dir < 0) {
                this.active = false;
                this.circle.setVisible(false);
            }
        }
    }

    @Override
    public void onCollision(GameObject collider) {
        if (this.active && !attached) {
            if (collider instanceof Balloon) {
                Balloon b = (Balloon) collider;
                b.inflate();
                this.setDirection(getDirection().multiply(-1));
                dir = -dir;
                attached = true;
            }
        }
    }

    public boolean isActive() {
        return this.active;
    }
}