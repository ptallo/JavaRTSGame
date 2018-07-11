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

    private final double xOffsetPhys = 8;
    private final double yOffsetPhys = 20;
    private final double xOffsetSelect = 8;
    private final double yOffsetSelect = 0;

    public GameObject(double x, double y){
        renderComponent = new RenderComponent("character.png", new Point(x, y), 32, 32);
        renderComponent.addAnimation("idle", 0, 4);
        renderComponent.addAnimation("moving", 5, 8);
        physicsComponent = new PhysicsComponent(new Rectangle(x + xOffsetPhys, y + yOffsetPhys, 16.0, 16.0), xOffsetPhys, yOffsetPhys);
        selectionComponent = new SelectionComponent(new Rectangle(x + xOffsetSelect, y + yOffsetSelect, 16.0, 32.0), xOffsetSelect, yOffsetSelect);
    }

    public void update(ArrayList<GameObject> objects) {
        Boolean updated = physicsComponent.update(objects);
        if (updated) {
            renderComponent.setCurrentAnimation("moving");
            Rectangle defaultRect = physicsComponent.getNormalizedRect();
            selectionComponent.setRect(defaultRect);
            renderComponent.setDrawPoint(new Point(defaultRect.getX(), defaultRect.getY()));
        } else {
            renderComponent.setCurrentAnimation("idle");
        }
    }

    public void draw(GraphicsContext gc){
        physicsComponent.draw(gc);
        renderComponent.draw(gc);
        selectionComponent.draw(gc);
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }

    public boolean checkSelected(Rectangle selectionRect){
        return selectionComponent.checkSelected(selectionRect);
    }
}
