package ir.ac.kntu.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import ir.ac.kntu.core.Map;
import ir.ac.kntu.core.rigidbody.Position;
import ir.ac.kntu.core.rigidbody.Vector;

import java.util.Stack;
import java.util.function.Predicate;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A component for finding path using A* algorithm
 * @author Mahdi Lotfi
 */
public class PathFinder {
    // public static ArrayList<Point> freePoints;
    // private final static Point[] MOVES = new Point[] { new Point(1, 0), new Point(-1, 0), new Point(0, 1), new Point(0, -1) };
    // // DFS for find all free points
    // static {
    //     boolean[][] viewedPoints = new boolean[MazeData.GRID_SIZE_X + 1][MazeData.GRID_SIZE_Y + 1];
    //     freePoints = new ArrayList<>();
    //     Point start = new Point(1, 1);
    //     Stack<Point> openPoints = new Stack<>();
    //     openPoints.push(start);
    //     Point currentPoint;
    //     Point neighbor;
    //     while (openPoints.size() > 0) {
    //         currentPoint = openPoints.pop();
    //         freePoints.add(currentPoint);
    //         viewedPoints[currentPoint.getX()][currentPoint.getY()] = true;
    //         for (Point move : MOVES) {
    //             neighbor = Point.sum(currentPoint, move);
    //             if (!viewedPoints[neighbor.getX()][neighbor.getY()] && MazeData.getData(neighbor.getX(), neighbor.getY()) != MazeData.BLOCK) {
    //                 openPoints.push(neighbor);
    //                 viewedPoints[neighbor.getX()][neighbor.getY()] = true;
    //             }
    //         }
    //     }
    // }
    private Map map;
    public PathFinder(Map map) {
        this.map = map;
    }

    private static Vector[] directions = {new Vector(0, 1),new Vector(1, 0),new Vector(0, -1),new Vector(-1, 0)};


    public Path find(Position start, Position goal) {
        return aStar(new Position(start), new Position(goal));
    }

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
        opened[(int)start.getY()][(int)start.getX()] = true;
        Position current, neighbor;
        double tentative;
        boolean betterTantative;
        while (openList.size() > 0) {
            current = popBackMin(totalCosts, openList);
            // System.out.println(current);
            // map.getChildren().add(new Circle(map.gridToLayout((int)current.getX()),map.gridToLayout((int)current.getY()),3,Color.CYAN));
            if ((int)current.getX()==(int)goal.getX() && (int)current.getY()==(int)goal.getY()){
                return reconstructPath(cameFrom, current);
            }
            closed[(int)current.getY()][(int)current.getX()] = true;
            opened[(int)current.getY()][(int)current.getX()] = false;
            for (Vector direction : directions) {
                neighbor = current.sum(direction);
                if (map.isBlock((int)neighbor.getX(), (int)neighbor.getY()) || closed[(int)neighbor.getY()][(int)neighbor.getX()]) {
                    continue;
                }
                tentative = currentCosts.get(current) + 1;
                if (!opened[(int)neighbor.getY()][(int)neighbor.getX()]) {
                    openList.add(neighbor);
                    opened[(int)neighbor.getY()][(int)neighbor.getX()] = true;
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

    public Position bfs(Position origin,Predicate<Position> predicate) {
        boolean[][] closed = new boolean[map.getGridHeight()][map.getGridWidth()];
        LinkedList<Position> queue = new LinkedList<>();
        queue.add(new Position(origin));
        closed[(int)origin.getY()][(int)origin.getX()] = true;
        Position current,neighbor;
        while(!queue.isEmpty()) {
            current = queue.poll();
            closed[(int)current.getY()][(int)current.getX()] = true;
            if (predicate.test(current)) {
                return current;
            }
            for (Vector direction : directions) {
                neighbor = current.sum(direction);
                if (map.isBlock((int)neighbor.getX(), (int)neighbor.getY()) || closed[(int)neighbor.getY()][(int)neighbor.getX()]) {
                    continue;
                }
                queue.add(neighbor);
            }
        }
        return null;
    }

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
        // private double step;
        // private Vector current;
        // private int counter;
        private Path() {
            this.path = new Stack<>();
            // this.step = 1;
            // this.counter = (int)this.step;
        }
        public boolean hasNext() {
            return this.path.size() > 0;
        }
        // private Vector getNext() {
        //     if (counter == step) {
        //         current = path.pop();
        //         counter = 0;
        //     }
        //     counter++;
        //     return current.multiply(1.0/step);
        // }
        public Vector next() {
            return path.pop();
            // return getNext();
        }
        public int lenght() {
            return path.size();
        }
        // public void setStep(int step) {
        //     this.step = step;
        //     this.counter = step;
        // }
    }
}