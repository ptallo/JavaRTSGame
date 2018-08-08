package model_layer.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.components.physics.Rectangle;

public class SelectionSystem {
    public void draw(GraphicsContext gc, SelectionComponent component){
        gc.setStroke(Color.AQUAMARINE);
        Rectangle rect = component.getRect();
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public Boolean checkSelected(Rectangle selectedRect, SelectionComponent component){
        Boolean selected = selectedRect.contains(component.getRect());
        component.setSelected(selected);
        return selected;
    }
}
