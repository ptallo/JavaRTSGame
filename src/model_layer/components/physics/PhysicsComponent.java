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
    private Double velocity = 0.5;
    private long lastUpdate;

    public PhysicsComponent(Rectangle rectangle, Double xOffset, Double yOffset) {
        this.rectangle = rectangle;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public Boolean update(ArrayList<GameObject> gameObjects) {
        if (destination != null) {
            Rectangle tempRect = getNewPosition(destination, rectangle);
            if (tempRect != null){
                Boolean updateRect = true;
                for (GameObject object : gameObjects){
                    if (object.getPhysicsComponent().getRectangle().contains(tempRect)){
                        updateRect = false;
                    }
                }

                if (updateRect){
                    rectangle = tempRect;
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc) {
        gc.setStroke(Color.DARKBLUE);
        gc.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    public Rectangle getNewPosition(Point destination, Rectangle currentPosition){
        Double xDistance = destination.getX() - currentPosition.getX();
        Double yDistance = destination.getY() - currentPosition.getY();
        Double hypotenuse = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        if (hypotenuse == 0) {
            destination = null;
            return null;
        }

        Double theta = Math.asin(yDistance / hypotenuse);

        Double deltaX = Math.abs(velocity * Math.cos(theta) * GameController.GAME_PERIOD) * (destination.getX() > currentPosition.getX() ? 1 : -1);
        Double deltaY = Math.abs(velocity * Math.sin(theta) * GameController.GAME_PERIOD) * (destination.getY() > currentPosition.getY() ? 1 : -1);

        boolean finalPos = true;

        Double newX;
        if (Math.abs(destination.getX() - currentPosition.getX()) < deltaX) {
            newX = destination.getX();
        } else {
            newX = currentPosition.getX() + deltaX;
            finalPos = false;
        }

        Double newY;
        if (Math.abs(destination.getY() - currentPosition.getY()) < deltaY){
            newY = destination.getY();
        } else {
            newY = currentPosition.getY() + deltaY;
            finalPos = false;
        }

        if (finalPos) {
            destination = null;
        }

        return new Rectangle(newX, newY, currentPosition.getWidth(), currentPosition.getHeight());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public Rectangle getNormalizedRect(){
        return new Rectangle(rectangle.getX() - xOffset, rectangle.getY() - yOffset, rectangle.getWidth(), rectangle.getHeight());
    }
}
