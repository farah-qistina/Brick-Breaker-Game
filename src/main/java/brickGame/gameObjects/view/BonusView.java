package brickGame.gameObjects.view;

import brickGame.gameObjects.controller.BonusController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class BonusView {
    /**
     * default constructor
     */
    public BonusView() {}

    /**
     * method to draw the bonus object
     * @param bonus bonus object
     */
    public void drawBonus(BonusController bonus) {
        Image img = bonus.getImage();
        bonus.getBonusFace().setFill(new ImagePattern(img));
    }
}
