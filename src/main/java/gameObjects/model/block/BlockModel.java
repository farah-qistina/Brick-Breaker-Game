package gameObjects.model.block;

import brickGame.Block;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlockModel {
    //block initialization
    public BlockModel(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }
    //static member as a dummy block
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    //first of both is 0
    public int row;
    public int column;

    public boolean isDestroyed = false;

    private Color color;
    public int type;

    //top-left corner of the block
    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    //padding from the top of the window to the top of the first row of blocks
    private int paddingTop = height * 2;
    //padding to the left of the blocks
    private int paddingLeft = 50;
    public Rectangle rect;
    private final Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };


    //accessor methods
    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingLeft() {
        return block.paddingLeft;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }
}
