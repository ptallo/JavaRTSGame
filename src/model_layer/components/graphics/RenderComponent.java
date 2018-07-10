package model_layer.components.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model_layer.components.physics.Rectangle;

import java.io.File;

public class RenderComponent {

    private Image image;
    private Double xOffset;
    private Double yOffset;

    public RenderComponent(String path, double xOffset, double yOffset){
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        image = new Image(new File("resources/" + path).toURI().toString());
    }

    public void draw(GraphicsContext gc, Rectangle rectangle){
        gc.drawImage(image, rectangle.getX() - xOffset, rectangle.getY() - yOffset, image.getWidth(), image.getHeight());
    }
}
