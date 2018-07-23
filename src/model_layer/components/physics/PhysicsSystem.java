package model_layer.components.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.object_interface.ObjectInterface;
import model_layer.object_interface.map.MapTile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhysicsSystem {

    public void update(PhysicsComponent component, ArrayList<ObjectInterface> gameObjects, ArrayList<MapTile> tiles) {
        List<PhysicsComponent> physicsComponents = gameObjects.stream().map(ObjectInterface::getPhysicsComponent).filter(
                PhysicsComponent::isCollidable).collect(Collectors.toList());

        List<PhysicsComponent> mapComponents = tiles.stream().map(MapTile::getPhysicsComponent).collect(Collectors.toList());

        if (component.isCollidable()) {
            ArrayList<PhysicsComponent> collidedComponents = getCollidedComponents(component.getRectangle(), physicsComponents);

            if (collidedComponents.size() != 0 && component.getVelocity() != 0) {
                component.separateComponent(collidedComponents.get(0).getRectangle());
            }
        }

        ArrayList<PhysicsComponent> collidedMapComponents = getCollidedComponents(component.getRectangle(), mapComponents);
        System.out.println(collidedMapComponents.size());

        if (collidedMapComponents.size() == 0) {
            Rectangle newRect = getNewPosition(component);

            if (newRect != null) {
                component.setRectangle(newRect);
            }
        }
    }

    private Rectangle getNewPosition(PhysicsComponent component) {
        if (component.getDestinations().size() > 0 && component.getDestinations().get(0) != null) {
            double newX;
            if (Math.abs(component.getCurrentDestination().getX() - component.getRectangle().getX()) < Math.abs(component.getxVelocity())) {
                newX = component.getCurrentDestination().getX();
            } else {
                newX = component.getRectangle().getX() + component.getxVelocity();
            }

            double newY;
            if (Math.abs(component.getCurrentDestination().getY() - component.getRectangle().getY()) < Math.abs(component.getyVelocity())){
                newY = component.getCurrentDestination().getY();
            } else {
                newY = component.getRectangle().getY() + component.getyVelocity();
            }

            if (newX == component.getCurrentDestination().getX() && newY == component.getCurrentDestination().getY()){
                component.removeCurrentDestination();
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
