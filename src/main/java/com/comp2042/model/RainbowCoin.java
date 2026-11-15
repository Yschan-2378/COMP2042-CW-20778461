package com.comp2042.model;

public class RainbowCoin extends Coin {

    private static final double SPEED_BONUS = 5.0;

    public RainbowCoin(int x, int y, Snake snek, Obstacle obstacle) {
        super(x, y, snek, obstacle, 5);
    }

    @Override
    public double getSpeedModifier() {
        return SPEED_BONUS;
    }

    public double getSpeedBonus() {
        return SPEED_BONUS;
    }
}