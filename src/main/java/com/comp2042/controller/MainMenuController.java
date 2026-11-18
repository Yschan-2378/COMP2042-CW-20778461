package com.comp2042.controller;

import com.comp2042.Main;
import com.comp2042.model.Coin;
import com.comp2042.model.Game;
import com.comp2042.model.Obstacle;
import com.comp2042.model.Snake;
import com.comp2042.view.GameView;
import com.comp2042.view.ViewManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML private RadioButton diffEasy;
    @FXML private RadioButton diffNormal;
    @FXML private RadioButton diffHard;

    @FXML private RadioButton sizeSmall;
    @FXML private RadioButton sizeMedium;
    @FXML private RadioButton sizeLarge;

    @FXML private RadioButton powerOn;
    @FXML private RadioButton powerOff;

    private static int xValue = 20;
    private static int yValue = 20;

    private Scene gameScene;
    private ViewManager viewManager = Main.getViewManager();

    @FXML
    public void handleStart() {
        // 1. Determine Difficulty ('E', 'N', or 'H')
        char difficulty = 'N';
        if (diffEasy != null && diffEasy.isSelected()) difficulty = 'E';
        else if (diffHard != null && diffHard.isSelected()) difficulty = 'H';

        // 2. Determine Grid Size
        xValue = 20;
        yValue = 20;
        if (sizeSmall != null && sizeSmall.isSelected()) {
            xValue = 15;
            yValue = 15;
        } else if (sizeLarge != null && sizeLarge.isSelected()) {
            xValue = 30;
            yValue = 30;
        }

        // 3. Determine Power-Ups
        boolean powerUp = true;
        if (powerOff != null && powerOff.isSelected()) powerUp = false;

        viewManager.initGameStage();
        Stage gameStage = viewManager.getGameStage();
        launchSnake(gameStage, difficulty, powerUp);
        gameStage.setScene(gameScene);
        viewManager.getPrimaryStage().close();
        gameStage.show();
    }

    private void launchSnake(Stage gameStage, char difficulty, boolean powerUp) {
        Game game = new Game(xValue, yValue, powerUp, difficulty);
        Obstacle obstacle = new Obstacle(game.getWidth(), game.getHeight());
        Snake snek = new Snake(game.getWidth(), game.getHeight(), game.isNoBorder(), obstacle);
        Coin coin = new Coin(game.getWidth(), game.getHeight(), snek, obstacle);

        GameView gameView = new GameView(game);
        GameController controller = new GameController(snek, coin, game, gameView, obstacle, gameStage);

        StackPane gamePane = new StackPane();
        gameScene = new Scene(gamePane, game.getWidth() * game.getMultiplier(), game.getHeight() * game.getMultiplier());

        gameScene.setOnKeyPressed(controller::handle);

        gameView.makeScene(gameScene, snek, coin);
        gamePane.getChildren().addAll(
                gameView.getGridCanvas(),
                gameView.getGameOverLabel(),
                gameView.getGameWonLabel(),
                gameView.getScoreLabel()
        );
        gamePane.setPadding(new Insets(20));
        StackPane.setAlignment(gameView.getGameOverLabel(), Pos.TOP_CENTER);
        StackPane.setAlignment(gameView.getGameWonLabel(), Pos.TOP_CENTER);
        StackPane.setAlignment(gameView.getScoreLabel(), Pos.BOTTOM_CENTER);
    }

    public ViewManager getViewManager() {
        return viewManager;
    }

    public static int getxValue() { return xValue; }
    public static int getyValue() { return yValue; }
}