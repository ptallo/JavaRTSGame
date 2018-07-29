package model_layer.object_interface;

import model_layer.components.SelectionComponent;
import model_layer.components.UnitCreationComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.io.Serializable;

public class GameObject implements Serializable, ObjectInterface {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;
    private SelectionComponent selectionComponent;
    private UnitCreationComponent unitCreationComponent;

    public GameObject(double x, double y, boolean collidable, double velocity){
        physicsComponent = new PhysicsComponent(new Rectangle(x , y, 16.0, 16.0), collidable, velocity);
        Rectangle rectangle = physicsComponent.getRectangle();

        renderComponent = new RenderComponent("character.png", new Point(x, y), 32, 32, -8, -20);
        renderComponent.setDrawPoint(new Point(rectangle.getX(), rectangle.getY()));
        renderComponent.addAnimation("idle", 0, 4, 250);
        renderComponent.addAnimation("moving", 5, 8, 250);

        selectionComponent = new SelectionComponent(new Rectangle(x, y, 16.0, 32.0), 0, -20);
        selectionComponent.setRect(rectangle);

        unitCreationComponent = new UnitCreationComponent(2000);
    }

    @Override
    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    @Override
    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    @Override
    public SelectionComponent getSelectionComponent() {
        return selectionComponent;
    }

    @Override
    public UnitCreationComponent getUnitCreationComponent() {
        return unitCreationComponent;
    }

    @Override
    public void setAnchor(Point point) {
        physicsComponent.getRectangle().setOrigin(point);
        selectionComponent.setAnchor(point);
        renderComponent.setDrawPoint(point);
    }
}
