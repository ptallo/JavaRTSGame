package model_layer.components.physics;

import controller_layer.GameController;

import java.io.Serializable;
import java.util.ArrayList;

public class PhysicsComponent implements Serializable {

    private Rectangle rectangle;
    private ArrayList<Point> destinations;
    private Double xVelocity;
    private Double yVelocity;
    private boolean collidable;
    private Double velocity = 0.25;

    public PhysicsComponent(Rectangle rectangle) {
        this(rectangle, true);
    }

    public PhysicsComponent(Rectangle rectangle, boolean collidable) {
        this.rectangle = rectangle;
        this.collidable = collidable;
        destinations = new ArrayList<>();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public ArrayList<Point> getDestinations() {
        return destinations;
    }

    public Point getCurrentDestination() {
        return destinations.get(0);
    }

    public void insertDestination(Point destination, Integer index){
        if (destination == null) {
            return;
        }

        if (index > destinations.size() - 1){
            destinations.add(destination);
        } else if (index < 0){
            destinations.add(0, destination);
        } else {
            destinations.add(index, destination);
        }

        recalculateVelocities();
    }

    public void addDestination(Point destination) {
        insertDestination(destination, destinations.size() - 1);
    }

    public void removeCurrentDestination(){
        destinations.remove(0);
        recalculateVelocities();
    }

    private void recalculateVelocities() {
        if (destinations.size() == 0){
            xVelocity = 0.0;
            yVelocity = 0.0;
        } else {
            Double xDistance = destinations.get(0).getX() - rectangle.getX();
            Double yDistance = destinations.get(0).getY() - rectangle.getY();
            Double hypotenuse = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

            Double theta = Math.asin(yDistance / hypotenuse);

            xVelocity = Math.abs(velocity * Math.cos(theta) * GameController.GAME_PERIOD) * (destinations.get(0).getX() > rectangle.getX() ? 1 : -1);
            yVelocity = Math.abs(velocity * Math.sin(theta) * GameController.GAME_PERIOD) * (destinations.get(0).getY() > rectangle.getY() ? 1 : -1);
        }
    }

    public Double getxVelocity() {
        return xVelocity;
    }

    public Double getyVelocity() {
        return yVelocity;
    }

    public boolean isCollidable() {
        return collidable;
    }
}
