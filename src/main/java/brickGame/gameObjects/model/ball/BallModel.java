package brickGame.gameObjects.model.ball;

import javafx.scene.image.Image;

/**
 * Class that stores data for BallController
 */

public class BallModel {
    //Ball properties
    protected static int ballRadius = 10;
    //Represents the x and y of the center of the ball
    private double xBall;
    private double yBall;
    //Velocity components for the ball
    //Controls the speed and direction to the right
    private double vX;
    private double vY;
    private double right;
    private double left;
    private double up;
    private double down;
    private Image img;
    private String name;

    /**
     * Ball class constructor
     *
     * @param name The name of the type of ball
     * @param xBall The x-coordinate of the ball
     * @param yBall The y-coordinate of the ball
     * @param img The image of the ball according to type
     */
    public BallModel(String name, double xBall, double yBall, Image img) {
        this.xBall = xBall;
        this.yBall = yBall;

        right = xBall + ballRadius;
        left = xBall - ballRadius;
        up = yBall - ballRadius;
        down = yBall + ballRadius;

        this.img = img;
        this.name = name;
    }

    //setter and getter methods

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvX() {return vX;}

    public void setvY(double vY) {
        this.vY = vY;
    }

    public double getvY(){return vY;}


    public void setX(double x) {xBall = x;}
    public double getX() {return xBall;}

    public void setY(double y) {yBall = y;}
    public double getY() {return yBall;}

    public void setRight(double r) {this.right = r;}
    public double getRight() {return right;}
    public void setLeft(double l) {this.left = l;}
    public double getLeft() {return left;}
    public void setUp(double u) {this.up = u;}
    public double getUp() {return up;}
    public void setDown(double d) {this.down = d;}
    public double getDown() {return down;}
    public static int getBallRadius() {return ballRadius;}
    public Image getImage() {return img;}
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {return name;}
}
