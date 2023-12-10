package gameObjects.model.block;

import gameObjects.controller.BlockController;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class GoldBlock extends BlockController {
    private static final String NAME = "Gold Block";
    private static final Image img = new Image("star.jpg");

    public GoldBlock(double xBlock, double yBlock) {
        super(NAME, xBlock, yBlock, img);
    }

    public Rectangle getBlock() {return super.getBlockFace();}
}
