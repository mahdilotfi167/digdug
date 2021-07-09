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
    private int counter;

    public Pump(Map map) {
        super(map, 0, 0, BLOCK_SCALE, BLOCK_SCALE, new ImageView(), 0);
        circle = new Circle(this.getLayoutX(), this.getLayoutY(), 3, Color.RED);
        getChildren().add(circle);
        circle.setLayoutX(BLOCK_SCALE / 2);
        circle.setLayoutY(BLOCK_SCALE / 2);
        circle.setVisible(false);
        setRange(3);
        this.getPosition().addXListener((oldval, newval) -> {
            if (getMap().isBlock(newval.intValue(), (int) this.getPosition().getY())) {
                this.active = false;
                this.circle.setVisible(false);
            }
        });
        this.getPosition().addYListener((oldval, newval) -> {
            if (getMap().isBlock((int) this.getPosition().getX(), newval.intValue())) {
                this.active = false;
                this.circle.setVisible(false);
            }
        });
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
            if (getMap().isBlock(this.getPosition())) {
                return;
            }
            this.active = true;
            circle.setVisible(true);
            this.dir = 1;
            attached = false;
            counter = 0;
        }
    }

    @Override
    public void update() {
        if (this.active) {
            if (counter++ % 3 == 0) {
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
    }

    @Override
    public void onCollision(GameObject collider) {
        if (this.active) {
            if (collider instanceof Balloon && !attached) {
                Balloon b = (Balloon) collider;
                b.inflate();
                this.setDirection(getDirection().multiply(-1));
                dir = -dir;
                attached = true;
            }
            if (collider instanceof Player && dir < 0) {
                this.active = false;
                this.circle.setVisible(false);
            }
        }
    }

    public boolean isActive() {
        return this.active;
    }
}