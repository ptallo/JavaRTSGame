package model_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model_layer.physics.Point;
import model_layer.physics.Rect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable, GameObjectInterface {

    private String id;
    private ObservableList<InputItem> inputs = FXCollections.observableList(new ArrayList<>());

    private Rect rect;
    private Point selectionPoint;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public void addInput(InputItem item) {
        this.inputs.add(item);
    }

    public String getId() {
        return id;
    }

    public ObservableList<InputItem> getInputs() {
        return inputs;
    }

    public void updateRect(Event event){
        event = (MouseEvent) event;
        System.out.println("update rect");
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
            selectionPoint = new Point(((MouseEvent) event).getX(), ((MouseEvent) event).getY());
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            selectionPoint = null;
            rect = null;
        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            Point endPoint = new Point(((MouseEvent) event).getX(), ((MouseEvent) event).getY());
            if (selectionPoint != null) {
                rect = new Rect(selectionPoint, endPoint);
            }
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        if (rect != null){
            gc.setStroke(Color.GOLD);
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
    }
}
