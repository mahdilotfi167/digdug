package ir.ac.kntu.core;

import javafx.scene.image.ImageView;

public class Foo extends GameObject {
    public Foo(Map map, int gridX, int gridY) {
        super(map, gridX, gridY, 10, 10, 2, new ImageView());
    }
}
