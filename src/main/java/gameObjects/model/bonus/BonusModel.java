package gameObjects.model.bonus;

import brickGame.Block;
import javafx.scene.shape.Rectangle;

public class BonusModel {
    //TODO make private
    public Rectangle bonus;

    public double x;
    public double y;
    //time created in milliseconds
    public long timeCreated;
    public boolean taken = false;

    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingLeft() + (Block.getWidth() / 2) - 15;//half of bonus
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
    }
}
