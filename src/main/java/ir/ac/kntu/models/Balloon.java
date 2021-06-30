package ir.ac.kntu.models;

import ir.ac.kntu.map.Map;
import ir.ac.kntu.rigidbody.Position;
import ir.ac.kntu.rigidbody.Vector;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static ir.ac.kntu.Constants.*;

import ir.ac.kntu.components.PathFinder;
import ir.ac.kntu.components.PathFinder.Path;
public class Balloon extends Sprite {

    private State state;
    private PathFinder pathFinder;
    public Balloon(Map map, int gridX, int gridY, ImageView spriteSheet, int gridCode) {
        super(map, gridX, gridY, BLOCK_SCALE,BLOCK_SCALE, spriteSheet, gridCode);
        this.state = State.ROAM;
        this.pathFinder = new PathFinder(getMap());
        getSpriteRenderer().setDuration(Duration.millis(300));
    }

    private Player target;

    private Path currentPath;

    private int counter = 0;
    @Override
    public void update() {
        if (counter++%15==0) {
            if (target != null) {
                Path p = pathFinder.find(this.getPosition(), target.getPosition());
                if (p.lenght() > 0) {
                    this.currentPath = p;
                    this.state = State.FOLLOW;
                }
            }
            switch (this.state) {
                case ROAM:
                roam();
                break;
                case FOLLOW:
                follow();
                break;
                default:
                break;
            }
        }
    }
    private void roam() {
        Position nextPos = this.getPosition().sum(getDirection());
        if (getMap().getData((int)nextPos.getX(),(int)nextPos.getY()) != 0) {
            this.move(getDirection().multiply(-1));
        } else {
            this.move(getDirection());
        }
    }
    private void follow() {
        if (currentPath.hasNext()) {
            move(currentPath.next());
        } else {
            this.state = State.ROAM;
        }
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    private static enum State {
        ROAM,FOLLOW;
    }
}
