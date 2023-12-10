package gameObjects.controller;

import gameObjects.model.block.BlockModel;
import gameObjects.model.block.ImpactDirection;
import gameObjects.view.BlockView;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 *
 */
public abstract class BlockController {
    private Rectangle blockFace = new Rectangle();
    private final BlockModel blockModel;
    private final BlockView blockView;

    public BlockController(String name, double xBlock, double yBlock, Image img) {
        blockModel = new BlockModel(name, xBlock, yBlock, img);
        setBlockFace(makeBlockFace(xBlock, yBlock));
        blockView = new BlockView();
    }

    private Rectangle makeBlockFace(double xBlock, double yBlock) {
        Rectangle out = new Rectangle();
        out.setWidth(BlockModel.getBlockWidth());
        out.setHeight(BlockModel.getBlockHeight());
        out.setX(xBlock);
        out.setY(yBlock);
        return out;
    }

    public abstract Rectangle getBlock();

    public void impact() {
        blockModel.setBroken(true);
    }

    //collision detection with block
    //TODO improve conditions
    public final ImpactDirection findImpact (BallController ballController) {
        if (getBroken()) {
            return ImpactDirection.NO_IMPACT;
        }

        double xBall = ballController.getX();
        double yBall = ballController.getY();
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

    public final boolean getBroken() {
        return blockModel.getBroken();
    }

    public void updateView(BlockController block) {
        blockView.drawBlock(block);
    }

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

}
