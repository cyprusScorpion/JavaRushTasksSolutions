package com.javarush.task.task34.task3410.model;

import java.awt.*;

public class Player extends CollisionObject implements Movable {

  public Player(int x, int y) {
    super(x, y);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.setColor(Color.RED);
    int leftUpperCornerX = getX() - getWidth() / 2;
    int leftUpperCornerY = getY() - getHeight() / 2;
    graphics.drawOval(leftUpperCornerX, leftUpperCornerY,
            width, height);
    graphics.fillOval(leftUpperCornerX, leftUpperCornerY,
            width, height);
  }

  @Override
  public void move(int x, int y) {
    this.setX(getX() + x);
    this.setY(getY() + y);
  }
}
