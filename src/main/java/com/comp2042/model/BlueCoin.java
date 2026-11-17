package com.comp2042.model;

public class BlueCoin extends Coin {

    public BlueCoin(int x, int y, Snake snek, Obstacle obstacle) {
        super(x, y, snek, obstacle, 5);
    }

    @Override
    public void applyEffect(Game game, Snake snek, Obstacle obstacle) {
        super.applyEffect(game, snek, obstacle);
        snek.setIgnoreBorders(true);
    }
}