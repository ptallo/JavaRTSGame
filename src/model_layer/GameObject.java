package model_layer;

import javafx.scene.canvas.GraphicsContext;
import model_layer.physics.PhysicsComponent;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {

    private PhysicsComponent physicsComponent;

    public GameObject(double x, double y, double height, double width){
        physicsComponent = new PhysicsComponent(x, y, height, width);
    }

    public void update(ArrayList<GameObject> objects) {
        physicsComponent.update(objects);
    }

    public void draw(GraphicsContext gc){
        physicsComponent.draw(gc);
    }

    public PhysicsComponent getPhysicsComponent() {
        return physicsComponent;
    }
}
