package brickGame.gameObjects.model.board;

import brickGame.gameObjects.controller.BallController;
import brickGame.gameObjects.controller.BlockController;
import brickGame.gameObjects.controller.PaddleController;
import brickGame.gameObjects.model.ball.BallFactory;

import java.util.ArrayList;

/**
 * Class that stores data for BoardController
 */
public class BoardModel {
    private ArrayList<BlockController> blocks;
    private int destroyedBlockCount;
    private BallController ball;
    private PaddleController paddle;

    private int score;
    private int  heart;

    private BallFactory ballFactory;

    /**
     * default constructor to initialize the default value for the board
     */
    public BoardModel() {
        heart = 3;
        score = 0;
    }

    //setter and getter methods

    /**
     * Setter to set the ball object
     *
     * @param ball The ball object
     */

    public void setBall(BallController ball) {this.ball = ball;}

    /**
     * Getter to get the ball object
     *
     * @return The ball object
     */

    public BallController getBall() {
        return ball;
    }

    public PaddleController getPaddle() {
        return paddle;
    }

    /**
     * Setter to set the player
     */

    public void setPaddle(double xPaddle, double yPaddle) {
        PaddleController.getUniquePlayer(xPaddle, yPaddle);
    }

    /**
     * Getter to get the array of block objects
     *
     * @return An array of block objects
     */

    public ArrayList<BlockController> getBlocks() {
        return blocks;
    }
    public void setBlocks(ArrayList<BlockController> blocks) {
        this.blocks = blocks;
    }
    /**
     * Setter to set the score
     *
     * @param score The score of the player
     */

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter to get the score of the player
     *
     * @return Score integer
     */

    public int getScore() {
        return score;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getHeart() {
        return heart;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }
}
