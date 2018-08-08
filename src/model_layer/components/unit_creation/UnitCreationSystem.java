package model_layer.components.unit_creation;

import model_layer.components.Point;
import model_layer.object_interface.ObjectInterface;

public class UnitCreationSystem {
    public ObjectInterface update(ObjectInterface object, Point point) {
        ObjectInterface entity = getEntity(object.getUnitCreationComponent());
        if (entity != null) {
            entity.setAnchor(point);
            if (entity.getPhysicsComponent() != null) {
                entity.getPhysicsComponent().insertDestination(object.getUnitCreationComponent().getCreationPoints().get(entity), 0);
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
