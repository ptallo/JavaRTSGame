package model_layer.components;

import controller_layer.GameController;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.object_interface.ObjectInterface;

public class UnitCreationSystem {
    public ObjectInterface update(ObjectInterface object, Point point) {
        ObjectInterface entity = getEntity(object.getUnitCreationComponent());
        if (entity != null) {
            entity.setAnchor(point);
            if (entity.getPhysicsComponent() != null) {
                entity.getPhysicsComponent().setDestination(object.getUnitCreationComponent().getCreationPoints().get(entity));
            }
        }
        return entity;
    }

    private ObjectInterface getEntity(UnitCreationComponent component) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - component.getCreationStarted() > component.getCreationDuration()) {
            return component.getEntityFromQueue();
        }
        return null;
    }
}
