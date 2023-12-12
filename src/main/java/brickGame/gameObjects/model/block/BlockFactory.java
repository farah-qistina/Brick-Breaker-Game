package brickGame.gameObjects.model.block;

import brickGame.gameObjects.controller.BlockController;

/**
 * This class implements the Factory design pattern to create block objects
 */
public class BlockFactory {

    /**
     * default constructor
     */
    public BlockFactory() {}

    /**
     * factory method to create block objects
     * @param type the type of the block
     * @param xBlock the top left x coordinate of the block
     * @param yBlock the top left y coordinate of the block
     * @return a brick object
     */
    public BlockController makeBlock(BlockType type, double xBlock, double yBlock) {
        BlockController out;
        switch (type) {
            case NORMAL:
                out = new NormalBlock(xBlock, yBlock);
                break;
            case CHOC:
                out = new ChocBlock(xBlock, yBlock);
                break;
            case GOLD:
                out = new GoldBlock(xBlock, yBlock);
                 break;
            case HEART:
                out = new HeartBlock(xBlock, yBlock);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown Type:%s\n", type));
        }
        return out;
    }
}
