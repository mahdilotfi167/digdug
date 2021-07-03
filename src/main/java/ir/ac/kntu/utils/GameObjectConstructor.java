package ir.ac.kntu.utils;

import ir.ac.kntu.core.GameObject;
import ir.ac.kntu.core.Map;

@FunctionalInterface
public interface GameObjectConstructor {
    public GameObject getObject(Map map,int x, int y);
}
