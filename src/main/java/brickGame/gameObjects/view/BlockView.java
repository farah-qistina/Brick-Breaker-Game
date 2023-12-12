package brickGame.gameObjects.view;

import brickGame.gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class BlockView {
    /**
     * Default constructor
     */
    public BlockView() {}
    public void drawBlock(BlockController block) {
        Image img = block.getBlockImage();
        block.getBlockFace().setFill(new ImagePattern(img));
    }
}
