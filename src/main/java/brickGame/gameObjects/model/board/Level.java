package brickGame.gameObjects.model.board;

import brickGame.gameObjects.controller.BlockController;
import brickGame.gameObjects.model.block.BlockFactory;
import brickGame.gameObjects.model.block.BlockType;

import java.util.ArrayList;
import java.util.Random;

public class Level {
    private BlockFactory blockFactory;

    private boolean isExistHeartBlock = false;
    //padding from the top of the window to the top of the first row of blocks
    public static final int paddingTop = BlockController.getBlockHeight() * 2;
    //padding to the left of the blocks
    public static final int paddingLeft = 50;

    public Level() {
        blockFactory = new BlockFactory();
    }
    public ArrayList<BlockController> makeLevel(int level) {
        ArrayList<BlockController> out = new ArrayList<BlockController>();
        //i is columns
        for (int i = 0; i < 4; i++) {
            //j is rows
            for (int j = 0; j < level + 1; j++) {
                //Randomly determines if a block should be skipped
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }

                //Determine the type of block based on random number 'r'
                BlockType type;
                if (r % 10 == 1) {
                    type = BlockType.CHOC;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = BlockType.HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = BlockType.NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = BlockType.GOLD;
                } else {
                    type = BlockType.NORMAL;
                }
                //Block creation and addition
                //Block coordinates
                double x = (i * BlockController.getBlockWidth()) + paddingLeft;
                double y = (j * BlockController.getBlockHeight()) + paddingTop;
                out.add(blockFactory.makeBlock(type, x, y));
                //System.out.println("colors " + r % (colors.length));
            }
        }
        return out;
    }
}
