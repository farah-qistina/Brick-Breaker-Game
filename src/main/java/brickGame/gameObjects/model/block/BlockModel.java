package brickGame.gameObjects.model.block;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * Class that stores data for BallController
 */
public class BlockModel {
    protected static Random rnd;
    protected static int blockWidth = 100;
    protected static int blockHeight = 30;
    //top-left corner
    private double xBlock;
    private double yBlock;
    private String name;
    private boolean broken;
    private Image img;

    //block initialization
    public BlockModel(String name, double xBlock, double yBlock,Image img) {

        setBroken(false);
        setRnd(new Random());
        this.setName(name);

        this.xBlock = xBlock;
        this.yBlock = yBlock;
        this.img = img;
//        this.row = row;
//        this.column = column;
//        this.color = color;
//        this.type = type;
//
//        draw();
    }
//    //static member as a dummy block
//    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);
//
//    //first of both is 0
//    public int row;
//    public int column;

    public void setX(double x) {this.xBlock = x;}
    public double getX() {return xBlock;}

    public void setY(double y) {this.yBlock = y;}
    public double getY() {return yBlock;}

    public static int getBlockWidth() {return blockWidth;}
    public static int getBlockHeight() {return blockHeight;}
    public static void setRnd(Random rnd) {BlockModel.rnd = rnd;}
    public static Random getRnd() {return rnd;}

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

    public  void setBroken(boolean broken) {
        this.broken = broken;
    }
    public boolean getBroken() {return broken;}

    public Image getBlockImage() {return img;}
}
