package com.comp2042.controller;

import com.comp2042.Main;
import com.comp2042.model.Coin;
import com.comp2042.model.CoinFactory;
import com.comp2042.model.Game;
import com.comp2042.model.HighScoreManager;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import com.comp2042.view.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Point;

public class GameController {
    private Snake snek;
    private Coin coin;
    private GameView gameView;
    private Game game;
    private Stage stage;
    private Obstacle obstacle;

    private static KeyCode key = KeyCode.LEFT;
    private static KeyCode lastKey = KeyCode.LEFT;
    private boolean keyPressed = false;

    public GameController(Snake snek, Coin coin, Game game, GameView gameView, Obstacle obstacle, Stage stage) {
        this.snek = snek;
        this.coin = coin;
        this.gameView = gameView;
        this.game = game;
        this.stage = stage;
        this.obstacle = obstacle;

        key = KeyCode.LEFT;
        lastKey = KeyCode.LEFT;

        animation.setCycleCount(Animation.INDEFINITE);
        animation.setRate(game.getSpeed());
    }

    private final Timeline animation = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        if (snek.getDead()) {
            return;
        }

        if (key.equals(KeyCode.LEFT)) {
            snek.move('L');
        } else if (key.equals(KeyCode.RIGHT)) {
            snek.move('R');
        } else if (key.equals(KeyCode.UP)) {
            snek.move('U');
        } else if (key.equals(KeyCode.DOWN)) {
            snek.move('D');
        }

        if (!snek.getDead()) {
            Point portalExit = game.getPortalPair().getExitIfEntered(snek.getHead());
            if (portalExit != null) {
                snek.teleportTo(portalExit);
            }
        }

        if (!snek.getDead() && snek.ateCoin(coin)) {
            powerCoin();
            game.incScore();
            HighScoreManager.getInstance().updateHighScore(game.getScorePoints());
            if (game.gameWon()) {
                this.animation.stop();
                gameView.drawGrid(coin, snek, obstacle, gameView.getGc());
                gameView.gameWon();
                gameView.updateScoreLabel();
                return;
            }
            newCoin();
        }

        keyPressed = false;

        if (!snek.getDead() || snek.hitSelf()) {
            gameView.drawGrid(coin, snek, obstacle, gameView.getGc());
        }

        gameView.gameWon();
        gameView.gameOver(snek);
        gameView.updateScoreLabel();
    }));

    public void handle(KeyEvent event) {
        if (event.getCode().isArrowKey() && !game.gameWon()) {
            animation.play();
        }

        if (valKey(event.getCode()) && !keyPressed) {
            lastKey = key;
            key = event.getCode();
            keyPressed = true;
            gameView.turnDir(snek);
        }

        if (event.getCode().equals(KeyCode.P)) {
            animation.stop();
        }

        if (event.getCode().equals(KeyCode.TAB)) {
            reset();
            gameView.updateScoreLabel();
        }

        if (event.getCode().equals(KeyCode.PLUS)) {
            gameView.scaleToFullscreen(true, stage, obstacle, snek, coin);
            gameView.getGameOverLabel().setStyle("-fx-font-size: 50;");
            gameView.getGameWonLabel().setStyle("-fx-font-size: 50;");
            gameView.getScoreLabel().setStyle("-fx-font-size: 35;");
        }

        if (event.getCode().equals(KeyCode.MINUS)) {
            gameView.scaleToFullscreen(false, stage, obstacle, snek, coin);
            gameView.getGameOverLabel().setStyle("-fx-font-size: 15;");
            gameView.getGameWonLabel().setStyle("-fx-font-size: 15;");
            gameView.getScoreLabel().setStyle("-fx-font-size: 15;");
        }

        if (event.getCode().equals(KeyCode.ESCAPE)) {
            animation.stop();
            Main.getViewManager().initalizeMainMenu();
            Main.getViewManager().getGameStage().close();
        }
    }

    private boolean valKey(KeyCode key) {
        if (!key.isArrowKey()) return false;
        if (key.equals(KeyCode.LEFT)) return !GameController.key.equals(KeyCode.RIGHT);
        if (key.equals(KeyCode.RIGHT)) return !GameController.key.equals(KeyCode.LEFT);
        if (key.equals(KeyCode.DOWN)) return !GameController.key.equals(KeyCode.UP);
        if (key.equals(KeyCode.UP)) return !GameController.key.equals(KeyCode.DOWN);
        return false;
    }

    private void reset() {
        animation.jumpTo(Duration.ZERO);
        animation.stop();

        snek = new Snake(game.getWidth(), game.getHeight(), game.isNoBorder(), obstacle);
        coin = new Coin(game.getWidth(), game.getHeight(), snek, obstacle);

        animation.setRate(game.getSpeed());
        key = KeyCode.LEFT;
        keyPressed = false;
        game.resetScore();
        obstacle.reset();
        game.getPortalPair().reset();
        gameView.clearTurns();
        gameView.gameWon();
        gameView.gameOver(snek);
        gameView.updateScoreLabel();

        gameView.drawGrid(coin, snek, obstacle, gameView.getGc());
    }

    private void powerCoin() {
        snek.setIgnoreBorders(false);
        coin.applyEffect(game, snek, obstacle);
        animation.setRate(game.getSpeed() + coin.getSpeedModifier());
    }

    private void newCoin() {
        coin = CoinFactory.createCoin(game, snek, obstacle);
    }

    public static KeyCode getKey() {
        return key;
    }

    public static KeyCode getLastKey() {
        return lastKey;
    }
}