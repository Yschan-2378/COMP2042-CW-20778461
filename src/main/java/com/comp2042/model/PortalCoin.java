package com.comp2042.model;

public class PortalCoin extends Coin {

    public PortalCoin(int x, int y, Snake snek, Obstacle obstacle) {
        super(x, y, snek, obstacle, 10);
    }

    @Override
    public void applyEffect(Game game, Snake snek, Obstacle obstacle) {
        super.applyEffect(game, snek, obstacle);
        game.getPortalPair().spawn(snek, obstacle, this);
    }
}