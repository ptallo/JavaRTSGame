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

    private void tryToPlaceObject(Point creationPoint, ObjectInterface newEntity, ObjectInterface creationEntity) {
        if (newEntity.getPhysicsComponent() != null && creationEntity.getPhysicsComponent() != null) {
            if (newEntity.getPhysicsComponent().isCollidable() && creationEntity.getPhysicsComponent().isCollidable()) {
                placeObject(creationPoint, newEntity, creationEntity);
            }
        }
    }

    private void placeObject(Point creationPoint, ObjectInterface newEntity, ObjectInterface creationEntity) {
        PhysicsComponent entityComponent = newEntity.getPhysicsComponent();
        PhysicsComponent creationComponent = creationEntity.getPhysicsComponent();

        if (creationPoint == null){
            creationPoint = new Point(
                    creationComponent.getRectangle().getX() + creationComponent.getRectangle().getWidth(),
                    creationComponent.getRectangle().getY() + creationComponent.getRectangle().getHeight()
            );
        }

        Double xDistance = creationPoint.getX() - entityComponent.getRectangle().getX();
        Double yDistance = creationPoint.getY() - entityComponent.getRectangle().getY();
        Double hypotenuse = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        if (hypotenuse == 0) {
            return;
        }

        Double theta = Math.asin(yDistance / hypotenuse);

        double percentage = 0;

        entityComponent.setRectangle(creationComponent.getRectangle());
        while (creationComponent.getRectangle().contains(entityComponent.getRectangle())) {
            percentage += 0.01;
            Double deltaX = Math.acos(theta) * percentage * GameController.GAME_PERIOD *
                    (entityComponent.getRectangle().getX() > creationPoint.getX() ? -1 : 1);
            Double deltaY = Math.asin(theta) * percentage * GameController.GAME_PERIOD *
                    (entityComponent.getRectangle().getY() > creationPoint.getY() ? -1 : 1);

            entityComponent.getRectangle().setX(entityComponent.getRectangle().getX() + deltaX);
            entityComponent.getRectangle().setY(entityComponent.getRectangle().getY() + deltaY);
        }
    }
}
