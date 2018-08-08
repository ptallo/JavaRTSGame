package model_layer.components.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model_layer.components.Point;

public class RenderSystem {
    public void draw(GraphicsContext gc, RenderComponent component) {
        Animation currentAnimation = component.getCurrentAnimation();
        Point drawPoint = component.getDrawPoint();
        Image image = component.getImage();

        if (currentAnimation != null) {
            currentAnimation.draw(gc, drawPoint);
        } else {
            gc.drawImage(image, drawPoint.getX(), drawPoint.getY(), image.getWidth(), image.getHeight());
        }
    }
}
