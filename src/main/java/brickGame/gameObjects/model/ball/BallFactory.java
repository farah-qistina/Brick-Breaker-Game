package brickGame.gameObjects.model.ball;
import brickGame.gameObjects.controller.BallController;

public class BallFactory {
    /**
     * Default constructor
     */
    public BallFactory() {}

    /**
     * Factory method to create ball objects
     *
     * @param ballType The type of ball
     * @param xBall The x-coordinate of the ball
     * @param yBall The y-coordinate of the ball
     * @return A ball
     */
    public BallController makeBall(String ballType, double xBall, double yBall) {
        BallController out;
        switch (ballType) {
            case "GOLD":
                out = new GoldBall("GOLD", xBall, yBall);
                break;
            case "NORMAL":
                out = new NormalBall("NORMAL", xBall, yBall);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown Type:%s\n", ballType));
        }
        return out;
    }
}
