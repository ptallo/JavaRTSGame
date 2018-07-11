package model_layer.components.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model_layer.components.physics.Point;

public class Animation {

    private long animationDuration;
    private Image image;

    private Integer currentFrame;
    private Integer startFrame;
    private Integer endFrame;
    private Double frameWidth;
    private Double frameHeight;

    private long lastUpdated;

    public Animation(Image image, int startFrame, int endFrame, double frameWidth, double frameHeight, long animationDuration) {
        this.image = image;
        this.startFrame = startFrame;
        this.currentFrame = startFrame;
        this.endFrame = endFrame;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.animationDuration = animationDuration;
    }

    public void animate() {
        long currentTime = System.currentTimeMillis();
        if (lastUpdated != 0 && (Math.abs(currentTime - lastUpdated) > animationDuration)){
            if (currentFrame < endFrame){
                currentFrame++;
            } else {
                currentFrame = startFrame;
            }
            lastUpdated = currentTime;
        } else if (lastUpdated == 0){
            lastUpdated = currentTime;
        }
    }

    public void draw(GraphicsContext gc, Point drawPoint){
        animate();

        Point currentPoint = calculateCurrentFramePoint();

        gc.drawImage(
                image,
                currentPoint.getX(), currentPoint.getY(), frameWidth, frameHeight,
                drawPoint.getX(), drawPoint.getY(), frameWidth, frameHeight
        );
    }

    public Point calculateCurrentFramePoint(){
        double unmodifiedX = frameWidth * currentFrame;
        double xCoord = unmodifiedX > image.getWidth() ? unmodifiedX % image.getWidth() : unmodifiedX;
        int yCoord = unmodifiedX > image.getWidth() ? (int) (unmodifiedX / image.getWidth()) : 0;
        return new Point(xCoord, (double) yCoord);
    }

    public void reset(){
        currentFrame = startFrame;
    }
}
