package model_layer.components.physics;

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

    public Boolean contains(Rectangle rectangle){
        return rectangle.getX() < this.x + this.width && rectangle.getX() + rectangle.getWidth() > this.x &&
                rectangle.getY() < this.y + this.height && rectangle.getHeight() + rectangle.getY() > this.y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }
}
