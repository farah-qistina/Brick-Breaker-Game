package brickGame.gameObjects.view;

import brickGame.gameObjects.controller.BallController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BallView {
    /**
     * Default constructor
      */
    public BallView() {}

    /**
     * method to draw the ball
     * @param ball ball object
     */
    public void drawBall(BallController ball) {
        Image img = ball.getBallImage();
        ball.getBallFace().setFill(new ImagePattern(img));
    }
}
