package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.graphics.RenderComponent;
import model_layer.physics.PhysicsComponent;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {

    private PhysicsComponent physicsComponent;
    private RenderComponent renderComponent;

    public GameObject(double x, double y, double height, double width){
        physicsComponent = new PhysicsComponent(x, y, height, width);
        renderComponent = new RenderComponent("example-sprite.png");
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
