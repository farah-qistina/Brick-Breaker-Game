package brickGame.gameObjects.model.bonus;

import brickGame.gameObjects.model.block.BlockModel;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Class that stores data for BonusController
 */
public class BonusModel {
    //TODO make private
    public Rectangle bonus;
    private static final int bonusWidth     = 30;
    private static final int bonusHeight    = 30;
    private double xBonus;
    private double yBonus;
    //time created in milliseconds
    public long timeCreated;
    public boolean taken = false;
    private static Image img;

    /**
     * default constructor to initialize the default value for the bonus object
     */
    public BonusModel(double xBlock, double yBlock) {
        xBonus = (xBlock + (double) BlockModel.getBlockWidth() /2) - 15; //half of bonus
        yBonus = (yBlock + (double) BlockModel.getBlockHeight() /2) - 15; //half of bonus

        if (new Random().nextInt(20) % 2 == 0) {
            img = new Image("bonus1.png");
        } else {
            img = new Image("bonus2.png");
        }
    }

    //setter and getter methods

    public static int getBonusWidth() {return bonusWidth;}
    public static int getBonusHeight() {return bonusHeight;}

    public void setxBonus(double xBonus) {
        this.xBonus = xBonus;
    }

    public double getxBonus() {
        return xBonus;
    }

    public void setyBonus(double yBonus) {
        this.yBonus = yBonus;
    }

    public double getyBonus() {
        return yBonus;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean getTaken() {
        return taken;
    }

    public static Image getImage() {return img;}
}
