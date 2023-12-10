package gameObjects.model.paddle;

public class PlayerModel {

    //Paddle dimensions
    private final int paddleWidth     = 130;
    private final int paddleHeight    = 30;
    private final int halfPaddleWidth = paddleWidth / 2;


    //Paddle position
    //Left side of the paddle
    private double xPaddle = 0.0f;
    //Top of the paddle, 640 units from the top of the game window
    private double yPaddle = 640.0f;
    private double centerPaddleX;
}
