package brickGame.gameObjects.model.block;

import javafx.scene.image.Image;

import java.util.Random;

/**
 * Class that stores data for BlockController
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

    /**
     * default constructor to initialize the default value for the block
     * @param name name of the block
     * @param xBlock top left x coordinate of the block
     * @param yBlock top left y coordinate of the block
     * @param img image of the block
     */
    //block initialization
    public BlockModel(String name, double xBlock, double yBlock,Image img) {

        setBroken(false);
        setRnd(new Random());
        this.setName(name);

        this.xBlock = xBlock;
        this.yBlock = yBlock;
        this.img = img;
    }

    //setter and getter methods

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
