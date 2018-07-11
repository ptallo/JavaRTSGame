package model_layer.components;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model_layer.components.physics.Rectangle;

public class SelectionComponent {

    private Rectangle rect;
    private Double xOffset;
    private Double yOffset;

    private Boolean isSelected;

    public SelectionComponent(Rectangle rect, Double xOffset, Double yOffset) {
        this.rect = rect;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void draw(GraphicsContext gc){
        gc.setStroke(Color.AQUAMARINE);
        gc.strokeRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public Boolean checkSelected(Rectangle selectedRect){
        Boolean selected = selectedRect.contains(rect);
        isSelected = selected;
        return selected;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = new Rectangle(rect.getX() + xOffset, rect.getY() + yOffset, this.rect.getWidth(), this.rect.getHeight());
    }

    public Boolean getSelected() {
        return isSelected;
    }
}
