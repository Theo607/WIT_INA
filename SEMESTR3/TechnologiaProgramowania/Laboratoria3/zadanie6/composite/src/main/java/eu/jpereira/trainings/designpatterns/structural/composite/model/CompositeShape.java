package eu.jpereira.trainings.designpatterns.structural.composite.model;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositeShape extends Shape {

    private List<Shape> shapes;

    public CompositeShape() {
        this.shapes = new ArrayList<>();
    }

    @Override
    public CompositeShape asComposite() {
        return this;
    }

    public void addShape(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
        }
    }

    public boolean removeShape(Shape shape) {
        return shapes.remove(shape);
    }

    public int getShapeCount() {
        return shapes.size();
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public List<Shape> getShapesByType(ShapeType type) {
        List<Shape> result = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.getType() == type) {
                result.add(s);
            }
        }
        return result;
    }

    public List<Shape> getLeafShapes() {
        List<Shape> leafs = new ArrayList<>();
        for (Shape s : shapes) {
            if (s instanceof CompositeShape) {
                leafs.addAll(((CompositeShape) s).getLeafShapes());
            } else {
                leafs.add(s);
            }
        }
        return leafs;
    }

    @Override
    public void move(int xIncrement, int yIncrement) {
        super.move(xIncrement, yIncrement);
        for (Shape s : shapes) {
            s.move(xIncrement, yIncrement);
        }
    }
}

