package com.comp2042.model;

import javafx.scene.image.Image;

public class GoldCoin extends Coin {

    // TODO: This JavaFX View component should eventually be moved to GameView to perfectly satisfy MVC
    private Image image = new Image("resources/views/res/goldcoin.png");

    public GoldCoin(int x, int y, Snake snek, Obstacle obstacle) {
        super(x, y, snek, obstacle);
    }

    public Image getImage() {
        return image;
    }

    public int getPoints() {
        return 15;
    }
}