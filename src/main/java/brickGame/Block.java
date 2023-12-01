package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

//consists of properties, padding, hit codes, block type codes, initialization, draw, collision with blocks
public class Block implements Serializable {
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

    //hit codes
    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    //block type codes
    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;


    //block initialization
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * width) + paddingLeft;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }
    }

    //converts blocks to a map and later reconstructed
    //TODO add color
    public Map<String, Integer> BlockSerializable(int row, int column, int type){
        Map<String, Integer> result = new HashMap<>();
        result.put("row",row);
        result.put("column", column);
        result.put("type", type);
        return result;
    }

    //collision detection
    //TODO add edge collision
    //TODO consider radius
    public int checkHitToBlock(double xBall, double yBall) {

        if (isDestroyed) {
            return NO_HIT;
        }

        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return HIT_BOTTOM;
        }

        if (xBall >= x && xBall <= x + width && yBall == y) {
            return HIT_TOP;
        }

        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return HIT_RIGHT;
        }

        if (yBall >= y && yBall <= y + height && xBall == x) {
            return HIT_LEFT;
        }

        return NO_HIT;
    }

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