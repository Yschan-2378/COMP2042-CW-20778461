package com.comp2042.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Obstacle {

    private ArrayList<Point> obstacles = new ArrayList<>();
    private int x;
    private int y;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void newObstacles(Snake snek, Coin coin, int amount) {
        if (obstacles.size() > 20) {
            return;
        }
        Random rnd = new Random();
        Point p;
        for (int i = 0; i < amount; i++) {
            do {
                p = new Point(rnd.nextInt(x), rnd.nextInt(y));
            } while (isInvalidSpawn(p, snek, coin));
            obstacles.add(p);
        }
    }

    private boolean isInvalidSpawn(Point p, Snake snek, Coin coin) {
        boolean nearHead = Math.abs(p.x - snek.getHead().x) <= 3 && Math.abs(p.y - snek.getHead().y) <= 3;
        return nearHead
                || coin.getPos().equals(p)
                || snek.getBody().contains(p)
                || obstacles.contains(p);
    }

    public ArrayList<Point> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    public void reset() {
        obstacles.clear();
    }
}