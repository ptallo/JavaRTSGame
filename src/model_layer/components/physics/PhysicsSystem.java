package model_layer.components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhysicsSystem {

    public Boolean update(PhysicsComponent component, ArrayList<ObjectInterface> gameObjects) {
        List<PhysicsComponent> physicsComponents = gameObjects.stream().map(ObjectInterface::getPhysicsComponent).filter(
                PhysicsComponent::isCollidable).collect(Collectors.toList());

        Rectangle newRect = getNewPosition(component);
        if (newRect != null) {
            ArrayList<PhysicsComponent> collidedComponents = component.isCollidable() ?
                    getCollidedComponents(newRect, physicsComponents) : new ArrayList<>();

            if (collidedComponents.size() == 0){
                component.setRectangle(newRect);
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

    private ArrayList<PhysicsComponent> getCollidedComponents(Rectangle rectangle, List<PhysicsComponent> collidableComponents) {
        ArrayList<PhysicsComponent> collidedComponents = new ArrayList<>();
        for (PhysicsComponent collidableComponent : collidableComponents) {
            if (collidableComponent.getRectangle().contains(rectangle)) {
                collidedComponents.add(collidableComponent);
            }
        }
        return collidedComponents;
    }

    private Rectangle getNewPosition(PhysicsComponent component) {
        if (component.getDestination() != null) {
            double newX;
            boolean atXDestination = false;
            if (Math.abs(component.getDestination().getX() - component.getRectangle().getX()) < Math.abs(component.getXVelocity())) {
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
