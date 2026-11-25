package com.comp2042.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

class PortalTest {

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
    void testFactoryCreatesPortalCoinOnRollFive() {
        Coin portalCoin = CoinFactory.createCoinByRoll(5, 20, 20, snake, obstacle);
        assertInstanceOf(PortalCoin.class, portalCoin);
        assertEquals(10, portalCoin.getPoints());
    }

    @Test
    void testPortalCoinActivatesLinkedPortalsFarApartOnFreeTiles() {
        PortalCoin portalCoin = new PortalCoin(20, 20, snake, obstacle);
        portalCoin.applyEffect(game, snake, obstacle);

        PortalPair pair = game.getPortalPair();
        assertTrue(pair.isActive());
        assertNotNull(pair.getPortalA());
        assertNotNull(pair.getPortalB());
        assertNotEquals(snake.getHead(), pair.getPortalA());
        assertNotEquals(snake.getHead(), pair.getPortalB());
        assertTrue(pair.manhattanDistance(pair.getPortalA(), pair.getPortalB()) >= 20);
    }

    @Test
    void testBidirectionalPortalTeleportationAndSplitBody() {
        PortalPair pair = game.getPortalPair();
        pair.setPortals(new Point(9, 10), new Point(15, 15));

        snake.move('L'); // head moves from (10,10) to (9,10) -> enters Portal A
        Point exit = pair.getExitIfEntered(snake.getHead());
        assertEquals(new Point(15, 15), exit);

        snake.teleportTo(exit);
        assertEquals(new Point(15, 15), snake.getHead());
        // Tail remains at (10,10) on this tick, splitting the snake across portals
        assertTrue(snake.getBody().contains(new Point(10, 10)));

        // Entering Portal B teleports back to Portal A
        assertEquals(new Point(9, 10), pair.getExitIfEntered(new Point(15, 15)));
    }

    @Test
    void testPortalResetClearsActivePortals() {
        PortalPair pair = game.getPortalPair();
        pair.setPortals(new Point(2, 2), new Point(18, 18));
        assertTrue(pair.isActive());

        pair.reset();
        assertFalse(pair.isActive());
        assertNull(pair.getExitIfEntered(new Point(2, 2)));
    }
}