package model_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {

    private String id;
    private Game game;
    private ObservableList<InputItem> inputs = FXCollections.observableList(new ArrayList<>());

    private Rectangle selectionRectangle;
    private Point selectionPoint;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public void updateRect(MouseEvent event){
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
            selectionPoint = new Point(event.getX(), event.getY());
            if (event.isSecondaryButtonDown()){
                game.setObjectDestination(this, selectionPoint);
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            if (selectionRectangle != null){
                game.selectUnits(this, selectionRectangle);
            }
            selectionPoint = null;
            selectionRectangle = null;
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            Point endPoint = new Point(event.getX(), event.getY());
            if (selectionPoint != null) {
                selectionRectangle = new Rectangle(selectionPoint, endPoint);
            }
        }
    }

    public void update() {

    }

    public void draw(GraphicsContext gc) {
        if (selectionRectangle != null){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(selectionRectangle.getX(), selectionRectangle.getY(), selectionRectangle.getWidth(), selectionRectangle.getHeight());
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
