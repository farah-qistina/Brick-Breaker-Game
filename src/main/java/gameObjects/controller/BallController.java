package gameObjects.controller;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;

import gameObjects.model.ball.BallModel;
import gameObjects.view.BallView;

/**
 * Abstract BallController class for other classes to inherit
 */
public class BallController {
    private final Circle ballFace = new Circle();
    private final BallModel ballModel;
    private final BallView ballView;

    /**
     * Ball class constructor
     *
     * @param xBall The x-coordinate of the ball
     * @param yBall The y-coordinate of the ball
     * @param img The image of the ball according to type
     */
    public BallController(String ballType, double xBall,  double yBall, Image img) {
        ballModel = new BallModel(ballType, xBall, yBall, img);
        ballFace.setRadius(BallModel.getBallRadius());
        ballView = new BallView();
    }

    public void moveX(boolean goRightBall) {
        //Update ball position based on velocity
        if (goRightBall) {
            //going right
            ballModel.setX(ballModel.getX()+ ballModel.getvX());
        } else {
            //going left
            ballModel.setX(ballModel.getX()- ballModel.getvX());
        }
    }

    public void moveY(boolean goDownBall) {
        //Update ball position based on velocity
        if (goDownBall) {
            //going down
            ballModel.setY(ballModel.getY()+ ballModel.getvY());
        } else {
            //going up
            ballModel.setY(ballModel.getY()- ballModel.getvY());
        }
    }

    public void moveTo(double x, double y) {
        ballModel.setX(x);
        ballModel.setY(y);
    }

    public void setPoints() {
        int ballRadius = BallModel.getBallRadius();
        double x = ballModel.getX();
        double y = ballModel.getY();
        ballModel.setRight(x + ballRadius);
        ballModel.setLeft(x - ballRadius);
        ballModel.setUp(y - ballRadius);
        ballModel.setDown(y + ballRadius);
    }

    public void updateView(BallController ball) {
        ballView.drawBall(ball);
    }

    public void setVelocity(double vX, double vY) {
        ballModel.setVelocity(vX, vY);
    }



    public Circle getBallFace() {return ballFace;}
    public Image getBallImage() {return ballModel.getImage();}
    public double getX() {return ballModel.getX();}
    public double getY() {return ballModel.getY();}
    public double getvX() {return ballModel.getvX();}
    public double getvY() {return ballModel.getvY();}
    public double getRight() {return ballModel.getRight();}
    public double getLeft() {return ballModel.getLeft();}
    public double getUp() {return ballModel.getUp();}
    public double getDown() {return ballModel.getDown();}
    public void setName(String name) {
        ballModel.setName(name);
    }
    public String getName() {return ballModel.getName();}
}