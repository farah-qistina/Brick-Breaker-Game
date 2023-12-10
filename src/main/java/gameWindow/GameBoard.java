package gameWindow;

import brickGame.Block;
import brickGame.Bonus;
import brickGame.GameEngine;
import brickGame.Score;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

public class GameBoard extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {
    private int destroyedBlockCount = 0;
    //Check if all blocks are destroyed
    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level...
            //System.out.println("You Win");
            nextLevel();
        }
    }

    private int  heart    = 3;
    private int  score    = 0;
    private long time     = 0;
    private long goldTime = 0;

    //Game engine
    private GameEngine engine;
    private boolean isGoldStatus      = false;
    //Scene dimensions in pixels
    private final int sceneWidth = 500;
    private final int sceneHeight = 700;

    //Direction constants
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    //Handles keyboard events
    //Main thread (UI)
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
            //Saves the game when s is pressed
            case S:
                saveGame();
                break;
        }
    }

    //UI elements
    //Display; layout purposes for UI
    public Pane root;
    private Label scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;
    //Window; holds the scenes
    Stage primaryStage;
    Button load    = null;
    Button newGame = null;

    //Save state flag
    private boolean loadFromSave = false;


}
