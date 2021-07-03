package ir.ac.kntu.core;

@FunctionalInterface
public interface GameObjectConstructor {
    public GameObject getObject(Map map,int x, int y);
}
