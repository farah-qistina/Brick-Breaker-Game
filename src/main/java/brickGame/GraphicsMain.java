package brickGame;

import brickGame.gameObjects.controller.BlockController;
import brickGame.gameObjects.controller.BoardController;
import brickGame.gameWindow.GameBoard;
import brickGame.gameWindow.GameEngine;
import brickGame.gameWindow.ShowMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main thread (UI)
 */
public class GraphicsMain extends Application {

    //Window; holds the scenes
    public Stage primaryStage;

    public static Pane root;
    public static long time     = 0;
    public static long getTime() {return time;}
    //Game settings
    private static int level = 0;
    //Game engine
    private static GameEngine engine;

    //UI elements
    //Display; layout purposes for UI

    static Button load    = null;
    static Button newGame = null;
    public static Label            scoreLabel;
    public static Label            heartLabel;
    public Label            levelLabel;


    //Save state flag
//    private boolean loadFromSave = false;
    static BoardController boardController;
    public GraphicsMain() {}

    /**
     * overridden start method
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        //Refers to the main window of the JavaFX application

        //Initializing code for a new game
//        if (!loadFromSave) {
            level++;
            if (level >1){
                new ShowMessage().showMessage("Level Up :)");
            }
            if (level == 18) {
                new ShowMessage().showWin();
                return;
            }

            //Initializing game elements
            BoardController boardController = new BoardController(level);

            //Creating buttons for loading and starting a new game
            load = new Button("Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(220); //220 units to the right
            load.setTranslateY(300); //300 units from the top
            newGame.setTranslateX(220);
            newGame.setTranslateY(340);
//        }

        //Create the game scene
        root = new Pane();
        scoreLabel = new Label("Score: " + boardController.getScore());
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + boardController.getHeart());
        heartLabel.setTranslateX(GameBoard.getSceneWidth() - 70);

        //Add UI elements to the root pane
//        if (!loadFromSave) {
            root.getChildren().addAll(boardController.getPaddle().getPaddleFace(), boardController.getBall().getBallFace(), scoreLabel, heartLabel, levelLabel, load, newGame);
//        } else {
//            root.getChildren().addAll(boardController.getPaddle().getPaddleFace(), boardController.getBall().getBallFace(), scoreLabel, heartLabel, levelLabel);
//        }

        //Add blocks to the root pane
        for (BlockController block : boardController.getBlocks()) {
            root.getChildren().add(block.getBlockFace());
        }

        //Create game scene
        Scene scene = new Scene(root, GameBoard.getSceneWidth(), GameBoard.getSceneHeight());
        scene.getStylesheets().add("style.css");
        //Has to be in the same class as handle with the EventHandler<KeyEvent> interface
        //When a key is pressed while the focus is on the scene the handle event will be invoked
        GameBoard gameBoard = new GameBoard();
        scene.setOnKeyPressed(gameBoard);

        //Set up the primary stage
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Start the game engine
        //TODO rearrange
//        if (!loadFromSave) { //Starting the game engine
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(gameBoard);
                engine.setFps(120);
                engine.start();
//            }

            //Set up event handlers for the load and new game buttons
            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
//                    loadGame();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(gameBoard);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else { //Starting the game engine when loaded from save
            //Test: take out of if-else
            engine = new GameEngine();
            engine.setOnAction(gameBoard);
            engine.setFps(120);
            engine.start();
//            loadFromSave = false;
        }
    }

    /**
     * Move to the next game level
     */
    public void nextLevel() {
        //Platform.runLater ensures that UI updates are done on the main thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //try-catch block
                try {
                    engine.stop(); //halts game loop before transitioning to a new level
                    BoardController boardController = new BoardController(level);
                    start(primaryStage);
                    //reset

                    time = 0;
                    //start a new game by calling the start method with the primaryStage

                } catch (Exception e) {
                    //prints any exceptions that might occur
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * restart the game
     */
    public void restartGame() {
        try {
            engine.stop();
            BoardController boardController = new BoardController(1);
            time = 0;

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Entry point for the Java Virtual Machine when launching
     * @param args argument
     */
    public static void main(String[] args) {
        //launch is a method of Application class which is part of the JavaFX lib
        //launch initializes the JavaFX runtime and starts the JavaFX application
        launch(args);
    }
}
