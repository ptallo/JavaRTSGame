package model_layer.object_interface;

import model_layer.components.SelectionComponent;
import model_layer.components.UnitCreationComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.io.Serializable;

public class CreationUnit implements ObjectInterface, Serializable {

    private PhysicsComponent physicsComponent;
    private SelectionComponent selectionComponent;
    private UnitCreationComponent creationComponent;

    public CreationUnit(double x, double y){
        physicsComponent = new PhysicsComponent(new Rectangle(x, y, 20, 20));
        selectionComponent = new SelectionComponent(new Rectangle(x, y, 15, 40), 2.5, 0);
        selectionComponent.setRect(physicsComponent.getRectangle());

        creationComponent = new UnitCreationComponent(1000);
        creationComponent.addEntityToList(
                new GameObject(0, 0, true),
                new Point(physicsComponent.getRectangle().getX() + 1000, physicsComponent.getRectangle().getY() + 0)
        );
        creationComponent.addEntityToList(
                new GameObject(0, 0, true),
                new Point(physicsComponent.getRectangle().getX() + 1000, physicsComponent.getRectangle().getY() + 0)
        );
    }

    @Override
    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    @Override
    public RenderComponent getRenderComponent() {
        return null;
    }

    @Override
    public SelectionComponent getSelectionComponent() {
        return selectionComponent;
    }

    @Override
    public UnitCreationComponent getUnitCreationComponent() {
        return creationComponent;
    }
}
