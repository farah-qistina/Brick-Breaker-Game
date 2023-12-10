package gameObjects.view;

import brickGame.Block;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

public class BallView {
    //Initialize the ball
    private void initBall() {
        Random random = new Random();
        //generates random x and y coordinates for the ball
        xBall = random.nextInt(sceneWidth) + 1;
        //Ensures the ball starts below the blocks ((level + 1) * Block.getHeight() + 15) and above a certain threshold (sceneHeight - 200)
        yBall = random.nextInt(sceneHeight - 200) + (level + 1) * Block.getHeight() + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }
}
