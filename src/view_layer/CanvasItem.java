package view_layer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;



public abstract class CanvasItem {

    private Image image;
    private Rectangle drawRect;
    private Rectangle sourceRect;

    abstract void activate(MouseEvent event);

    public CanvasItem(Image image, Rectangle drawRect, Rectangle sourceRect) {
        this.image = image;
        this.drawRect = drawRect;
        this.sourceRect = sourceRect;
    }

    public void draw(GraphicsContext gc) {
        gc.drawImage(
                image,
                sourceRect.getX(), sourceRect.getY(), sourceRect.getWidth(), sourceRect.getHeight(),
                drawRect.getX(), drawRect.getY(), drawRect.getWidth(), drawRect.getHeight()
        );
        gc.setStroke(Color.BLACK);
        gc.strokeRect(drawRect.getX(), drawRect.getY(), drawRect.getWidth(), drawRect.getHeight());
    }

    public void setDrawPoint(Point point) {
        drawRect.setX(point.getX());
        drawRect.setY(point.getY());
    }

    public void handleMouseEvent(MouseEvent event) {
        if (event.getX() < drawRect.getX() + drawRect.getWidth() && event.getX() > drawRect.getX() &&
                event.getY() < drawRect.getY() + drawRect.getHeight() && event.getY() > drawRect.getY()) {
            activate(event);
        }
    }

    public void handleKeyEvent(KeyEvent event) {

    }

}
