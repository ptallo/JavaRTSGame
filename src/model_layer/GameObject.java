package model_layer;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GameObject implements Serializable, GameObjectInterface {

    private PhysicsComponent physicsComponent;

    public GameObject(double x, double y, int height, int width){
        physicsComponent = new PhysicsComponent(x, y, height, width);
    }

    public void update() {

    }

    public void draw(GraphicsContext gc){
        physicsComponent.draw(gc);
    }
}
