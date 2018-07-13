package model_layer.components.physics;

import controller_layer.GameController;

import java.io.Serializable;

public class PhysicsComponent implements Serializable {

    private Rectangle rectangle;
    private Point destination;
    private Double xVelocity;
    private Double yVelocity;
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
        if (destination != null){

            Double xDistance = destination.getX() - rectangle.getX();
            Double yDistance = destination.getY() - rectangle.getY();
            Double hypotenuse = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

            Double theta = Math.asin(yDistance / hypotenuse);

            xVelocity = Math.abs(velocity * Math.cos(theta) * GameController.GAME_PERIOD) * (destination.getX() > rectangle.getX() ? 1 : -1);
            yVelocity = Math.abs(velocity * Math.sin(theta) * GameController.GAME_PERIOD) * (destination.getY() > rectangle.getY() ? 1 : -1);
        } else {
            xVelocity = 0.0;
            yVelocity = 0.0;
        }
    }

    public Double getXVelocity() {
        return xVelocity;
    }

    public Double getYVelocity() {
        return yVelocity;
    }

    public boolean isCollidable() {
        return collidable;
    }
}
