package com.comp2042.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HighScoreManager {

    private static HighScoreManager uniqueInstance;

    private Path storagePath = Path.of("snake_highscore.txt");
    private int highScore = 0;

    private HighScoreManager() {
        loadHighScore();
    }

    public static HighScoreManager getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new HighScoreManager();
        }
        return uniqueInstance;
    }

    public int getHighScore() {
        return highScore;
    }

    public boolean updateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
            return true;
        }
        return false;
    }

    public void loadHighScore() {
        if (!Files.exists(storagePath)) {
            highScore = 0;
            return;
        }
        try {
            String content = Files.readString(storagePath).trim();
            int parsed = Integer.parseInt(content);
            highScore = Math.max(0, parsed);
        } catch (IOException | NumberFormatException e) {
            highScore = 0;
        }
    }

    private void saveHighScore() {
        try {
            Files.writeString(storagePath, String.valueOf(highScore));
        } catch (IOException ignored) {
            // Keep running even if disk is read-only
        }
    }

    void useTestFile(Path testPath) {
        this.storagePath = testPath;
        this.highScore = 0;
        loadHighScore();
    }
}