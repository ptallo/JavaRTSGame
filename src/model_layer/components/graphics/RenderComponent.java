package model_layer.components.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model_layer.components.physics.Point;
import model_layer.components.physics.Rectangle;

import java.io.File;
import java.util.HashMap;

public class RenderComponent {

    private Image image;
    private Point drawPoint;

    private HashMap<String, Animation> animations = new HashMap<>();
    private Animation currentAnimation;

    public RenderComponent(String path, Point drawPoint){
        this.drawPoint = drawPoint;
        image = new Image(new File("resources/" + path).toURI().toString());
        currentAnimation = new Animation(image, 0, 4, 32, 32, 250);
    }

    public void draw(GraphicsContext gc){
        currentAnimation.draw(gc, drawPoint);
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public Point getDrawPoint() {
        return drawPoint;
    }
}
