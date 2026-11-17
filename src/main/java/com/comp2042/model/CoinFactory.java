package com.comp2042.model;

import java.util.Random;

public class CoinFactory {

    private static final Random RANDOM = new Random();

    private CoinFactory() {
    }

    public static Coin createCoin(Game game, Snake snek, Obstacle obstacle) {
        if (!game.isPowerUp()) {
            return new Coin(game.getWidth(), game.getHeight(), snek, obstacle);
        }

        int roll = RANDOM.nextInt(12);
        return createCoinByRoll(roll, game.getWidth(), game.getHeight(), snek, obstacle);
    }

    public static Coin createCoinByRoll(int roll, int width, int height, Snake snek, Obstacle obstacle) {
        switch (roll) {
            case 1:
                return new GoldCoin(width, height, snek, obstacle);
            case 2:
                return new RainbowCoin(width, height, snek, obstacle);
            case 3:
                return new BlueCoin(width, height, snek, obstacle);
            case 4:
                if (obstacle.getObstacles().isEmpty()) {
                    return new SilverCoin(width, height, snek, obstacle);
                }
                return new Coin(width, height, snek, obstacle);
            default:
                return new Coin(width, height, snek, obstacle);
        }
    }
}