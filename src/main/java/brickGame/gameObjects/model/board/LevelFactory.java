package brickGame.gameObjects.model.board;

import brickGame.gameObjects.controller.BlockController;

import java.util.ArrayList;

/**
 * implements the factory design to create a level
 */
public class LevelFactory {

    private Level level;
    public LevelFactory() {level = new Level();}

    /**
     * method to create a set of blocks for the board according to the level
     * @param lvl
     * @return
     */
    //Initialize the game board with blocks
    public ArrayList<BlockController> makeLevel(int lvl) {
        return level.makeLevel(lvl);
    }
}
