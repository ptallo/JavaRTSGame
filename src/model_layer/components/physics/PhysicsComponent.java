package model_layer.components.physics;

import java.io.Serializable;

public class PhysicsComponent implements Serializable {

    private Rectangle rectangle;
    private Point destination;
    private boolean collidable;
    private Double velocity = 0.25;

    public PhysicsComponent(Rectangle rectangle) {
        collidable = true;
        this.rectangle = rectangle;
    }

    public PhysicsComponent(Rectangle rectangle, boolean collidable) {
        this.rectangle = rectangle;
        this.collidable = collidable;
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

    public boolean isCollidable() {
        return collidable;
    }
}
