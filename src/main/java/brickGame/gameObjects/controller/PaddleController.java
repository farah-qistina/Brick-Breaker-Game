package brickGame.gameObjects.controller;

import brickGame.gameObjects.model.block.ImpactDirection;
import brickGame.gameObjects.model.paddle.PaddleModel;
import brickGame.gameObjects.model.paddle.Playable;
import brickGame.gameObjects.view.PaddleView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * class for the paddle object
 */
public class PaddleController implements Playable {

    private Rectangle paddleFace;
    private PaddleModel paddleModel;
    private PaddleView paddleView;
    private static PaddleController paddle;

    //Direction constants
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    /**
     * default constructor
     */
    private PaddleController() {

        // Prints error message
        System.out.println("Singleton Violated Error: Second Player object should not be created!!");
    }

    /**
     * default constructor
     * @param xPaddle top left x coordinate of the paddle
     * @param yPaddle top left y coordinate of the paddle
     */
    private PaddleController(double xPaddle, double yPaddle) {
        paddleModel = new PaddleModel(xPaddle, yPaddle);
        paddleFace = makePaddleFace();
        paddleView = new PaddleView();
    }

    /**
     * makes a rectangle paddle face
     * @return the paddle face
     */
    private Rectangle makePaddleFace() {
        Rectangle out = new Rectangle();
        out.setWidth(PaddleModel.getPaddleWidth());
        out.setHeight(PaddleModel.getPaddleHeight());
        out.setX(getxPaddle());
        out.setY(getyPaddle());
        return out;
    }

    /**
     * Method to get the Player instance, creates a new instance if no instance is created
     * @return the paddle
     */
    public static PaddleController getUniquePlayer() {
        if (paddle == null) {
            paddle = new PaddleController();
        }
        return paddle;
    }

    /**
     * Method to get the Player instance, creates a new instance if no instance is created
     * @param xPaddle top left x coordinate of the paddle
     * @param yPaddle top left y coordinate of the paddle
     * @return the paddle
     */
    public static PaddleController getUniquePlayer(double xPaddle, double yPaddle) {
        if (paddle == null) {
            paddle = new PaddleController(xPaddle, yPaddle);
        }
        return paddle;
    }

    /**
     * find the impact direction between the ball and paddle
     * @param ballController the ball
     * @return impact direction
     */
    public final ImpactDirection findImpact (BallController ballController) {
        double xPaddle = paddleFace.getX();
        double yPaddle = paddleFace.getY();
        double xBall = ballController.getBallFace().getCenterX();
        double yBall = ballController.getBallFace().getCenterY();

        if (ballController.getDown() >= yPaddle && xBall >= xPaddle && xBall <= xPaddle + PaddleModel.getPaddleWidth()) {
            return ImpactDirection.UP_IMPACT;
        }
        if (ballController.getLeft() >= xPaddle && yBall >= yPaddle && yBall <= yPaddle + PaddleModel.getPaddleHeight()) {
            return ImpactDirection.RIGHT_IMPACT;
        }
        if (ballController.getRight() <= xPaddle && yBall >= yPaddle && yBall <= yPaddle + PaddleModel.getPaddleHeight()) {
            return ImpactDirection.LEFT_IMPACT;
        }
        return ImpactDirection.NO_IMPACT;
    }


    /**
     * horizontally moves the paddle according to the direction given
     * @param direction move left or right
     */
    @Override
    public void move(final int direction) {
        //Separate thread to avoid blocking the JavaFX application's main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Duration for which the thread sleeps between each movement step
                int initialSleepTime = 4;
                //Controls the number of movement steps the paddle takes, iterates 30 times
                for (int i = 0; i < 30; i++) {
                    //Checks if the paddle has reached the right edge of the game area and direction is right, method returns to prevent movement
                    if (paddleFace.getX() == paddleModel.getMax() && direction == RIGHT) {
                        return;
                    }
                    //"" left edge
                    if (paddleFace.getX() == 0 && direction == LEFT) {
                        return;
                    }
                    //Updates the X-coordinate of the paddle based on the direction
                    if (direction == RIGHT) {
                        paddleFace.setX(paddleFace.getX() + 1);                      ;
                    } else {
                        paddleFace.setX(paddleFace.getX() - 1);
                    }
                    //Introduces a sleep to slow down the movement making it visible and controlled
                    try {
                        Thread.sleep(initialSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //After 20 iterations, sleep time gradually increases and makes the paddle move faster
                    if (i >= 20) {
                        initialSleepTime = i;
                    }
                }
            }
        }).start(); //Starts the thread
    }

    /**
     * horizontally moves the paddle to a specified location
     * @param x new x coordinate
     */
    @Override
    public void moveTo(double x) {
        if (x >= paddleModel.getMax()) {
            return;
        }
        paddleFace.setX(x);
    }

    /**
     * updates the view of the paddle
     * @param p the paddle
     */
    public void updateView(PaddleController p) {paddleView.drawPaddle(p);}

    //setter and getter methods

    public Rectangle getPaddleFace() {
        return paddleFace;
    }

    public Image getImage() {return PaddleModel.getImage();}

    public void setxPaddle(double xPaddle) {
        paddleModel.setxPaddle(xPaddle);
    }
    public double getxPaddle() {
        return paddleModel.getxPaddle();
    }

    public void setyPaddle(double yPaddle) {
        paddleModel.setyPaddle(yPaddle);
    }
    public double getyPaddle() {
        return paddleModel.getyPaddle();
    }

    public double getCenterPaddleX() {
        return paddleFace.getX() + PaddleModel.getHalfPaddleWidth();
    }
}
