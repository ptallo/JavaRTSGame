package model_layer.physics;

public class Rect {

    private Double x;
    private Double y;
    private Double width;
    private Double height;

    public Rect(Double x, Double y, Double width, Double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rect(Point start, Point end) {
        x = Math.min(start.getX(), end.getX());
        y = Math.min(start.getY(), end.getY());
        width = Math.abs(start.getX() - end.getX());
        height = Math.abs(start.getY() - end.getY());
    }

    public Boolean contains(Rect rect){
        return false;
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
