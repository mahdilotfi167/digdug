package ir.ac.kntu.models.balloon;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static ir.ac.kntu.Constants.*;

import java.util.ArrayList;

import ir.ac.kntu.Engine;
import ir.ac.kntu.components.PathFinder;
import ir.ac.kntu.components.PathFinder.Path;
import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import ir.ac.kntu.models.Player;
import ir.ac.kntu.models.Sprite;
import ir.ac.kntu.models.Stone;

public class Balloon extends Sprite {

    private ArrayList<Stone> stones;
    private State state;
    private PathFinder pathFinder;
    private static final Position out = new Position(0,0);
    private int cycle;
    private ImageView inflateMask;
    private ArrayList<Balloon> friends;
    public Balloon(Map map, int gridX, int gridY, ImageView spriteSheet, int gridCode) {
        super(map, gridX, gridY, BLOCK_SCALE, BLOCK_SCALE, spriteSheet, gridCode);
        stones = getMap().collect(Stone.class);
        friends = new ArrayList<>();
        friends.addAll(getMap().collect(DragonBalloon.class));
        friends.addAll(getMap().collect(NormalBalloon.class));
        this.state = State.ROAM;
        this.pathFinder = new PathFinder(getMap());
        getSpriteRenderer().setDuration(Duration.millis(300));
        this.cycle = 15;
        inflateMask = new ImageView();
        this.getChildren().add(inflateMask);
        inflateMask.setLayoutX(0);
        inflateMask.setLayoutY(0);
    }

    private Player target;

    private Path currentPath;

    private int counter = 0;

    @Override
    public void update() {
        if (counter++ % cycle == 0) {
            if (inflateOrder > 0) {
                if ((counter-1)%(4*cycle) == 0) {
                    setInflateOrder(getInflateOrder()-1);
                }
                return;
            }

            if (target != null) {
                Path playerPath = pathFinder.find(this.getPosition(), target.getPosition());
                Path exitPath = pathFinder.find(this.getPosition(),out);
                if (playerPath.lenght() > 0) {
                    this.currentPath = playerPath;
                    this.state = State.FOLLOW;
                    if (exitPath.lenght() > 0) {
                        int playerCost = playerPath.lenght()-3*queryFriends();
                        int exitCost = exitPath.lenght();
                        if (exitCost < playerCost) {
                            this.currentPath = exitPath;
                            this.state = State.ESCAPE;
                        }
                    }
                }
            }
            
            if (stoneFalling()) {
                Position free = pathFinder.bfs(this.getPosition(), pos->pos.getX()!=this.getPosition().getX());
                if (free != null) {
                    this.currentPath = pathFinder.find(this.getPosition(), free);
                }
            }
            switch (this.state) {
                case ROAM:
                    roam();
                    break;
                case ESCAPE:
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

    private int queryFriends() {
        int count = 0;
        for (Balloon friend : friends) {
            if (friend.state == State.FOLLOW) {
                count++;
            }
        }
        return count;
    }



    private void roam() {
        Position nextPos = this.getPosition().sum(getDirection());
        if (getMap().isBlock((int) nextPos.getX(), (int) nextPos.getY())) {
            this.move(getDirection().multiply(-1));
        } else {
            this.move(getDirection());
        }
    }

    @Override
    public void move(Vector movement) {
        super.move(movement);
        if (this.getPosition().equals(out)) {
            this.kill();
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
        ROAM, FOLLOW, ESCAPE;
    }

    protected void setInflateOrder(int inflateOrder) {
        if (inflateOrder<0) {
            this.getMask().setVisible(true);
            inflateOrder = 0;
            return;
        }
        if (inflateOrder == 0) {
            this.getMask().setVisible(true);
            this.inflateMask.setImage(null);
        }
        if (inflateOrder<5 && inflateOrder>0) {
            this.getMask().setVisible(false);
            this.inflateMask.setFitHeight(getHeight()*(1+inflateOrder/5.0));
            this.inflateMask.setFitWidth(getWidth()*(1+inflateOrder/5.0));
        }
        if (inflateOrder == 4) {
            this.kill();
        }
        this.inflateOrder = inflateOrder;
    }

    private boolean stoneFalling() {
        Vector upDir = new Vector(0, -1);
        Position current = new Position(this.getPosition().sum(upDir));
        while (!getMap().isBlock(current)) {
            if ((getMap().getData(current) & STONE_GRID_CODE) != 0) {
                return true;
            }
            current.move(upDir);
        }
        return false;
    }

    @Override
    public void kill() {
        Engine.deadBalloon();
        super.kill();
    }

    protected int getInflateOrder() {
        return inflateOrder;
    }

    private int inflateOrder;

    public void inflate() {
        setInflateOrder(getInflateOrder()+1);
    }

    public ImageView getInflateMask() {
        return inflateMask;
    }

    @Override
    public void setDirection(Vector direction) {
        if (direction.getX() < 0) {
            getInflateMask().setScaleX(-1);
            getInflateMask().setRotate(0);
        } else {
            getInflateMask().setScaleX(1);
            getInflateMask().setRotate(direction.getRotation());
        }
        super.setDirection(direction);
    }
}
