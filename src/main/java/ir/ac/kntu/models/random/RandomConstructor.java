package ir.ac.kntu.models.random;

import ir.ac.kntu.core.Map;

@FunctionalInterface
public interface RandomConstructor {
    public RandomObject getObject(Map map,int gridX,int gridY);
}
