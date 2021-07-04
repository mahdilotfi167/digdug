package ir.ac.kntu.models;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static ir.ac.kntu.Constants.*;

import ir.ac.kntu.Engine;
import ir.ac.kntu.components.PathFinder;
import ir.ac.kntu.components.PathFinder.Path;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;

public class Balloon extends Sprite {

    private State state;
    private PathFinder pathFinder;
    private static final Position out = new Position(0,0);
    private int cycle;

    public Balloon(Map map, int gridX, int gridY, ImageView spriteSheet, int gridCode) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, spriteSheet, gridCode);
        this.state = State.ROAM;
        this.pathFinder = new PathFinder(getMap());
        getSpriteRenderer().setDuration(Duration.millis(300));
        this.cycle = 15;
    }

    private Player target;

    private Path currentPath;

    private int counter = 0;

    @Override
    public void update() {
        if (counter++ % cycle == 0) {
            if (target != null) {
                Path playerPath = pathFinder.find(this.getPosition(), target.getPosition());
                Path exitPath = pathFinder.find(this.getPosition(),out);
                if (playerPath.lenght() > 0) {
                    this.currentPath = playerPath;
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

    protected int getCounter() {
        return counter;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }


    @Override
    public void onCollision(GameObject collider) {
        if (collider instanceof Player) {
            ((Player)collider).kill();
        }
    }



    private void roam() {
        Position nextPos = this.getPosition().sum(getDirection());
        if (getMap().isBlock((int) nextPos.getX(), (int) nextPos.getY())) {
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
        ROAM, FOLLOW;
    }
}
