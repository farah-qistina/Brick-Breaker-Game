package brickGame.gameObjects.controller;

import brickGame.gameObjects.model.bonus.BonusModel;
import brickGame.gameObjects.model.paddle.PaddleModel;
import brickGame.gameObjects.view.BonusView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class BonusController {
    private Rectangle bonusFace;
    private BonusModel bonusModel;
    private BonusView bonusView;


    public BonusController(double xBlock, double yBlock) {
        bonusModel = new BonusModel(xBlock, yBlock);
        bonusFace = makeBonusFace();
        bonusView = new BonusView();
    }

    private Rectangle makeBonusFace() {
        Rectangle out = new Rectangle();
        out.setWidth(BonusModel.getBonusWidth());
        out.setHeight(BonusModel.getBonusHeight());
        out.setX(getxBonus());
        out.setY(getyBonus());
        return out;
    }

    public void moveY(double y) {
        bonusFace.setY(y);
    }

    public void updateView(BonusController bonus) {
        bonusView.drawBonus(bonus);
    }

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
