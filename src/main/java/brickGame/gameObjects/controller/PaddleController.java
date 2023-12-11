package brickGame.gameObjects.controller;

import brickGame.gameObjects.model.block.ImpactDirection;
import brickGame.gameObjects.model.paddle.PaddleModel;
import brickGame.gameObjects.model.paddle.Playable;
import brickGame.gameObjects.view.PaddleView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class PaddleController implements Playable {

    private Rectangle paddleFace;
    private PaddleModel paddleModel;
    private PaddleView paddleView;
    private static PaddleController paddle;

    //Direction constants
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    private PaddleController() {

        // Prints error message
        System.out.println("Singleton Violated Error: Second Player object should not be created!!");
    }

    private PaddleController(double xPaddle, double yPaddle) {
        paddleModel = new PaddleModel(xPaddle, yPaddle);
        paddleFace = makePaddleFace();
        paddleView = new PaddleView();
    }

    private Rectangle makePaddleFace() {
        Rectangle out = new Rectangle();
        out.setWidth(PaddleModel.getPaddleWidth());
        out.setHeight(PaddleModel.getPaddleHeight());
        out.setX(paddleModel.getxPaddle());
        out.setY(paddleModel.getyPaddle());
        return out;
    }

    public static PaddleController getUniquePlayer() {
        if (paddle == null) {
            paddle = new PaddleController();
        }
        return paddle;
    }

    public static PaddleController getUniquePlayer(double xPaddle, double yPaddle) {
        if (paddle == null) {
            paddle = new PaddleController(xPaddle, yPaddle);
        }
        return paddle;
    }

    public final ImpactDirection findImpact (BallController ballController) {
        if (ballController.getDown() >= paddleModel.getyPaddle() && ballController.getX() >= paddleModel.getxPaddle() && ballController.getX() <= paddleModel.getxPaddle() + PaddleModel.getPaddleWidth()) {
            return ImpactDirection.UP_IMPACT;
        }
        if (ballController.getLeft() >= paddleModel.getxPaddle() && ballController.getY() >= paddleModel.getyPaddle() && ballController.getY() <= paddleModel.getyPaddle() + PaddleModel.getPaddleHeight()) {
            return ImpactDirection.RIGHT_IMPACT;
        }
        if (ballController.getRight() <= paddleModel.getxPaddle() && ballController.getY() >= paddleModel.getyPaddle() && ballController.getY() <= paddleModel.getyPaddle() + PaddleModel.getPaddleHeight()) {
            return ImpactDirection.LEFT_IMPACT;
        }
        return ImpactDirection.NO_IMPACT;
    }


    //Move the paddle based on the direction parameter
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
                    if (paddleModel.getxPaddle() == paddleModel.getMax() && direction == RIGHT) {
                        return;
                    }
                    //"" left edge
                    if (paddleModel.getxPaddle() == 0 && direction == LEFT) {
                        return;
                    }
                    //Updates the X-coordinate of the paddle based on the direction
                    if (direction == RIGHT) {
                        paddleModel.setxPaddle(paddleModel.getxPaddle() + 1);                        ;
                    } else {
                        paddleModel.setxPaddle(paddleModel.getxPaddle() - 1);
                    }
                    //Updates the center of paddle based on its new position
                    paddleModel.setCenterPaddleX(paddleModel.getxPaddle() + PaddleModel.getHalfPaddleWidth());
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

    @Override
    public void moveTo(double xPaddle) {
        if (xPaddle >= paddleModel.getMax()) {
            return;
        }
        paddleModel.setxPaddle(xPaddle);
        paddleModel.setCenterPaddleX(paddleModel.getxPaddle() + PaddleModel.getHalfPaddleWidth());
    }

    public void updateView(PaddleController p) {paddleView.drawPaddle(p);}

    public Rectangle getPaddleFace() {
        return paddleFace;
    }

    public Image getImage() {return PaddleModel.getImage();}

    public double getCenterPaddleX() {
        return paddleModel.getCenterPaddleX();
    }
}
