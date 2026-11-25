package com.comp2042.model;

import java.util.Random;

public class SilverCoin extends Coin {

    public SilverCoin(int x, int y, Snake snek, Obstacle obstacle) {
        super(x, y, snek, obstacle, 10);
    }

    @Override
    public void applyEffect(Game game, Snake snek, Obstacle obstacle) {
        super.applyEffect(game, snek, obstacle);
        Random rnd = new Random();
        int obstacleCount = rnd.nextInt(3) + 3; // Spawns 3 to 5 blocks instead of up to 22
        obstacle.newObstacles(snek, this, obstacleCount);
        game.decMaxScore(obstacle.getObstacles().size());
    }
}