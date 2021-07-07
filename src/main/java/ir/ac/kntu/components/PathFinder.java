package ir.ac.kntu.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * A component for finding path using graph search algorithms
 * 
 * @author Mahdi Lotfi
 */
public class PathFinder {
    private Map map;

    public PathFinder(Map map) {
        this.map = map;
    }

    private static Vector[] directions = { new Vector(0, 1), new Vector(1, 0), new Vector(0, -1), new Vector(-1, 0) };

    /**
     * This method uses A* graph search algorithm for find shortest path between start position and goal position
     * @param start position
     * @param goal position
     * @return a {@code PathFinder.Path} object that contains result of this search
     */
    public Path find(Position start, Position goal) {
        return aStar(new Position(start), new Position(goal));
    }

    // create a path using a* algorithm from start to goal
    private Path aStar(Position start, Position goal) {
        boolean[][] closed = new boolean[map.getGridHeight()][map.getGridWidth()];
        boolean[][] opened = new boolean[map.getGridHeight()][map.getGridWidth()];
        HashMap<Position, Double> estimateCosts = new HashMap<>();
        HashMap<Position, Double> currentCosts = new HashMap<>();
        HashMap<Position, Double> totalCosts = new HashMap<>();
        HashMap<Position, Position> cameFrom = new HashMap<>();
        ArrayList<Position> openList = new ArrayList<>();
        estimateCosts.put(start, huristic(start, goal));
        currentCosts.put(start, 0.0);
        totalCosts.put(start, estimateCosts.get(start) + currentCosts.get(start));
        openList.add(start);
        opened[(int) start.getY()][(int) start.getX()] = true;
        Position current, neighbor;
        double tentative;
        boolean betterTantative;
        while (openList.size() > 0) {
            current = popBackMin(totalCosts, openList);
            if ((int) current.getX() == (int) goal.getX() && (int) current.getY() == (int) goal.getY()) {
                return reconstructPath(cameFrom, current);
            }
            closed[(int) current.getY()][(int) current.getX()] = true;
            opened[(int) current.getY()][(int) current.getX()] = false;
            for (Vector direction : directions) {
                neighbor = current.sum(direction);
                if (map.isBlock((int) neighbor.getX(), (int) neighbor.getY())
                        || closed[(int) neighbor.getY()][(int) neighbor.getX()]) {
                    continue;
                }
                tentative = currentCosts.get(current) + 1;
                if (!opened[(int) neighbor.getY()][(int) neighbor.getX()]) {
                    openList.add(neighbor);
                    opened[(int) neighbor.getY()][(int) neighbor.getX()] = true;
                    betterTantative = true;
                } else if (tentative < currentCosts.get(neighbor)) {
                    betterTantative = true;
                } else {
                    betterTantative = false;
                }
                if (betterTantative) {
                    cameFrom.put(neighbor, current);
                    currentCosts.put(neighbor, tentative);
                    estimateCosts.put(neighbor, huristic(neighbor, goal));
                    totalCosts.put(neighbor, currentCosts.get(neighbor) + estimateCosts.get(neighbor));
                }
            }
        }
        return new Path();
    }

    // reconstruct path for a*
    private Path reconstructPath(HashMap<Position, Position> cameFrom, Position currentPoint) {
        Path res = new Path();
        Position next;
        while ((next = cameFrom.get(currentPoint)) != null) {
            res.path.push(new Vector(next, currentPoint));
            currentPoint = next;
        }
        return res;
    }

    // manhattan
    private double huristic(Position start, Position goal) {
        return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY());
    }

    /**
     * This method uses BFS graph search algorithm for find first position that satifies the predication
     * @param origin start position for search
     * @param predicate condition for find optimal position
     * @return first position that satisfies the predication or null if no position found
     */
    public Position bfs(Position origin, Predicate<Position> predicate) {
        boolean[][] closed = new boolean[map.getGridHeight()][map.getGridWidth()];
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(new Position(origin));
        closed[(int) origin.getY()][(int) origin.getX()] = true;
        Position current, neighbor;
        while (!queue.isEmpty()) {
            current = queue.poll();
            closed[(int) current.getY()][(int) current.getX()] = true;
            if (predicate.test(current)) {
                return current;
            }
            for (Vector direction : directions) {
                neighbor = current.sum(direction);
                if (map.isBlock((int) neighbor.getX(), (int) neighbor.getY())
                        || closed[(int) neighbor.getY()][(int) neighbor.getX()]) {
                    continue;
                }
                queue.add(neighbor);
            }
        }
        return null;
    }

    // pop backs min cost points for a*
    private Position popBackMin(HashMap<Position, Double> costs, ArrayList<Position> list) {
        ListIterator<Position> iterator = list.listIterator(list.size());
        double minCost = 99999, currentCost;
        Position minPoint = null, currentPoint;
        while (iterator.hasPrevious()) {
            currentPoint = iterator.previous();
            currentCost = costs.get(currentPoint);
            if (currentCost < minCost) {
                minPoint = currentPoint;
                minCost = currentCost;
            }
        }
        list.remove(minPoint);
        return minPoint;
    }

    public static class Path {
        private Stack<Vector> path;

        private Path() {
            this.path = new Stack<>();
        }

        public boolean hasNext() {
            return this.path.size() > 0;
        }

        public Vector next() {
            return path.pop();
        }

        public int lenght() {
            return path.size();
        }
    }
}