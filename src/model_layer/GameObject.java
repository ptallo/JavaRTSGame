package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.components.ObjectInterface;
import model_layer.components.SelectionComponent;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;
import org.w3c.dom.css.Rect;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GameObject implements Serializable, ObjectInterface {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;
    private SelectionComponent selectionComponent;

    public GameObject(double x, double y, boolean collidable){
        physicsComponent = new PhysicsComponent(new Rectangle(x , y, 16.0, 16.0), collidable);
        Rectangle rectangle = physicsComponent.getRectangle();
        renderComponent = new RenderComponent("character.png", new Point(x, y), 32, 32, -8, -20);
        renderComponent.setDrawPoint(new Point(rectangle.getX(), rectangle.getY()));
        renderComponent.addAnimation("idle", 0, 4, 250);
        renderComponent.addAnimation("moving", 5, 8, 250);
        selectionComponent = new SelectionComponent(new Rectangle(x, y, 16.0, 32.0), 0, -20);
        selectionComponent.setRect(rectangle);
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public RenderComponent getRenderComponent() {
        return renderComponent;
    }

    public SelectionComponent getSelectionComponent() {
        return selectionComponent;
    }
}
