package gameObjects.model.block;

import gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class ChocBlock extends BlockController {
    private static final String NAME = "Chocolate Block";
    private static final Image img = new Image("choco.jpg");

    public ChocBlock(double xBlock, double yBlock) {
        super(NAME, xBlock, yBlock, img);
    }

    public Rectangle getBlock() {return super.getBlockFace();}
}
