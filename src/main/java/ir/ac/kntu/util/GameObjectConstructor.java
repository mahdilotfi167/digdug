package ir.ac.kntu.util;

import ir.ac.kntu.map.Map;
import ir.ac.kntu.models.GameObject;

@FunctionalInterface
public interface GameObjectConstructor {
    public GameObject getObject(Map map,int x, int y);
}
