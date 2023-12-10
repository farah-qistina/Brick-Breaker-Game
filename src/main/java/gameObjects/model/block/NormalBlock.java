package gameObjects.model.block;

import gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class NormalBlock extends BlockController {
    private static final String NAME = "Normal Block";
    private static final Image img = new Image("purple.jpg");

    public NormalBlock(double xBlock, double yBlock) {
        super(NAME, xBlock, yBlock, img);
    }

    public Rectangle getBlock() {return super.getBlockFace();}
}
