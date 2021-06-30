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
    @Override
    public void update() {
        if (target != null) {
            if (this.getGridPos().getX()%1==0 && this.getGridPos().getY()%1==0) {
                PathFinder.Path p = this.pathFinder.find(this.getGridPos(), target.getGridPos());
                System.out.println(p.lenght());
                if (p.lenght() != 0) {
                    currentPath = p;
                    // currentPath.setStep(1);
                    this.state = State.FOLLOW;
                }
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
    private void roam() {
        Position nextPos = this.getLayoutPos().sum(getDirection());
        if (getMap().getData(getMap().layoutToGrid(nextPos.getX()),getMap().layoutToGrid(nextPos.getY())) != 0) {
            this.move(getDirection().multiply(-1));
        }
        this.move(getDirection());
    }
    private int counter = BLOCK_SCALE-1;
    private void follow() {
        if (counter == BLOCK_SCALE-1) {
            if (currentPath.hasNext()) {
                move(currentPath.next());
                counter = 0;
            } else {
                this.state = State.ROAM;
            }
        }
        move(getDirection());
        counter++;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    private static enum State {
        ROAM,FOLLOW;
    }
}
