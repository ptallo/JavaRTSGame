package model_layer.components;

import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnitCreationSystem {
    public ObjectInterface getEntity(UnitCreationComponent component) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - component.getCreationStarted() > component.getCreationDuration()) {
            return component.getEntityFromQueue();
        }
        return null;
    }

    public void update(UnitCreationComponent component, ArrayList<ObjectInterface> gameObjects) {
        List<PhysicsComponent> collidableComponents = gameObjects.stream().map(ObjectInterface::getPhysicsComponent)
                .filter(PhysicsComponent::isCollidable).collect(Collectors.toList());

        ObjectInterface entity = getEntity(component);
        if (entity != null) {
            Point creationPoint = component.getCreationPoints().get(entity);
            placeObject(creationPoint, entity, collidableComponents);
        }
    }

    public void placeObject(Point creationPoint, ObjectInterface object, List<PhysicsComponent> collidableComponents){
        object.getPhysicsComponent().getRectangle().setOrigin(creationPoint);

        List<Rectangle> rectangles = collidableComponents.stream().map(PhysicsComponent::getRectangle).collect(Collectors.toList());
        ArrayList<Rectangle> collidedRectangles = object.getPhysicsComponent().getRectangle().contains(rectangles);
        if (collidedRectangles.size() > 0){
            object.getPhysicsComponent().getRectangle().resolveCollision(collidedRectangles);
        }
    }
}
