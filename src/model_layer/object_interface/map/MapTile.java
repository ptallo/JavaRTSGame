package model_layer.object_interface.map;

import model_layer.components.SelectionComponent;
import model_layer.components.UnitCreationComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.object_interface.ObjectInterface;

import java.io.Serializable;

public class MapTile implements Serializable, ObjectInterface {

    private RenderComponent renderComponent;
    private PhysicsComponent physicsComponent;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;

    public MapTile(double x, double y, String path, Boolean isCollidable) {
        renderComponent = new RenderComponent(path, new Point(x, y));
        physicsComponent = new PhysicsComponent(renderComponent.getDrawRect(), isCollidable, 0);
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
        return null;
    }

    @Override
    public UnitCreationComponent getUnitCreationComponent() {
        return null;
    }

    @Override
    public void setAnchor(Point point) {
        renderComponent.setDrawPoint(point);
    }
}
