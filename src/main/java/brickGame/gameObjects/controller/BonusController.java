package brickGame.gameObjects.controller;

import brickGame.gameObjects.model.bonus.BonusModel;
import brickGame.gameObjects.model.paddle.PaddleModel;
import brickGame.gameObjects.view.BonusView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * class for the bonus object
 */
public class BonusController {
    private Rectangle bonusFace;
    private BonusModel bonusModel;
    private BonusView bonusView;


    /**
     * constructor for the bonus object
     * @param xBlock the top left x coordinate of the block that the bonus object is coming from
     * @param yBlock the top left y coordinate of the block that the bonus object is coming from
     */
    public BonusController(double xBlock, double yBlock) {
        bonusModel = new BonusModel(xBlock, yBlock);
        bonusFace = makeBonusFace();
        bonusView = new BonusView();
    }

    /**
     * makes a rectangle bonus face
     * @return the bonus face
     */
    private Rectangle makeBonusFace() {
        Rectangle out = new Rectangle();
        out.setWidth(BonusModel.getBonusWidth());
        out.setHeight(BonusModel.getBonusHeight());
        out.setX(getxBonus());
        out.setY(getyBonus());
        return out;
    }

    /**
     * moves the bonus object face vertically
     * @param y y coordinate of new position
     */
    public void moveY(double y) {
        bonusFace.setY(y);
    }

    /**
     * updates the view of the bonus object
     * @param bonus the bonus object
     */
    public void updateView(BonusController bonus) {
        bonusView.drawBonus(bonus);
    }


    //setter and getter methods

    public Image getImage() {return BonusModel.getImage();}
    public void setxBonus(double xBonus) {
        bonusModel.setxBonus(xBonus);
    }
    public double getxBonus() {return bonusModel.getxBonus();}
    public void setyBonus(double yBonus) {
        bonusModel.setyBonus(yBonus);
    }
    public double getyBonus() {return bonusModel.getyBonus();}

    public void setTaken(boolean taken) {
        bonusModel.setTaken(taken);
    }
    public boolean getTaken() {return bonusModel.getTaken();}

    public void setTimeCreated(long time) {
        bonusModel.setTimeCreated(time);
    }
    public long getTimeCreated() {return bonusModel.getTimeCreated();}

    public Rectangle getBonusFace() {
        return bonusFace;
    }
}
