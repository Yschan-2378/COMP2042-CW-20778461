package com.comp2042.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void testDifficultyConfigurations() {
        Game easyGame = new Game(20, 20, true, 'E');
        assertEquals(8.5, easyGame.getSpeed());
        assertTrue(easyGame.isNoBorder());
        assertTrue(easyGame.isPowerUp());

        Game normalGame = new Game(20, 20, false, 'N');
        assertEquals(8.5, normalGame.getSpeed());
        assertFalse(normalGame.isNoBorder());
        assertFalse(normalGame.isPowerUp());

        Game hardGame = new Game(20, 20, true, 'H');
        assertEquals(13.0, hardGame.getSpeed());
        assertFalse(hardGame.isNoBorder());
    }

    @Test
    void testGridDimensionsAndMultiplier() {
        Game standardGame = new Game(20, 20, true, 'N');
        assertEquals(20, standardGame.getWidth());
        assertEquals(20, standardGame.getHeight());
        assertEquals(20.0, standardGame.getMultiplier());

        Game largeGame = new Game(60, 60, true, 'N');
        assertEquals(10.0, largeGame.getMultiplier());
    }

    @Test
    void testScoreTrackingAndWinCondition() {
        Game game = new Game(4, 4, true, 'N'); // maxScore = (4 * 4) / 2 = 8
        assertFalse(game.gameWon());

        for (int i = 0; i < 8; i++) {
            game.incScore();
        }
        assertTrue(game.gameWon());

        game.addPoints(15);
        assertEquals(15, game.getScorePoints());

        game.resetScore();
        assertEquals(0, game.getScorePoints());
        assertFalse(game.gameWon());
    }

    @Test
    void testDecMaxScoreAdjustsWinThreshold() {
        Game game = new Game(4, 4, true, 'N'); // initial maxScore = 8
        game.decMaxScore(4); // subtracts (4 - 1) = 3 -> new maxScore = 5
        for (int i = 0; i < 5; i++) {
            game.incScore();
        }
        assertTrue(game.gameWon());
    }
}