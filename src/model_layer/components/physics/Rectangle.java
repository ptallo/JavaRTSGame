package model_layer.components.physics;

import java.util.ArrayList;
import java.util.List;

public class Rectangle {

    private Double x;
    private Double y;
    private Double width;
    private Double height;

    public Rectangle(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Point start, Point end) {
        x = Math.min(start.getX(), end.getX());
        y = Math.min(start.getY(), end.getY());
        width = Math.abs(start.getX() - end.getX());
        height = Math.abs(start.getY() - end.getY());
    }

    public Boolean contains(Rectangle rectangle) {
        return rectangle.getX() < this.x + this.width && rectangle.getX() + rectangle.getWidth() > this.x &&
                rectangle.getY() < this.y + this.height && rectangle.getHeight() + rectangle.getY() > this.y;
    }
    
    /**
     * @param rectangleList : a list of rectangles this object could collide with
     * @return a list of rectangle containing any rectangles it is colliding with
     */
    public ArrayList<Rectangle> contains(List<Rectangle> rectangleList) {
        ArrayList<Rectangle> collided = new ArrayList<>();
        for (Rectangle rectangle : rectangleList) {
            if (rectangle.contains(this)) {
                collided.add(rectangle);
            }
        }
        return collided;
    }

    public Point getCenter() {
        return new Point(x + (width / 2), y + width / 2);
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public void setOrigin(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }
}
