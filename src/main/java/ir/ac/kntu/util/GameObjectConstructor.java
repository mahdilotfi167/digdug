package ir.ac.kntu.util;

import ir.ac.kntu.models.GameObject;

@FunctionalInterface
public interface GameObjectConstructor {
    public GameObject getObject(int x, int y);
}
