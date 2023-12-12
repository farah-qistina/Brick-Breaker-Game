package brickGame.gameObjects.model.board;

import brickGame.gameObjects.controller.BlockController;

import java.util.ArrayList;

public class LevelFactory {

    private Level level;
    public LevelFactory() {level = new Level();}

    //Initialize the game board with blocks
    public ArrayList<BlockController> makeLevel(int lvl) {
        return level.makeLevel(lvl);
    }
}
