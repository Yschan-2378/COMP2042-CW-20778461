package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    private Obstacle obstacle;
    private Snake snake;

    @BeforeEach
    void setUp() {
        obstacle = new Obstacle(20, 20);
        snake = new Snake(20, 20, false, obstacle);
    }

    @Test
    void testInitialStateAndDefensiveCopies() {
        assertEquals(new Point(10, 10), snake.getHead());
        assertEquals(1, snake.getBody().size());
        assertEquals(new Point(11, 10), snake.getBody().get(0));
        assertFalse(snake.getDead());
        assertEquals(270, snake.getSnakeDir());

        // Verify defensive copying on getHead() and getBody()
        Point mutatedHead = snake.getHead();
        mutatedHead.translate(99, 99);
        assertEquals(new Point(10, 10), snake.getHead());

        ArrayList<Point> mutatedBody = snake.getBody();
        mutatedBody.clear();
        assertEquals(1, snake.getBody().size());
    }

    @Test
    void testDirectionalMovementAndHeadRotation() {
        snake.move('L');
        assertEquals(new Point(9, 10), snake.getHead());
        assertEquals(270, snake.getSnakeDir());

        snake.move('U');
        assertEquals(new Point(9, 9), snake.getHead());
        assertEquals(0, snake.getSnakeDir());

        snake.move('R');
        assertEquals(new Point(10, 9), snake.getHead());
        assertEquals(90, snake.getSnakeDir());

        snake.move('D');
        assertEquals(new Point(10, 10), snake.getHead());
        assertEquals(180, snake.getSnakeDir());
    }

    @Test
    void testWallCollisionKillsSnakeWhenBordersActive() {
        Snake smallBoardSnake = new Snake(4, 4, false, obstacle); // starts at (2, 2)
        smallBoardSnake.move('L'); // (1, 2)
        smallBoardSnake.move('L'); // (0, 2)
        assertFalse(smallBoardSnake.getDead());

        smallBoardSnake.move('L'); // (-1, 2) -> hits left border
        assertTrue(smallBoardSnake.getDead());
        assertEquals(new Point(0, 2), smallBoardSnake.getHead());
    }

    @Test
    void testBorderWrapAroundWithIgnoreBordersPowerUp() {
        Snake smallBoardSnake = new Snake(4, 4, false, obstacle); // starts at (2, 2)
        smallBoardSnake.setIgnoreBorders(true);

        smallBoardSnake.move('L'); // (1, 2)
        smallBoardSnake.move('L'); // (0, 2)
        smallBoardSnake.move('L'); // wraps to (3, 2)
        assertFalse(smallBoardSnake.getDead());
        assertEquals(new Point(3, 2), smallBoardSnake.getHead());
    }
}