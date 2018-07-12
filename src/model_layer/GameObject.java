package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.components.SelectionComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;
import org.w3c.dom.css.Rect;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameObject implements Serializable {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;
    private SelectionComponent selectionComponent;

    public GameObject(double x, double y){
        renderComponent = new RenderComponent("character.png", new Point(x, y), 32, 32);
        renderComponent.addAnimation("idle", 0, 4);
        renderComponent.addAnimation("moving", 5, 8);
        physicsComponent = new PhysicsComponent(new Rectangle(x + 8, y + 20, 16.0, 16.0), 8.0, 20.0);
        selectionComponent = new SelectionComponent(new Rectangle(x + 8, y + 0, 16.0, 32.0), 8.0, 0.0);
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public boolean checkSelected(Rectangle selectionRect){
        return selectionComponent.checkSelected(selectionRect);
    }
}
