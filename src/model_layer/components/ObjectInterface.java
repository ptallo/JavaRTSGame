package model_layer.components;

import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;

public interface ObjectInterface {
    PhysicsComponent getPhysicsComponent();
    RenderComponent getRenderComponent();
    SelectionComponent getSelectionComponent();
}
