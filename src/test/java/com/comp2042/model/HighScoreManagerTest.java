package com.comp2042.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreManagerTest {

    @Test
    void testSingletonReturnsSameInstance() {
        HighScoreManager first = HighScoreManager.getInstance();
        HighScoreManager second = HighScoreManager.getInstance();
        assertSame(first, second);
    }

    @Test
    void testUpdateHighScoreBoundaries(@TempDir Path tempDir) {
        HighScoreManager manager = HighScoreManager.getInstance();
        manager.useTestFile(tempDir.resolve("test_highscore.txt"));

        assertEquals(0, manager.getHighScore());
        assertFalse(manager.updateHighScore(-5));
        assertFalse(manager.updateHighScore(0));
        assertTrue(manager.updateHighScore(50));
        assertEquals(50, manager.getHighScore());

        assertFalse(manager.updateHighScore(50));
        assertFalse(manager.updateHighScore(30));
        assertEquals(50, manager.getHighScore());
    }

    @Test
    void testHighScorePersistsAcrossReload(@TempDir Path tempDir) {
        Path scoreFile = tempDir.resolve("persist_highscore.txt");
        HighScoreManager manager = HighScoreManager.getInstance();
        manager.useTestFile(scoreFile);

        assertTrue(manager.updateHighScore(120));
        manager.loadHighScore();
        assertEquals(120, manager.getHighScore());
    }

    @Test
    void testCorruptedFileDefaultsToZero(@TempDir Path tempDir) throws Exception {
        Path badFile = tempDir.resolve("corrupt_highscore.txt");
        java.nio.file.Files.writeString(badFile, "not_a_number");

        HighScoreManager manager = HighScoreManager.getInstance();
        manager.useTestFile(badFile);
        assertEquals(0, manager.getHighScore());
    }
}