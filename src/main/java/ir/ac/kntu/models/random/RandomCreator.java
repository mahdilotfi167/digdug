package ir.ac.kntu.models.random;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.utils.Random;
import javafx.scene.image.ImageView;
import static ir.ac.kntu.Constants.*;

import java.util.ArrayList;

public class RandomCreator extends GameObject {

    private int counter;
    private ArrayList<RandomConstructor> constructors;

    public RandomCreator(Map map) {
        super(map, 0, 0, BLOCK_SCALE, BLOCK_SCALE, 0, new ImageView());
        counter = 0;
        constructors = new ArrayList<>();
        constructors.add(Sniper::new);
        constructors.add(Heart::new);
        constructors.add(Mushroom::new);
    }

    @Override
    public void update() {
        if (counter++ % 375 == 0) {
            createRandom();
        }
    }

    public void createRandom() {
        Position random = Random.choice(getMap().getFreePoints());
        getMap().addObject(Random.choice(constructors).getObject(getMap(), (int) random.getX(), (int) random.getY()));
    }
}