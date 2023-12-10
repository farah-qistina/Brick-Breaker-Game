package gameObjects.view;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PaddleView {
    //Initialize the paddle
    private void initPaddle() {
        rect = new Rectangle();
        rect.setWidth(paddleWidth);
        rect.setHeight(paddleHeight);
        rect.setX(xPaddle);
        rect.setY(yPaddle);

        //TODO combine
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }
}
