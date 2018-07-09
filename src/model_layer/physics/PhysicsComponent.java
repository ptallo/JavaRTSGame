package model_layer.physics;

import controller_layer.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.GameObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PhysicsComponent implements Serializable {

    private Rectangle rectangle;
    private Point destination;

    private Double velocity = 0.5;
    private long lastUpdate;

    public PhysicsComponent(Double x, Double y, Double width, Double height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public void update(ArrayList<GameObject> gameobjects) {
        if (destination != null) {
            rectangle = getNewPosition();
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public Rectangle getNewPosition(){
        Double xDistance = destination.getX() - rectangle.getX();
        Double yDistance = destination.getY() - rectangle.getY();

        Double theta = Math.atan(yDistance / xDistance);

        Double deltax = Math.abs(velocity * Math.cos(theta) * GameController.GAME_PERIOD) * (destination.getX() > rectangle.getX() ? 1 : -1);
        Double deltay = Math.abs(velocity * Math.sin(theta) * GameController.GAME_PERIOD) * (destination.getY() > rectangle.getY() ? 1 : -1);

        System.out.println(deltax + ", " + deltay);

        boolean finalPos = true;

        Double newX;
        if (Math.abs(destination.getX() - rectangle.getX()) < deltax) {
            newX = destination.getX();
        } else {
            newX = rectangle.getX() + deltax;
            finalPos = false;
        }

        Double newY;
        if (Math.abs(destination.getY() - rectangle.getY()) < deltay){
            newY = destination.getY();
        } else {
            newY = rectangle.getY() + deltay;
            finalPos = false;
        }

        if (finalPos) {
            destination = null;
        }

        return new Rectangle(newX, newY, rectangle.getWidth(), rectangle.getHeight());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }
}
