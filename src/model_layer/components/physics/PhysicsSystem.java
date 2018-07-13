package model_layer.components.physics;

import controller_layer.GameController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PhysicsSystem {

    public Boolean update(PhysicsComponent component, ArrayList<PhysicsComponent> componentArrayList){
        if (component.getDestination() != null) {
            Rectangle tempRect = getNewPosition(component);
            if (tempRect != null){
                for (PhysicsComponent arrayComponent : componentArrayList){
                    if (component.isCollidable() && arrayComponent.isCollidable() && tempRect.contains(arrayComponent.getRectangle())){
                        return false;
                    }
                }

                component.setRectangle(tempRect);
                return true;
            }
        }
        return false;
    }

    public void draw(GraphicsContext gc, PhysicsComponent component) {
        if (component.isCollidable()){
            gc.setStroke(Color.DARKBLUE);
        } else {
            gc.setStroke(Color.DARKGREEN);
        }
        Rectangle rectangle = component.getRectangle();
        gc.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }

    private Rectangle getNewPosition(PhysicsComponent component){
        Double xDistance = component.getDestination().getX() - component.getRectangle().getX();
        Double yDistance = component.getDestination().getY() - component.getRectangle().getY();
        Double hypotenuse = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        if (hypotenuse == 0) {
            component.setDestination(null);
            return null;
        }

        Double theta = Math.asin(yDistance / hypotenuse);

        Double deltaX = Math.abs(component.getVelocity() * Math.cos(theta) * GameController.GAME_PERIOD) * (component.getDestination().getX() > component.getRectangle().getX() ? 1 : -1);
        Double deltaY = Math.abs(component.getVelocity() * Math.sin(theta) * GameController.GAME_PERIOD) * (component.getDestination().getY() > component.getRectangle().getY() ? 1 : -1);

        boolean finalPos = true;

        Double newX;
        if (Math.abs(component.getDestination().getX() - component.getRectangle().getX()) < deltaX) {
            newX = component.getDestination().getX();
        } else {
            newX = component.getRectangle().getX() + deltaX;
            finalPos = false;
        }

        Double newY;
        if (Math.abs(component.getDestination().getY() - component.getRectangle().getY()) < deltaY){
            newY = component.getDestination().getY();
        } else {
            newY = component.getRectangle().getY() + deltaY;
            finalPos = false;
        }

        if (finalPos) {
            component.setDestination(null);
        }

        return new Rectangle(newX, newY, component.getRectangle().getWidth(), component.getRectangle().getHeight());
    }
}
