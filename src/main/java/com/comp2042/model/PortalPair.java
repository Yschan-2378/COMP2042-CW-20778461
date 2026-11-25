package com.comp2042.model;

import java.awt.Point;
import java.util.Random;

public class PortalPair {
    private final int width;
    private final int height;
    private Point portalA;
    private Point portalB;
    private boolean active = false;

    public PortalPair(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void spawn(Snake snek, Obstacle obstacle, Coin coin) {
        Random rnd = new Random();
        int minDistance = (width + height) / 2;
        portalA = findFreeTile(rnd, snek, obstacle, coin, null, 0);
        portalB = findFreeTile(rnd, snek, obstacle, coin, portalA, minDistance);
        active = true;
    }

    public void setPortals(Point a, Point b) {
        this.portalA = new Point(a);
        this.portalB = new Point(b);
        this.active = true;
    }

    private Point findFreeTile(Random rnd, Snake snek, Obstacle obstacle, Coin coin, Point otherPortal, int minDistance) {
        Point p;
        int attempts = 0;
        do {
            p = new Point(rnd.nextInt(width), rnd.nextInt(height));
            attempts++;
        } while (isOccupied(p, snek, obstacle, coin, otherPortal)
                || (otherPortal != null && attempts < 100 && manhattanDistance(p, otherPortal) < minDistance));
        return p;
    }

    private boolean isOccupied(Point p, Snake snek, Obstacle obstacle, Coin coin, Point otherPortal) {
        return snek.getHead().equals(p)
                || snek.getBody().contains(p)
                || obstacle.getObstacles().contains(p)
                || (coin != null && coin.getPos().equals(p))
                || (otherPortal != null && otherPortal.equals(p));
    }

    public int manhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public Point getExitIfEntered(Point head) {
        if (!active || portalA == null || portalB == null) {
            return null;
        }
        if (head.equals(portalA)) {
            return new Point(portalB);
        }
        if (head.equals(portalB)) {
            return new Point(portalA);
        }
        return null;
    }

    public boolean isActive() {
        return active;
    }

    public Point getPortalA() {
        return portalA == null ? null : new Point(portalA);
    }

    public Point getPortalB() {
        return portalB == null ? null : new Point(portalB);
    }

    public void reset() {
        portalA = null;
        portalB = null;
        active = false;
    }
}