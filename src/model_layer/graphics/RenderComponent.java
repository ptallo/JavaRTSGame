package model_layer.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model_layer.physics.Rectangle;

public class RenderComponent {

    private Image image;

    public RenderComponent(String path){
        image = new Image(getClass().getResourceAsStream(path));
    }

    public void draw(GraphicsContext gc, Rectangle rectangle){
        gc.drawImage(image, rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
}
