package model_layer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;
import model_layer.object_interface.map.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {

    private String id;
    private Game game;
    private ObservableList<InputItem> inputs = FXCollections.observableList(new ArrayList<>());

    private Rectangle selectionRectangle;
    private Point selectionPoint;

    private Double screenWidth;
    private Double screenHeight;

    private Color playerColor = Color.BLUE;

    private Double transformSpeed = 5.0;
    private Double xTransform = 0.0;
    private Double yTransform = 0.0;

    // Values -1, 0, 1 mean updateTransform negatively, don't updateTransform and updateTransform positively
    private Integer updateXTransform = 0;
    private Integer updateYTransform = 0;

    public Player() {
        id = UUID.randomUUID().toString();
    }

    public void updateRect(MouseEvent event){
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED){
            selectionPoint = new Point(event.getScreenX() - xTransform, event.getScreenY() - yTransform);
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
            Point endPoint = new Point(event.getScreenX() - xTransform, event.getScreenY() - yTransform);
            if (selectionPoint != null) {
                selectionRectangle = new Rectangle(selectionPoint, endPoint);
            }
        }
    }

    public void updateTransform(GraphicsContext gc, Map map) {
        Double newX = null;
        if (updateXTransform > 0) {
            newX = xTransform + transformSpeed;
        } else if (updateXTransform < 0) {
            newX = xTransform - transformSpeed;

        }

        Double newY = null;
        if (updateYTransform > 0) {
            newY = yTransform + transformSpeed;
        } else if (updateYTransform < 0) {
            newY = yTransform - transformSpeed;
        }

        Rectangle screenRect = new Rectangle(
                newX == null ? -xTransform : -newX,
                newY == null ? -yTransform : -newY,
                screenWidth,
                screenHeight
        );

        if (map.getMapRectangle().containsFully(screenRect)) {
            xTransform = -screenRect.getX();
            yTransform = -screenRect.getY();
        }

        gc.setTransform(1, 0, 0, 1, xTransform, yTransform);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(-xTransform, -yTransform, screenWidth, screenHeight);
    }

    public void draw(GraphicsContext gc) {
        if (selectionRectangle != null){
            gc.setStroke(Color.BLACK);
            gc.strokeRect(selectionRectangle.getX(), selectionRectangle.getY(), selectionRectangle.getWidth(), selectionRectangle.getHeight());
        }
    }

    public void updateTransformDirection(MouseEvent event, Canvas canvas, Boolean isCanvasEvent){
        if (event.getX() < canvas.getWidth() * 0.05){
            updateXTransform = 1;
        } else if (event.getX() > canvas.getWidth() * 0.95) {
            updateXTransform = -1;
        } else {
            updateXTransform = 0;
        }

        if (event.getY() < canvas.getHeight() * 0.05 && isCanvasEvent){
            updateYTransform = 1;
        } else if (event.getY() > canvas.getHeight() * 0.95 && isCanvasEvent) {
            updateYTransform = -1;
        } else {
            updateYTransform = 0;
        }
    }

    public void updateDimensions(double width, double height) {
        screenWidth = width;
        screenHeight = height;
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

    public Color getPlayerColor() {
        return playerColor;
    }

    public Rectangle getScreenRectangle() {
        return new Rectangle(
                -xTransform,
                -yTransform,
                screenWidth,
                screenHeight
        );
    }
}
