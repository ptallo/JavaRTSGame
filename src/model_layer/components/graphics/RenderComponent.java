package model_layer.components.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.io.File;

public class RenderComponent {

    private Image image;
    private Point drawPoint;

    public RenderComponent(String path, Point drawPoint){
        this.drawPoint = drawPoint;
        image = new Image(new File("resources/" + path).toURI().toString());
    }

    public void draw(GraphicsContext gc){
        gc.drawImage(image, drawPoint.getX(), drawPoint.getY(), image.getWidth(), image.getHeight());
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public Point getDrawPoint() {
        return drawPoint;
    }
}
