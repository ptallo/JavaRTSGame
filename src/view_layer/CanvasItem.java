package view_layer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class CanvasItem {

    private Image image;
    private Rectangle drawRect;
    private Rectangle sourceRect;

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

    }

    public void handleKeyPressed(KeyEvent event) {
        
    }
}
