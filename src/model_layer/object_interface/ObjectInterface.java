package model_layer.object_interface;

import model_layer.Player;
import model_layer.components.selection.SelectionComponent;
import model_layer.components.unit_creation.UnitCreationComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.Point;

public interface ObjectInterface {
    PhysicsComponent getPhysicsComponent();
    RenderComponent getRenderComponent();
    SelectionComponent getSelectionComponent();
    UnitCreationComponent getUnitCreationComponent();
    Player getOwner();
    void setAnchor(Point point);
}
