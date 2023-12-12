package brickGame.gameObjects.model.paddle;

import brickGame.gameWindow.GameBoard;
import javafx.scene.image.Image;

/**
 * Class that stores data for BonusController
 */
public class PaddleModel {

    //Paddle dimensions
    private static final int paddleWidth     = 130;
    private static final int paddleHeight    = 30;
    private static final int halfPaddleWidth = paddleWidth / 2;


    //Paddle position
    //Left side of the paddle
    private double xPaddle;
    private double yPaddle;
    private double centerPaddleX;

    private final int max;
    private static final Image img = new Image("paddle.png");

    /**
     * default constructor to initialize the default value for the board
     * @param xPaddle top left x coordinate of the paddle
     * @param yPaddle top left y coordinate of the paddle
     */
    public PaddleModel(double xPaddle, double yPaddle) {
        max = GameBoard.getSceneWidth() - paddleWidth;
        this.xPaddle = xPaddle;
        this.yPaddle = yPaddle;
    }

    //setter and getter methods

    public static int getPaddleWidth() {return paddleWidth;}
    public static int getPaddleHeight() {return paddleHeight;}
    public static double getHalfPaddleWidth() {return halfPaddleWidth;}

    public void setxPaddle(double xPaddle) {
        this.xPaddle = xPaddle;
    }
    public double getxPaddle() {
        return xPaddle;
    }

    public void setyPaddle(double yPaddle) {
        this.yPaddle = yPaddle;
    }
    public double getyPaddle() {
        return yPaddle;
    }

    public void setCenterPaddleX(double centerPaddleX) {
        this.centerPaddleX = centerPaddleX;
    }
    public double getCenterPaddleX() {
        return centerPaddleX;
    }

    public int getMax() {
        return max;
    }

    public static Image getImage() {return img;}

}
