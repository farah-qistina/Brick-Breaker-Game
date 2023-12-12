package brickGame.gameObjects.model.block;

import brickGame.gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Objects of this class inherits from the blockController class, creating heart blocks
 */
public class HeartBlock extends BlockController {
    private static final String NAME = "Heart Block";
    private static final Image img = new Image("heart.jpg");

    /**
     * Constructor to create a brick object of the type heart
     * @param xBlock top left x coordinate of the block
     * @param yBlock top left y coordinate of the block
     */
    public HeartBlock(double xBlock, double yBlock) {
        super(NAME, xBlock, yBlock, img);
    }

    /**
     * getter method for the block
     * @return block face
     */
    public Rectangle getBlock() {return super.getBlockFace();}
}
