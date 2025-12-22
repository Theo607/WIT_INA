package eu.jpereira.trainings.designpatterns.structural.composite.model;

public abstract class Shape {
    private int x;
    private int y;

    public CompositeShape asComposite() {
        return null; 
    }

    public void move(int xIncrement, int yIncrement) {
        this.x += xIncrement;
        this.y += yIncrement;
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public abstract ShapeType getType();
}

