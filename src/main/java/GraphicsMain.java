import brickGame.Block;
import brickGame.GameEngine;
import brickGame.Main;
import brickGame.Score;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphicsMain {
    //Application entry point
    //Main thread (UI)
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Refers to the main window of the JavaFX application
        this.primaryStage = primaryStage;

        //Initializing code for a new game
        if (!loadFromSave) {
            level++;
            if (level >1){
                new Score().showMessage("Level Up :)", this);
            }
            if (level == 18) {
                new Score().showWin(this);
                return;
            }

            //Initializing game elements
            initBall();
            initPaddle();
            initBoard();

            //Creating buttons for loading and starting a new game
            load = new Button("Load Game");
            newGame = new Button("Start New Game");
            load.setTranslateX(220); //220 units to the right
            load.setTranslateY(300); //300 units from the top
            newGame.setTranslateX(220);
            newGame.setTranslateY(340);
        }

        //Create the game scene
        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);

        //Add UI elements to the root pane
        if (!loadFromSave) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, load, newGame);
        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        }

        //Add blocks to the root pane
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        //Create game scene
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add("style.css");
        //Has to be in the same class as handle with the EventHandler<KeyEvent> interface
        //When a key is pressed while the focus is on the scene the handle event will be invoked
        scene.setOnKeyPressed(this);

        //Set up the primary stage
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        //Start the game engine
        //TODO rearrange
        if (!loadFromSave) { //Starting the game engine
            if (level > 1 && level < 18) {
                load.setVisible(false);
                newGame.setVisible(false);
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }

            //Set up event handlers for the load and new game buttons
            load.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    loadGame();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });

            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    engine = new GameEngine();
                    engine.setOnAction(Main.this);
                    engine.setFps(120);
                    engine.start();

                    load.setVisible(false);
                    newGame.setVisible(false);
                }
            });
        } else { //Starting the game engine when loaded from save
            //Test: take out of if-else
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }
    }
    //Entry point for the Java Virtual Machine when launching
    public static void main(String[] args) {
        //launch is a method of Application class which is part of the JavaFX lib
        //launch initializes the JavaFX runtime and starts the JavaFX application
        launch(args);
    }
}
