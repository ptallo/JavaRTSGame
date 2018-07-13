package model_layer.components;

import model_layer.components.physics.PhysicsComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnitCreationSystem {
    public ObjectInterface getEntity(UnitCreationComponent component) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - component.getCreationStarted() > component.getCreationDuration()) {
            return component.getObjectFromQueue();
        }
        return null;
    }

    public void update(UnitCreationComponent component, ArrayList<ObjectInterface> gameObjects) {
        List<PhysicsComponent> collidableComponents = gameObjects.stream().map(ObjectInterface::getPhysicsComponent)
                .filter(PhysicsComponent::isCollidable).collect(Collectors.toList());

        ObjectInterface entity = getEntity(component);
        if (entity != null) {
            entity.getPhysicsComponent().setDestination(component.getCreationDestination());
            while (isCollided(entity, collidableComponents)) {
                resolveCollision(entity, collidableComponents);
            }
        }
    }

    public void resolveCollision(ObjectInterface entity, List<PhysicsComponent> collidableObjects) {

    }

    public Boolean isCollided(ObjectInterface entity, List<PhysicsComponent> collidableComponents){
        if (entity.getPhysicsComponent() != null) {
            boolean collided = false;
            for (PhysicsComponent component : collidableComponents){
                if (entity.getPhysicsComponent().getRectangle().contains(component.getRectangle())) {
                    collided = true;
                }
            }
            return collided;
        }
        return false;
    }
}
