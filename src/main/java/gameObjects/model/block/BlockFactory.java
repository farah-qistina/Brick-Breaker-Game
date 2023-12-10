package gameObjects.model.block;

import gameObjects.controller.BlockController;

public class BlockFactory {

    public BlockFactory() {}

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
