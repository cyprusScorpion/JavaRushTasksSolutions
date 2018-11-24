package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.EventListener;
import com.javarush.task.task34.task3410.model.*;
import com.javarush.task.task34.task3410.model.Box;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

public class Field extends JPanel {
  private View view;
  private EventListener eventListener;

  public Field(View view) {
    this.view = view;
    KeyHandler keyHandler = new KeyHandler();
    addKeyListener(keyHandler);
    setFocusable(true);
  }

  public void paint(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, 500, 500);
    Set<GameObject> gameObjectSet = view.getGameObjects()
            .getAll();
    for(GameObject gameObject : gameObjectSet) {
      gameObject.draw(g);
    }
//    Player player = new Player(200, 100);
//    player.draw(g);
//    Box box = new Box(300, 450);
//    box.draw(g);
//    Home home = new Home(350, 350);
//    home.draw(g);
//    Wall wall = new Wall(100, 100);
//    wall.draw(g);
  }

  public void setEventListener(EventListener eventListener) {
    this.eventListener = eventListener;
  }

  public class KeyHandler extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      //super.keyPressed(e);
      switch (e.getKeyCode()) {
        case KeyEvent.VK_LEFT:
          eventListener.move(Direction.LEFT);
        case KeyEvent.VK_RIGHT:
          eventListener.move(Direction.RIGHT);
        case KeyEvent.VK_KP_UP:
          eventListener.move(Direction.UP);
        case KeyEvent.VK_DOWN:
          eventListener.move(Direction.DOWN);
        case KeyEvent.VK_R:
          eventListener.restart();
      }
    }
  }
}
