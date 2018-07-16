package model_layer.components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.object_interface.ObjectInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhysicsSystem {

    public void update(PhysicsComponent component, ArrayList<ObjectInterface> gameObjects) {
        List<PhysicsComponent> physicsComponents = gameObjects.stream().map(ObjectInterface::getPhysicsComponent).filter(
                PhysicsComponent::isCollidable).collect(Collectors.toList());

        if (component.isCollidable()) {
            ArrayList<PhysicsComponent> collidedComponents = getCollidedComponents(component.getRectangle(), physicsComponents);

            if (collidedComponents.size() != 0) {
                double uDir = collidedComponents.get(0).getRectangle().getX() - component.getRectangle().getX() > 0  ? -1.0 : 1.0;
                component.setDestination(new Point(
                        component.getRectangle().getX() + (uDir * component.getRectangle().getWidth()),
                        component.getRectangle().getY()
                ));
            }
        }

        Rectangle newRect = getNewPosition(component);

        if (newRect != null) {
            component.setRectangle(newRect);
        }
    }

    private Rectangle getNewPosition(PhysicsComponent component) {
        if (component.getDestination() != null) {
            double newX;
            if (Math.abs(component.getDestination().getX() - component.getRectangle().getX()) < Math.abs(component.getxVelocity())) {
                newX = component.getDestination().getX();
            } else {
                newX = component.getRectangle().getX() + component.getxVelocity();
            }

            double newY;
            if (Math.abs(component.getDestination().getY() - component.getRectangle().getY()) < Math.abs(component.getyVelocity())){
                newY = component.getDestination().getY();
            } else {
                newY = component.getRectangle().getY() + component.getyVelocity();
            }

            if (newX == component.getDestination().getX() && newY == component.getDestination().getY()){
                component.setDestination(null);
            }

            return new Rectangle(newX, newY, component.getRectangle().getWidth(), component.getRectangle().getHeight());
        }
        return null;
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

    public void draw(GraphicsContext gc, PhysicsComponent component) {
        if (component.isCollidable()){
            gc.setStroke(Color.DARKBLUE);
        } else {
            gc.setStroke(Color.DARKGREEN);
        }
        Rectangle rectangle = component.getRectangle();
        gc.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
}
