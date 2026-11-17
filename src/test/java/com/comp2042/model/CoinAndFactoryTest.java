package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

class CoinAndFactoryTest {

    private Game game;
    private Obstacle obstacle;
    private Snake snake;

    @BeforeEach
    void setUp() {
        game = new Game(20, 20, true, 'N');
        obstacle = new Obstacle(20, 20);
        snake = new Snake(20, 20, false, obstacle);
    }

    @Test
    void testCoinFactoryDeterministicCreationByRoll() {
        Coin gold = CoinFactory.createCoinByRoll(1, 20, 20, snake, obstacle);
        assertInstanceOf(GoldCoin.class, gold);
        assertEquals(15, gold.getPoints());

        Coin rainbow = CoinFactory.createCoinByRoll(2, 20, 20, snake, obstacle);
        assertInstanceOf(RainbowCoin.class, rainbow);
        assertEquals(5.0, rainbow.getSpeedModifier());

        Coin blue = CoinFactory.createCoinByRoll(3, 20, 20, snake, obstacle);
        assertInstanceOf(BlueCoin.class, blue);
        assertEquals(5, blue.getPoints());

        Coin silver = CoinFactory.createCoinByRoll(4, 20, 20, snake, obstacle);
        assertInstanceOf(SilverCoin.class, silver);

        Coin standard = CoinFactory.createCoinByRoll(0, 20, 20, snake, obstacle);
        assertEquals(Coin.class, standard.getClass());
        assertEquals(5, standard.getPoints());
        assertEquals(0.0, standard.getSpeedModifier());
    }

    @Test
    void testSilverCoinRollFallsBackWhenObstaclesAlreadyExist() {
        Coin seedCoin = new Coin(20, 20, snake, obstacle);
        obstacle.newObstacles(snake, seedCoin, 3);
        assertFalse(obstacle.getObstacles().isEmpty());

        Coin fallbackCoin = CoinFactory.createCoinByRoll(4, 20, 20, snake, obstacle);
        assertEquals(Coin.class, fallbackCoin.getClass());
    }

    @Test
    void testPolymorphicCoinEffects() {
        GoldCoin goldCoin = new GoldCoin(20, 20, snake, obstacle);
        goldCoin.applyEffect(game, snake, obstacle);
        assertEquals(15, game.getScorePoints());

        BlueCoin blueCoin = new BlueCoin(20, 20, snake, obstacle);
        blueCoin.applyEffect(game, snake, obstacle);
        assertEquals(20, game.getScorePoints());

        SilverCoin silverCoin = new SilverCoin(20, 20, snake, obstacle);
        silverCoin.applyEffect(game, snake, obstacle);
        assertEquals(25, game.getScorePoints());
        assertTrue(obstacle.getObstacles().size() >= 3);
    }

    @Test
    void testCoinPositionWithinBoundsAndDefensiveCopy() {
        Coin coin = new Coin(20, 20, snake, obstacle);
        Point pos = coin.getPos();
        assertTrue(pos.x >= 0 && pos.x < 20);
        assertTrue(pos.y >= 0 && pos.y < 20);
        assertNotEquals(snake.getHead(), pos);
        assertFalse(snake.getBody().contains(pos));

        pos.translate(100, 100);
        assertNotEquals(pos, coin.getPos());
    }
}