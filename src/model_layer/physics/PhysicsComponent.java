package model_layer.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.GameObjectInterface;

import java.io.Serializable;

public class PhysicsComponent implements Serializable, GameObjectInterface {

    private Rect rect;

    public PhysicsComponent(Double x, Double y, Double width, Double height) {
        this.rect = new Rect(x, y, width, height);
    }

    public void update() {

    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public Rect getRect() {
        return rect;
    }
}
