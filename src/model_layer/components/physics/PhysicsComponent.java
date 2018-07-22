package model_layer.components.physics;

import controller_layer.GameController;

import java.io.Serializable;
import java.util.ArrayList;

public class PhysicsComponent implements Serializable {

    private Rectangle rectangle;
    private ArrayList<Point> destinations;
    private Double xVelocity;
    private Double yVelocity;
    private Double velocity;

    private boolean collidable;
    private boolean separating;

    public PhysicsComponent(Rectangle rectangle) {
        this(rectangle, true, 0.25);
    }

    public PhysicsComponent(Rectangle rectangle, boolean collidable, double velocity) {
        this.rectangle = rectangle;
        this.collidable = collidable;
        this.velocity = velocity;
        this.separating = false;
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
        destinations = new ArrayList<>();
        insertDestination(destination, destinations.size() - 1);
    }

    public void removeCurrentDestination(){
        destinations.remove(0);
        recalculateVelocities();
    }

    public void separateComponent (PhysicsComponent collidedComponent) {
        if (!separating) {
            separating = true;
            double uDir = collidedComponent.getRectangle().getX() - getRectangle().getX() > 0  ? -1.0 : 1.0;
            insertDestination(new Point(
                    rectangle.getX() + (uDir * rectangle.getWidth()),
                    rectangle.getY()
            ),  -1);
        }
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

            if (hypotenuse == 0){
                removeCurrentDestination();
                return;
            }

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

    public Double getVelocity() {
        return velocity;
    }

    public boolean isCollidable() {
        return collidable;
    }
}
