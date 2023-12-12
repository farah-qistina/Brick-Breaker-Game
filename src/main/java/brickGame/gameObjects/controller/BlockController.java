package brickGame.gameObjects.controller;

import brickGame.gameObjects.model.block.BlockModel;
import brickGame.gameObjects.model.block.ImpactDirection;
import brickGame.gameObjects.view.BlockView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Abstract class for other classes to inherit
 */
public abstract class BlockController {
    private Rectangle blockFace = new Rectangle();
    private final BlockModel blockModel;
    private final BlockView blockView;

    /**
     * Block class constructor
     * @param name name of the block according to type
     * @param xBlock top left x coordinate of the block
     * @param yBlock top left y coordinate of the block
     * @param img block image
     */
    public BlockController(String name, double xBlock, double yBlock, Image img) {
        blockModel = new BlockModel(name, xBlock, yBlock, img);
        setBlockFace(makeBlockFace(xBlock, yBlock));
        blockView = new BlockView();
    }

    /**
     * makes a rectangle block face
     * @param xBlock top left x coordinate of the block
     * @param yBlock top left y coordinate of the block
     * @return the block face
     */
    private Rectangle makeBlockFace(double xBlock, double yBlock) {
        Rectangle out = new Rectangle();
        out.setWidth(BlockModel.getBlockWidth());
        out.setHeight(BlockModel.getBlockHeight());
        out.setX(xBlock);
        out.setY(yBlock);
        return out;
    }

    /**
     * updates the view of the block
     * @param block the block
     */
    public void updateView(BlockController block) {
        blockView.drawBlock(block);
    }


    /**
     * finds the impact direction of the collision between a ball and block
     * @param ballController the ball
     * @return impact direction
     */
    //collision detection with block
    //TODO improve conditions
    public final ImpactDirection findImpact (BallController ballController) {
        if (getBroken()) {
            return ImpactDirection.NO_IMPACT;
        }

        double xBall = ballController.getBallFace().getCenterX();
        double yBall = ballController.getBallFace().getCenterY();
        double xBlock = blockModel.getX();
        double yBlock = blockModel.getY();
        int width = BlockModel.getBlockWidth();
        int height = BlockModel.getBlockHeight();

        if (yBall >= yBlock && yBall <= yBlock + height && xBall == xBlock + width) {
            return ImpactDirection.RIGHT_IMPACT;
        }

        if (yBall >= yBlock && yBall <= yBlock + height && xBall == xBlock) {
            return ImpactDirection.LEFT_IMPACT;
        }

        if (xBall >= xBlock && xBall <= xBlock + width && yBall == yBlock + height) {
            return ImpactDirection.DOWN_IMPACT;
        }

        if (xBall >= xBlock && xBall <= xBlock + width && yBall == yBlock) {
            return ImpactDirection.UP_IMPACT;
        }

        return ImpactDirection.NO_IMPACT;
    }

    //setter and getter methods

    /**
     * Getter for the block object
     * @return the block object
     */
    public abstract Rectangle getBlock();

    public final boolean getBroken() {
        return blockModel.getBroken();
    }
    public void setBroken(boolean broken) {setBroken(broken);}
    public static int getBlockWidth() {return BlockModel.getBlockWidth();}
    public static int getBlockHeight() {return BlockModel.getBlockHeight();}

    public void setBlockFace(Rectangle blockFace) {
        this.blockFace = blockFace;
    }
    public Rectangle getBlockFace() {
        return blockFace;
    }

    public Image getBlockImage() {return blockModel.getBlockImage();}

    /**
     * Setter to set the name of the brick
     *
     * @param name String name of the brick
     */

    public void setName(String name) {
        blockModel.setName(name);
    }

    /**
     * Getter to get the brick's name
     *
     * @return String brick name
     */

    public String getName() {
        return blockModel.getName();
    }

    public double getX() {return blockModel.getX();}
    public double getY() {return blockModel.getY();}
}