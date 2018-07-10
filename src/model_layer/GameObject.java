package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.components.graphics.RenderComponent;
import model_layer.components.physics.PhysicsComponent;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;

    public GameObject(double x, double y){
        physicsComponent = new PhysicsComponent(x, y, 16.0, 16.0);
        renderComponent = new RenderComponent("example-sprite.png", 8, 24);
    }

    public void update(ArrayList<GameObject> objects) {
        physicsComponent.update(objects);
    }

    public void draw(GraphicsContext gc){
        physicsComponent.draw(gc);
        renderComponent.draw(gc, physicsComponent.getRectangle());
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }
}
