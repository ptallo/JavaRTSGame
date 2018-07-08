package model_layer;

import javafx.scene.canvas.GraphicsContext;

public interface GameObjectInterface {
    void update();
    void draw(GraphicsContext gc);
}
