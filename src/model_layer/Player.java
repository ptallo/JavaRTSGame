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
    private Game game;
    private ObservableList<InputItem> inputs = FXCollections.observableList(new ArrayList<>());

    private Rect rect;
    private Point selectionPoint;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public void updateRect(MouseEvent event){
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
            selectionPoint = new Point(event.getX(), event.getY());
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            game.selectUnits(this, rect);
            selectionPoint = null;
            rect = null;
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            Point endPoint = new Point(event.getX(), event.getY());
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
            gc.setStroke(Color.BLACK);
            gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        }
    }

    public void setGame(Game game) {
        this.game = game;
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
}
