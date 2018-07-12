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

    private Double xOffset;
    private Double yOffset;

    private Double frameWidth;
    private Double frameHeight;

    private HashMap<String, Animation> animations = new HashMap<>();
    private Animation currentAnimation;
    private String currentAnimKey;

    public RenderComponent(String path, Point drawPoint, double frameWidth, double frameHeight, double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.drawPoint = drawPoint;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
        image = new Image(new File("resources/" + path).toURI().toString());
    }

    public void addAnimation(String key, int startFrame, int endFrame, long animationDuration){
        Animation animation = new Animation(image, startFrame, endFrame, frameWidth, frameHeight, animationDuration);
        animations.put(key, animation);
        if (currentAnimation == null) {
            currentAnimation = animation;
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

    public Image getImage() {
        return image;
    }

    public Double getFrameWidth() {
        return frameWidth;
    }

    public Double getFrameHeight() {
        return frameHeight;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    public void setDrawPoint(Point drawPoint) {
        this.drawPoint = new Point(drawPoint.getX() + xOffset, drawPoint.getY() + yOffset);
    }

    public Point getDrawPoint() {
        return drawPoint;
    }
}
