package brickGame.gameWindow;

import brickGame.GraphicsMain;
import brickGame.gameObjects.controller.BoardController;
import brickGame.gameObjects.controller.BonusController;
import brickGame.gameObjects.controller.PaddleController;
import brickGame.gameObjects.model.paddle.PaddleModel;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * objects of this class implements OnAction and EventHandler<KeyEvent>
 */
public class GameBoard implements EventHandler<KeyEvent>, OnAction {
    private static BoardController boardController;
    private PaddleController paddleController;

    //Scene dimensions in pixels
    private static final int sceneWidth = 500;
    private static final int sceneHeight = 700;

    public static int getSceneWidth() {
        return sceneWidth;
    }

    public static int getSceneHeight() {
        return sceneHeight;
    }

    GraphicsMain graphicsMain = new GraphicsMain();



    //Direction constants
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;


    /**
     * default constructor
     */
    public GameBoard() {
    }

    /**
     * handles keyboard events
     * @param event keyboard press
     */
    //Main thread (UI)
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                paddleController.move(LEFT);
                break;
            case RIGHT:
                paddleController.move(RIGHT);
                break;
            //Saves the game when s is pressed
            case S:
//                saveGame();
                break;
        }
    }

    /**
     * Checks if all the blocks in a board are destroyed
     */
    //Check if all blocks are destroyed
    public void checkDestroyedCount() {
        if (boardController.getDestroyedBlockCount() == boardController.getBlocks().size()) {
            graphicsMain.nextLevel();
        }
    }

    /**
     * updates the visual representation of the game, handling collisions, and responding to events
     */
    @Override
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                GraphicsMain.scoreLabel.setText("Score: " + boardController.getScore());
                GraphicsMain.heartLabel.setText("Heart : " + boardController.getHeart());

                //Updates the Y-coordinate of the 'bonus' based on the elapsed time since its creation
                //bonus is "falling"
                //Updates the Y-coordinate of each bonus object in the bonuses list
                for (BonusController bonus : boardController.getBonuses()) {
                    bonus.moveY(((GraphicsMain.getTime() - bonus.getTimeCreated()) / 1000.000) + 1.000);
                }
            }
        });

//        BoardController.blockImpact();
    }

    /**
     * Initialization callback
     */
    @Override
    public void onInit() {
    }

    /**
     * physics update callback
     */
    @Override
    public void onPhysicsUpdate() {
        //Checks if the player has finished the level
        checkDestroyedCount();
        boardController.findImpacts();

        //Checks if the time elapsed since the last time there was a gold ball is greater than 5s
        if (GraphicsMain.getTime() - boardController.getGoldTime() > 5000) {
            //Returns to default
            boardController.setBall("GOLD", boardController.getBall().getBallFace().getCenterX(), boardController.getBall().getBallFace().getCenterY());
            GraphicsMain.root.getStyleClass().remove("goldRoot");
            boardController.setGoldStatus(false);
        }

        //Iterates through the bonuses list
        for (BonusController bonus : boardController.getBonuses()) {
            //If the Y-coordinate of the bonus is greater than the sceneHeight or if bonus is taken
            if (bonus.getBonusFace().getY() > sceneHeight || bonus.getTaken()) {
                continue;
            }
            //If the bonus is within the vertical and horizontal range of the paddle
            if (bonus.getBonusFace().getY() >= boardController.getPaddle().getPaddleFace().getY() && bonus.getBonusFace().getY() <= boardController.getPaddle().getPaddleFace().getY() + PaddleModel.getPaddleHeight() && bonus.getBonusFace().getX() >= boardController.getPaddle().getPaddleFace().getX() && bonus.getBonusFace().getX() <= boardController.getPaddle().getPaddleFace().getX() + PaddleModel.getPaddleWidth()) {
                System.out.println("You Got it and +3 score for you");
                bonus.setTaken(true);
                bonus.getBonusFace().setVisible(false);
                boardController.setScore(boardController.getScore() + 3);
                new ShowMessage().show(bonus.getBonusFace().getX(), bonus.getBonusFace().getY(), 3);
            }
        }

        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }

    /**
     * time update callback for the class to stay in sync with the overall game time
     * @param time time
     */
    @Override
    public void onTime(long time) {
        GraphicsMain.time = time;
    }
}
