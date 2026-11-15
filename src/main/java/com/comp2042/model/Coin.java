package com.comp2042.model;

import java.awt.Point;
import java.util.Random;

public class Coin {
    private Point pos;
    private final int points;

    public Coin(int x, int y, Snake snek, Obstacle obstacle) {
        this(x, y, snek, obstacle, 5);
    }

    protected Coin(int x, int y, Snake snek, Obstacle obstacle, int points) {
        this.points = points;
        int tx;
        int ty;
        Random rnd = new Random();

        do {
            tx = rnd.nextInt(x);
            ty = rnd.nextInt(y);
            pos = new Point(tx, ty);
        } while (snek.getHead().equals(pos) || snek.getBody().contains(pos) || obstacle.getObstacles().contains(pos));
    }

    public void applyEffect(Game game, Snake snek, Obstacle obstacle) {
        game.addPoints(points);
    }

    public double getSpeedModifier() {
        return 0.0;
    }

    public Point getPos() {
        return new Point(pos);
    }

    public int getPoints() {
        return points;
    }
}