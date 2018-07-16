package model_layer.components;

import controller_layer.GameController;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.object_interface.ObjectInterface;

public class UnitCreationSystem {
    public ObjectInterface update(ObjectInterface object) {
        ObjectInterface entity = getEntity(object.getUnitCreationComponent());
        if (entity != null) {
            entity.setAnchor(new Point(
                    object.getPhysicsComponent().getRectangle().getX(),
                    object.getPhysicsComponent().getRectangle().getY() + 100
            ));
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
