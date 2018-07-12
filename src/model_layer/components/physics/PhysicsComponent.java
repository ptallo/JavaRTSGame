package model_layer.components.physics;

import java.io.Serializable;

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

    public Double getVelocity() {
        return velocity;
    }
}
