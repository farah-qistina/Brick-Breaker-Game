package brickGame.gameObjects.controller;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import brickGame.gameObjects.model.ball.BallModel;
import brickGame.gameObjects.view.BallView;

/**
 * Abstract class for other classes to inherit
 */
public class BallController {
    private static Circle ballFace = new Circle();
    private static BallModel ballModel = null;
    private final BallView ballView;

    /**
     * Ball class constructor
     * @param ballType type of ball
     * @param xBall The x-coordinate of the ball
     * @param yBall The y-coordinate of the ball
     * @param img The image of the ball according to type
     */

    public BallController(String ballType, double xBall,  double yBall, Image img) {
        ballModel = new BallModel(ballType, xBall, yBall, img);
        ballFace = makeBallFace();
        ballView = new BallView();
    }

    /**
     * Makes a circular ball face
     * @return circle ball face
     */
    private Circle makeBallFace() {
        Circle out = new Circle();
        out.setRadius(BallModel.getBallRadius());
        out.setCenterX(getX());
        out.setCenterY(getY());
        return out;
    }

    /**
     * Horizontally moves the ball's face
     * @param goRightBall whether the ball moves right or left
     */
    public static void moveX(boolean goRightBall) {
        //Update ball position based on velocity
        if (goRightBall) {
            //going right
            ballFace.setCenterX(ballFace.getCenterX() + getvX());
        } else {
            //going left
            ballFace.setCenterX(ballFace.getCenterX() - getvX());
        }
        setPoints();
    }

    /**
     * Vertically moves the ball's face
     * @param goDownBall whether the ball moves down or up
     */
    public static void moveY(boolean goDownBall) {
        //Update ball position based on velocity
        if (goDownBall) {
            //going down
            ballFace.setCenterY(ballFace.getCenterY() + getvY());
        } else {
            //going up
            ballFace.setCenterY(ballFace.getCenterY() - getvY());
        }
        setPoints();
    }

    /**
     * moves the face of the ball to a point (x, y)
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void moveTo(double x, double y) {
        ballFace.setCenterX(x);
        ballFace.setCenterY(y);
        setPoints();
    }

    /**
     * sets the top, right, bottom, and left points of the ball
     */
    public static void setPoints() {
        int ballRadius = BallModel.getBallRadius();
        double x = ballFace.getCenterX();
        double y = ballFace.getCenterY();
        ballModel.setRight(x + ballRadius);
        ballModel.setLeft(x - ballRadius);
        ballModel.setUp(y - ballRadius);
        ballModel.setDown(y + ballRadius);
    }

    /**
     * updates the view of the ball
     * @param ball the ball
     */
    public void updateView(BallController ball) {
        ballView.drawBall(ball);
    }

    /**
     * set the velocity of the ball
     * @param vX horizonal speed
     * @param vY vertical speed
     */
    public void setVelocity(double vX, double vY) {
        setvX(vX);
        setvY(vY);
    }

    //setters and getter methods

    public Circle getBallFace() {return ballFace;}
    public Image getBallImage() {return ballModel.getImage();}
    public double getX() {return ballModel.getX();}
    public double getY() {return ballModel.getY();}
    public void setvX(double vX) {
        ballModel.setvX(vX);
    }
    public static double getvX() {return ballModel.getvX();}
    public void setvY(double vY) {
        ballModel.setvY(vY);
    }
    public static double getvY() {return ballModel.getvY();}
    public double getRight() {return ballModel.getRight();}
    public double getLeft() {return ballModel.getLeft();}
    public double getUp() {return ballModel.getUp();}
    public double getDown() {return ballModel.getDown();}
    public void setName(String name) {
        ballModel.setName(name);
    }
    public String getName() {return ballModel.getName();}
}