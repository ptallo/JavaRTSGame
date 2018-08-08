package model_layer.components.selection;

import model_layer.components.Point;
import model_layer.components.Rectangle;

public class SelectionComponent {

    private Rectangle rect;
    private Double xOffset;
    private Double yOffset;

    private Boolean isSelected;

    public SelectionComponent(Rectangle rect, double xOffset, double yOffset) {
        this.rect = rect;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
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

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public void setAnchor(Point point){
        rect = new Rectangle(
                point.getX() + xOffset,
                point.getY() + yOffset,
                rect.getWidth(),
                rect.getHeight()
        );
    }
}
