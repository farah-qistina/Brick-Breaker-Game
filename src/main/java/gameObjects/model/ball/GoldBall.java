package gameObjects.model.ball;

import gameObjects.controller.BallController;
import javafx.scene.image.Image;

/**
 * Class inherits from the Ball class
 */

public class GoldBall extends BallController{
    private static final Image img = new Image("goldball.png");
    private static final String ballType = "GOLD";

    /**
     * Constructor to create a normal ball
     *
     * @param ballType The type of ball
     * @param xBall The x-coordinate of the ball
     * @param yBall The y-coordinate of the ball
     */
    public GoldBall(String name, double xBall, double yBall) {
        super("GOLD", xBall, yBall, img);
    }
}
