package brickGame.gameObjects.model.block;

import brickGame.gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class HeartBlock extends BlockController {
    private static final String NAME = "Heart Block";
    private static final Image img = new Image("heart.jpg");

    public HeartBlock(double xBlock, double yBlock) {
        super(NAME, xBlock, yBlock, img);
    }

    public Rectangle getBlock() {return super.getBlockFace();}
}
