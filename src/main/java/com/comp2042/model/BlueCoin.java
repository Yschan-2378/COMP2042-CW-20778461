package com.comp2042.model;

import javafx.scene.image.Image;

public class BlueCoin extends Coin {

    // TODO: This JavaFX View component should eventually be moved to GameView to perfectly satisfy MVC
    private Image image = new Image("resources/views/res/bluecoin.png");

    public BlueCoin(int x, int y, Snake snek, Obstacle obstacle) {
        super(x, y, snek, obstacle);
    }

    public int getPoints() {
        return 5;
    }

    public Image getImage() {
        return image;
    }
}