package com.javarush.task.task37.task3710.decorators;

import com.javarush.task.task37.task3710.shapes.Shape;

public class RedShapeDecorator extends ShapeDecorator {
    protected Shape decoratedShape;

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
        this.decoratedShape = decoratedShape;
    }

    private void setBorderColor(Shape decoratedShape) {
        System.out.println("Setting border color for " +
                decoratedShape.getClass().getSimpleName() + " to red.");
    }

    @Override
    public void draw() {
        setBorderColor(decoratedShape);
        decoratedShape.draw();
    }
}
