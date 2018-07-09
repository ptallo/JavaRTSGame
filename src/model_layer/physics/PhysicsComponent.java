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

    public void update(ArrayList<GameObject> gameObjects) {
        if (destination != null) {
            Rectangle tempRect = getNewPosition();
            if (tempRect != null){
                Boolean updateRect = true;
                for (GameObject object : gameObjects){
                    if (object.getPhysicsComponent().getRectangle().contains(tempRect)){
                        updateRect = false;
                    }
                }

                if (updateRect){
                    rectangle = tempRect;
                }
            }
        }
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public Rectangle getNewPosition(){
        Double xDistance = destination.getX() - rectangle.getX();
        Double yDistance = destination.getY() - rectangle.getY();
        Double hypotenuse = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        if (hypotenuse == 0) {
            destination = null;
            return null;
        }

        Double theta = Math.asin(yDistance / hypotenuse);

        Double deltaX = Math.abs(velocity * Math.cos(theta) * GameController.GAME_PERIOD) * (destination.getX() > rectangle.getX() ? 1 : -1);
        Double deltaY = Math.abs(velocity * Math.sin(theta) * GameController.GAME_PERIOD) * (destination.getY() > rectangle.getY() ? 1 : -1);

        boolean finalPos = true;

        Double newX;
        if (Math.abs(destination.getX() - rectangle.getX()) < deltaX) {
            newX = destination.getX();
        } else {
            newX = rectangle.getX() + deltaX;
            finalPos = false;
        }

        Double newY;
        if (Math.abs(destination.getY() - rectangle.getY()) < deltaY){
            newY = destination.getY();
        } else {
            newY = rectangle.getY() + deltaY;
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
