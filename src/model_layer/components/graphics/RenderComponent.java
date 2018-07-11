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

    private Double frameWidth;
    private Double frameHeight;

    private HashMap<String, Animation> animations = new HashMap<>();
    private Animation currentAnimation;
    private String currentAnimKey;

    public RenderComponent(String path, Point drawPoint, double frameWidth, double frameHeight) {
        this.drawPoint = drawPoint;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        image = new Image(new File("resources/" + path).toURI().toString());
    }

    public void draw(GraphicsContext gc) {
        if (currentAnimation != null){
            currentAnimation.draw(gc, drawPoint);
        } else {
            gc.drawImage(image, drawPoint.getX(), drawPoint.getY(), image.getWidth(), image.getHeight());
        }
    }

    public void addAnimation(String key, int startFrame, int endFrame) {
        Animation newAnim = new Animation(image, startFrame, endFrame, frameWidth, frameHeight, 250);
        animations.put(key, newAnim);
        if (currentAnimation == null) {
            currentAnimation = newAnim;
            currentAnimKey = key;
        }
    }

    public void setCurrentAnimation(String key) {
        if (currentAnimKey != null && !currentAnimKey.equalsIgnoreCase(key)) {
            Animation animation = animations.get(key);
            if (animation != null) {
                currentAnimation.reset();
                currentAnimation = animation;
                currentAnimKey = key;
            }
        }
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = drawPoint;
    }

    public Point getDrawPoint() {
        return drawPoint;
    }
}
