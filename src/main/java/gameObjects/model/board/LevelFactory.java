package gameObjects.model.board;

import brickGame.Block;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class LevelFactory {
    private boolean isExistHeartBlock = false;

    //Block properties
    private Rectangle rect;
    //Game elements
    private final ArrayList<Block> blocks = new ArrayList<Block>();

    //Game settings
    private int level = 0;
    //Initialize the game board with blocks
    private void initBoard() {
        //i is columns
        for (int i = 0; i < 4; i++) {
            //j is rows
            for (int j = 0; j < level + 1; j++) {
                //Randomly determines if a block should be skipped
                //TODO random logic
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }

                //Determine the type of block based on random number 'r'
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                //Block creation and addition
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }
}
