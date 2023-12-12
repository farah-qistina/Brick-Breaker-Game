package brickGame.gameObjects.model.block;

import brickGame.gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Objects of this class inherits from the blockController class, creating gold blocks
 */
public class GoldBlock extends BlockController {
    private static final String NAME = "Gold Block";
    private static final Image img = new Image("star.jpg");

    /**
     * Constructor to create a brick object of the type gold
     * @param xBlock top left x coordinate of the block
     * @param yBlock top left y coordinate of the block
     */
    public GoldBlock(double xBlock, double yBlock) {
        super(NAME, xBlock, yBlock, img);
    }

    /**
     * getter method for the block
     * @return block face
     */
    public Rectangle getBlock() {return super.getBlockFace();}
}
