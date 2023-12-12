package brickGame.gameObjects.view;

import brickGame.gameObjects.controller.PaddleController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class PaddleView {

    public PaddleView() {}

    public void drawPaddle(PaddleController paddle) {
        Image img = paddle.getImage();
        paddle.getPaddleFace().setFill(new ImagePattern(img));
    }
}
