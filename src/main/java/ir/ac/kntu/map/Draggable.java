package ir.ac.kntu.map;

import ir.ac.kntu.core.GameObject;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Draggable extends GameObject {

    private final Delta dragDelta = new Delta();

    public Draggable(GameObject go) {
        super(go.getMap(), go.getGridX(), go.getGridY(), go.getWidth(), go.getHeight(), go.getGridCode(), go.getMask());
        getChildren().remove(getMask());
        getMap().getChildren().add(getMask());
        getMask().setLayoutX(getMap().gridToLayout(this.getGridX()));
        getMask().setLayoutY(getMap().gridToLayout(this.getGridY()));
        getMask().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                dragDelta.x = go.getMask().getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = go.getMask().getLayoutY() - mouseEvent.getSceneY();
            }
        });
        getMask().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                go.getMask().setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                go.getMask().setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });
        getMask().setOnMouseReleased(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent arg0) {
                getMask().setLayoutX(getMap().gridToLayout(getMap().layoutToGrid(getMask().getLayoutX())));
                getMask().setLayoutY(getMap().gridToLayout(getMap().layoutToGrid(getMask().getLayoutY())));
                getPosition().setX(getMap().layoutToGrid(getMask().getLayoutX()));
                getPosition().setY(getMap().layoutToGrid(getMask().getLayoutY()));
            }
        });
    }

    class Delta {
        private double x;
        private double y;
    }
}