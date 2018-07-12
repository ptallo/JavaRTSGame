package model_layer.components.physics;

import controller_layer.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.GameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PhysicsComponent implements Serializable {

    private Rectangle rectangle;
    private Double xOffset;
    private Double yOffset;
    private Point destination;
    private Double velocity = 0.25;

    public PhysicsComponent(Rectangle rectangle, Double xOffset, Double yOffset) {
        this.rectangle = rectangle;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public Rectangle getNormalizedRect(){
        return new Rectangle(rectangle.getX() - xOffset, rectangle.getY() - yOffset, rectangle.getWidth(), rectangle.getHeight());
    }

    public Double getVelocity() {
        return velocity;
    }
}
