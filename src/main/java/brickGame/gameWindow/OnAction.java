package brickGame.gameWindow;

/**
 * an interface that allows other classes to implement its methods
 */
public interface OnAction {
    /**
     * updates gameBoard properties
     */
    void onUpdate();

    /**
     * initialization
     */
    void onInit();

    /**
     * updates game physics
     */
    void onPhysicsUpdate();

    /**
     * syncs the time for the class
     * @param time time
     */
    void onTime(long time);
}
