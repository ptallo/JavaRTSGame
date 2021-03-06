package model_layer.object_interface.map;

import model_layer.Player;
import model_layer.components.selection.SelectionComponent;
import model_layer.components.unit_creation.UnitCreationComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.Point;
import model_layer.object_interface.ObjectInterface;

import java.io.Serializable;

public class MapTile implements Serializable, ObjectInterface {

    private RenderComponent renderComponent;
    private PhysicsComponent physicsComponent;
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;

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
    public Player getOwner() {
        return null;
    }

    @Override
    public void setAnchor(Point point) {
        renderComponent.setDrawPoint(point);
    }
}
