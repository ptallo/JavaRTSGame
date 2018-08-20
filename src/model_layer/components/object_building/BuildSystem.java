package model_layer.components.object_building;

import javafx.scene.canvas.GraphicsContext;
import model_layer.components.Rectangle;

public class BuildSystem {
    public void update(BuildComponent component) {
        component.incrementTicks();
    }

    public void draw(GraphicsContext gc, BuildComponent component, Rectangle objectRect) {

    }
}
