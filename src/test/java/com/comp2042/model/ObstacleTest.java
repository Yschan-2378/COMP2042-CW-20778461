package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleTest {

    private Obstacle obstacle;
    private Snake snake;
    private Coin coin;

    @BeforeEach
    void setUp() {
        obstacle = new Obstacle(20, 20);
        snake = new Snake(20, 20, false, obstacle);
        coin = new Coin(20, 20, snake, obstacle);
    }

    @Test
    void testObstacleGenerationAvoidsSnakeAndCoin() {
        obstacle.newObstacles(snake, coin, 10);
        ArrayList<Point> generated = obstacle.getObstacles();
        assertEquals(10, generated.size());

        for (Point p : generated) {
            assertNotEquals(snake.getHead(), p);
            assertFalse(snake.getBody().contains(p));
            assertNotEquals(coin.getPos(), p);
        }
    }

    @Test
    void testDefensiveCopyAndReset() {
        obstacle.newObstacles(snake, coin, 5);
        ArrayList<Point> externalCopy = obstacle.getObstacles();
        externalCopy.clear();
        assertEquals(5, obstacle.getObstacles().size());

        obstacle.reset();
        assertTrue(obstacle.getObstacles().isEmpty());
    }
}