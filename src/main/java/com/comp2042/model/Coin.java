package com.comp2042.model;

import javafx.scene.image.Image;

import java.awt.Point;
import java.util.Random;

public class Coin {
    private Point pos;

    // TODO: This JavaFX View component should eventually be moved to GameView to perfectly satisfy MVC
    private Image image = new Image("resources/views/res/redcoin.png");

    public Coin(int x, int y, Snake snek, Obstacle obstacle) {
        int tx;
        int ty;
        Random rnd = new Random();

        do {
            tx = rnd.nextInt(x);
            ty = rnd.nextInt(y);
            pos = new Point(tx, ty);
        } while (snek.getHead().equals(pos) || snek.getBody().contains(pos) || obstacle.getObstacles().contains(pos));
    }

    public Point getPos() {
        return new Point(pos);
    }

    public int getPoints() {
        return 5;
    }

    public Image getImage() {
        return image;
    }
}