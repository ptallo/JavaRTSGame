package model_layer.components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhysicsSystem {

    public Boolean update(PhysicsComponent component, ArrayList<GameObject> gameObjects){
        List<PhysicsComponent> physicsComponents = gameObjects.stream().map(GameObject::getPhysicsComponent).filter(
                PhysicsComponent::isCollidable).collect(Collectors.toList());
        if (component.getDestination() != null) {
            Rectangle tempRect = getNewPosition(component);
            if (tempRect != null){
                for (PhysicsComponent arrayComponent : physicsComponents){
                    if (tempRect.contains(arrayComponent.getRectangle())){
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
        if (component.getDestination() != null){

            double newX;
            boolean atXDestination = false;
            if (Math.abs(component.getDestination().getX() - component.getRectangle().getX()) < Math.abs(component.getXVelocity())){
                newX = component.getDestination().getX();
                atXDestination = true;
            } else {
                newX = component.getRectangle().getX() + component.getXVelocity();
            }

            double newY;
            boolean atYDestination = false;
            if (Math.abs(component.getDestination().getY() - component.getRectangle().getY()) < Math.abs(component.getYVelocity())){
                newY = component.getDestination().getY();
                atYDestination = true;
            } else {
                newY = component.getRectangle().getY() + component.getYVelocity();
            }

            if (atXDestination && atYDestination){
                component.setDestination(null);
            }

            return new Rectangle(newX, newY, component.getRectangle().getWidth(), component.getRectangle().getHeight());
        }
        return null;
    }
}
