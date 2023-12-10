package gameObjects.view;

import gameObjects.controller.BallController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BallView {
    /**
     * Default constructor
      */
    public BallView() {}
    public void drawBall(BallController ball) {
        Image img = ball.getBallImage();
        ball.getBallFace().setFill(new ImagePattern(img));
    }
}
