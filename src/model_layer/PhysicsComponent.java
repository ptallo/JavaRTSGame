package model_layer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class PhysicsComponent implements Serializable, GameObjectInterface {

    private Double x;
    private Double y;
    private Integer width;
    private Integer height;

    public PhysicsComponent(Double x, Double y, Integer width, Integer height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {

    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(x, y, width, height);
    }
}
