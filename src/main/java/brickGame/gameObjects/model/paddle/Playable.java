package brickGame.gameObjects.model.paddle;

/**
 * an interface that allows other classes to implement its methods
 */
public interface Playable {
    /**
     * method to move the paddle according to the direction
     * @param direction left or right
     */
    public void move(final int direction);

    /**
     * method to move the paddle to a specific spot
     * @param xPaddle x coordinate of the new spot
     */
    public void moveTo(double xPaddle);
}
