package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

//use of hash for serializables
import java.util.HashMap;
import java.util.Map;

//@Override, indicates that it overrides a method declared in an interface or superclass

//Main class that extends Application and implements event handlers and game engine callbacks
public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    //Game settings
    private int level = 0;

    //Paddle position
    //Left side of the paddle
    private double xPaddle = 0.0f;
    //Top of the paddle, 640 units from the top of the game window
    private double yPaddle = 640.0f;
    private double centerPaddleX;

    //Paddle dimensions
    private final int paddleWidth     = 130;
    private final int paddleHeight    = 30;
    private final int halfPaddleWidth = paddleWidth / 2;

    //Scene dimensions in pixels
    private final int sceneWidth = 500;
    private final int sceneHeight = 700;

    //Direction constants
    private static final int LEFT  = 1;
    private static final int RIGHT = 2;

    //Ball properties
    private Circle ball;
    //Represents the x and y of the center of the ball
    private double xBall;
    private double yBall;
    private final int ballRadius = 10;

    //Game state flags
    private boolean isGoldStatus      = false;
    private boolean isExistHeartBlock = false;

    //Block properties
    private Rectangle rect;

    private int destroyedBlockCount = 0;

    private int  heart    = 3;
    private int  score    = 0;
    private long time     = 0;
    private long goldTime = 0;

    //Game engine
    private GameEngine engine;

    //Save file paths
    public static String savePath    = "./save/save.mdds";
    public static String savePathDir = "./save/";

    //Game elements
    private final ArrayList<Block> blocks = new ArrayList<Block>();
    private final ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
    private final Color[]          colors = new Color[]{
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };

    //UI elements
    //Display; layout purposes for UI
    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;
    private Label            levelLabel;
    //Window; holds the scenes
    Stage  primaryStage;
    Button load    = null;
    Button newGame = null;

    //Save state flag
    private boolean loadFromSave = false;

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

    //Initialize the game board with blocks
    private void initBoard() {
        //i is columns
        for (int i = 0; i < 4; i++) {
            //j is rows
            for (int j = 0; j < level + 1; j++) {
                //Randomly determines if a block should be skipped
                //TODO random logic
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }

                //Determine the type of block based on random number 'r'
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                //Block creation and addition
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }

    //Entry point for the Java Virtual Machine when launching
    public static void main(String[] args) {
        //launch is a method of Application class which is part of the JavaFX lib
        //launch initializes the JavaFX runtime and starts the JavaFX application
        launch(args);
    }

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

    //Move the paddle based on the direction parameter
    private void move(final int direction) {
        //Separate thread to avoid blocking the JavaFX application's main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Duration for which the thread sleeps between each movement step
                int initialSleepTime = 4;
                //Controls the number of movement steps the paddle takes, iterates 30 times
                for (int i = 0; i < 30; i++) {
                    //Checks if the paddle has reached the right edge of the game area and direction is right, method returns to prevent movement
                    if (xPaddle == (sceneWidth - paddleWidth) && direction == RIGHT) {
                        return;
                    }
                    //"" left edge
                    if (xPaddle == 0 && direction == LEFT) {
                        return;
                    }
                    //Updates the X-coordinate of the paddle based on the direction
                    if (direction == RIGHT) {
                        xPaddle++;
                    } else {
                        xPaddle--;
                    }
                    //Updates the center of paddle based on its new position
                    centerPaddleX = xPaddle + halfPaddleWidth;
                    //Introduces a sleep to slow down the movement making it visible and controlled
                    try {
                        Thread.sleep(initialSleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //After 20 iterations, sleep time gradually increases and makes the paddle move faster
                    if (i >= 20) {
                        initialSleepTime = i;
                    }
                }
            }
        }).start(); //Starts the thread
    }

    //Initialize the ball
    private void initBall() {
        Random random = new Random();
        //generates random x and y coordinates for the ball
        xBall = random.nextInt(sceneWidth) + 1;
        //Ensures the ball starts below the blocks ((level + 1) * Block.getHeight() + 15) and above a certain threshold (sceneHeight - 200)
        yBall = random.nextInt(sceneHeight - 200) + (level + 1) * Block.getHeight() + 15;
        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
    }

    //Initialize the paddle
    private void initPaddle() {
        rect = new Rectangle();
        rect.setWidth(paddleWidth);
        rect.setHeight(paddleHeight);
        rect.setX(xPaddle);
        rect.setY(yPaddle);

        //TODO combine
        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);
    }

    //Flags to track ball movement and collisions
    //should the ball move down and right
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    //whether the ball has collided with the paddle
    private boolean collideToPaddle               = false;
    //whether the ball should move to the right after colliding with the paddle
    private boolean collideToPaddleAndMoveToRight = true;
    private boolean collideToRightWall           = false;
    private boolean collideToLeftWall            = false;
    private boolean collideToRightBlock          = false;
    private boolean collideToBottomBlock         = false;
    private boolean collideToLeftBlock           = false;
    private boolean collideToTopBlock            = false;

    //Velocity components for the ball
    //Controls the speed and direction to the right
    private double vX = 1.000;
    private final double vY = 1.000;

    //Reset all collision flags
    //helps prevent unintended or persistent effects from previous collisions that could affect the ball's behavior incorrectly
    private void resetCollideFlags() {

        collideToPaddle = false;
        collideToPaddleAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    //Ball physics
    private void setPhysicsToBall() {

        //Update ball position based on velocity
        if (goDownBall) {
            //going down
            yBall += vY;
        } else {
            //going up
            yBall -= vY;
        }

        if (goRightBall) {
            //going right
            xBall += vX;
        } else {
            //going left
            xBall -= vX;
        }

        //Handle boundary conditions
        //TODO change boundary
        if (yBall <= 0) {
            resetCollideFlags();
            goDownBall = true;
            return;
        }
        if (yBall >= sceneHeight) {
            goDownBall = false;
            //Handle game over
            if (!isGoldStatus) {
                //TODO gameover
                heart--;
                new Score().show(sceneWidth / 2, sceneHeight / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }
            }
        }

        //Handle collision with the paddle
        //Checks if the ball is on or below the paddle
        if (yBall >= yPaddle - ballRadius) {
            //System.out.println("Collide1");
            //Checks if the ball is on the paddle or not
            if (xBall >= xPaddle && xBall <= xPaddle + paddleWidth) {
                //TODO arrange reset efficiently
                resetCollideFlags();
                collideToPaddle = true;
                goDownBall = false;

                //Adjust velocity based on the position of the collision on the paddle
                //calculates the position of the ball with respect to the centre of the paddle
                double relation = (xBall - centerPaddleX) / (paddleWidth / 2);

                //if the ball is near the centre of the paddle
                if (Math.abs(relation) <= 0.3) {
                    vX = Math.abs(relation);
                //if the ball is towards the edges of the paddle
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                    //if the ball is at the far edges
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                //determines whether the ball should move to the right relative to the centre
                collideToPaddleAndMoveToRight = xBall - centerPaddleX > 0;
                //System.out.println("Collide2");
            }
        }

        //Handle collision with walls
        if (xBall >= sceneWidth) {
            resetCollideFlags();
            collideToRightWall = true;
        }

        if (xBall <= 0) {
            resetCollideFlags();
            collideToLeftWall = true;
        }

        //TODO integrate into if-else above
        //Handle paddle collision
        if (collideToPaddle) {
            goRightBall = collideToPaddleAndMoveToRight;
        }

        //Wall Collide
        if (collideToRightWall) {
            goRightBall = false;
        }

        if (collideToLeftWall) {
            goRightBall = true;
        }

        //Block Collide
        //TODO left-right error
        if (collideToRightBlock) {
            goRightBall = true;
        }

        if (collideToLeftBlock) {
            goRightBall = true;
        }

        if (collideToTopBlock) {
            goDownBall = false;
        }

        if (collideToBottomBlock) {
            goDownBall = true;
        }
    }

    //Check if all blocks are destroyed
    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level...
            //System.out.println("You Win");
            nextLevel();
        }
    }

    //Save the current game state to a file
    private void saveGame() {
        //opens up a new thread to perform the save operation in the bg
        new Thread(new Runnable() {
            @Override
            public void run() {
                //creates the directory for saving the file
                new File(savePathDir).mkdirs();
                //create a file object representing the save file
                File file = new File(savePath);
                //Initializing the ObjectOutputStream for writing objects to the file
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    //write various game-related data to the file
                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(heart);
                    outputStream.writeInt(destroyedBlockCount);


                    outputStream.writeDouble(xBall);
                    outputStream.writeDouble(yBall);
                    outputStream.writeDouble(xPaddle);
                    outputStream.writeDouble(yPaddle);
                    outputStream.writeDouble(centerPaddleX);
                    outputStream.writeLong(time);
                    outputStream.writeLong(goldTime);
                    outputStream.writeDouble(vX);


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(isGoldStatus);
                    outputStream.writeBoolean(goDownBall);
                    outputStream.writeBoolean(goRightBall);
                    outputStream.writeBoolean(collideToPaddle);
                    outputStream.writeBoolean(collideToPaddleAndMoveToRight);
                    outputStream.writeBoolean(collideToRightWall);
                    outputStream.writeBoolean(collideToLeftWall);
                    outputStream.writeBoolean(collideToRightBlock);
                    outputStream.writeBoolean(collideToBottomBlock);
                    outputStream.writeBoolean(collideToLeftBlock);
                    outputStream.writeBoolean(collideToTopBlock);

                    //create a list to store serializable representations of non-destroyed blocks
//                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    ArrayList<Map<String, Integer>> blockSerializables = new ArrayList<Map<String, Integer>>();
                    for (Block block : blocks) {
                        //skip destroyed blocks
                        if (block.isDestroyed) {
                            continue;
                        }
                        //add a serializable representation of the block to the list
                        //TODO add color
                        blockSerializables.add(block.BlockSerializable(block.row, block.column, block.type));
                    }

                    //write the list of block serializables to the file
                    outputStream.writeObject(blockSerializables);

                    //show a message indicating that the game has been saved
                    new Score().showMessage("Game Saved", Main.this);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //TODO use try-with-resources method
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //Load the game state from a file
    private void loadGame() {

        //Create an instance of the LoadSave class
        LoadSave loadSave = new LoadSave();
        //read the saved game data
        loadSave.read();

        //load the saved data into the current game state
        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStatus;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        collideToPaddle = loadSave.collideToPaddle;
        collideToPaddleAndMoveToRight = loadSave.collideToPaddleAndMoveToRight;
        collideToRightWall = loadSave.collideToRightWall;
        collideToLeftWall = loadSave.collideToLeftWall;
        collideToRightBlock = loadSave.collideToRightBlock;
        collideToBottomBlock = loadSave.collideToBottomBlock;
        collideToLeftBlock = loadSave.collideToLeftBlock;
        collideToTopBlock = loadSave.collideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xPaddle = loadSave.xPaddle;
        yPaddle = loadSave.yPaddle;
        centerPaddleX = loadSave.centerPaddleX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

        //clear existing blocks and bonuses
        blocks.clear();
        bonuses.clear();

        //create block objects based on the serialized data
        for (Map<String, Integer> ser : loadSave.blocks) {
            //TODO remove color
            //generate a random number to determine the color
            int r = new Random().nextInt(200);
            //create a new block and add it to the blocks list
            blocks.add(new Block(ser.get("row"), ser.get("column"), colors[r % colors.length], ser.get("type")));
        }

        try {
            loadFromSave = true;
            //start the game with the loaded state
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Move to the next game level
    private void nextLevel() {
        //Platform.runLater ensures that UI updates are done on the main thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //try-catch block
                try {
                    //reset
                    vX = 1.000;
                    isGoldStatus = false;
                    isExistHeartBlock = false;

                    //stops the game engine
                    engine.stop(); //halts game loop before transitioning to a new level
                    resetCollideFlags();
                    goDownBall = true;

                    time = 0;
                    goldTime = 0;

                    //clear list of blocks and bonuses
                    blocks.clear();
                    bonuses.clear();
                    destroyedBlockCount = 0;
                    //start a new game by calling the start method with the primaryStage
                    start(primaryStage);

                } catch (Exception e) {
                    //prints any exceptions that might occur
                    e.printStackTrace();
                }
            }
        });
    }

    //Restart the game
    //TODO redundant; make a reset method
    public void restartGame() {
        try {
            //game engine stop?
            level = 0;
            heart = 3;
            score = 0;
            vX = 1.000;
            destroyedBlockCount = 0;
            resetCollideFlags();
            goDownBall = true;

            isGoldStatus = false;
            isExistHeartBlock = false;
            time = 0;
            goldTime = 0;

            blocks.clear();
            bonuses.clear();

            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    //updates the visual representation of the game, handling collisions, and responding to events
    public void onUpdate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                scoreLabel.setText("Score: " + score);
                heartLabel.setText("Heart : " + heart);

                rect.setX(xPaddle);
                rect.setY(yPaddle);
                //Sets the visual position of the ball based on the current values of x and y Ball
                ball.setCenterX(xBall);
                ball.setCenterY(yBall);

                //Updates the Y-coordinate of each bonus object in the bonuses list
                for (Bonus bonus : bonuses) {
                    bonus.bonus.setY(bonus.y);
                }
            }
        });

        //Check if the yBall coordinate is within a certain range, indicating that the ball is within the area where the blocks are present
        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            //Iterates through blocks and checks for collisions between the ball and each block
            for (final Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT) {
                    //A hit is detected
                    score += 1;

                    new Score().show(block.x, block.y, 1, this);

                    //Hides the block
                    block.rect.setVisible(false);
                    //Marks the block as destroyed
                    block.isDestroyed = true;
                    destroyedBlockCount++;
                    //System.out.println("size is " + blocks.size());
                    resetCollideFlags();

                    //Additional reactions based on block type

                    if (block.type == Block.BLOCK_CHOCO) {
                        //Creates a new bonus object bonus
                        final Bonus bonus = new Bonus(block.row, block.column);
                        //Set its creation time
                        bonus.timeCreated = time;
                        //Adds its graphical presentation to the root JavaFX container
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                root.getChildren().add(bonus.bonus);
                            }
                        });
                        //Bonus objects added to the bonuses list
                        bonuses.add(bonus);
                    }

                    if (block.type == Block.BLOCK_STAR) {
                        goldTime = time;
                        //Changes the pattern of the ball
                        ball.setFill(new ImagePattern(new Image("goldball.png")));
                        System.out.println("gold ball");
                        //Adds a style class to the 'root'
                        root.getStyleClass().add("goldRoot");
                        isGoldStatus = true;
                    }

                    if (block.type == Block.BLOCK_HEART) {
                        heart++;
                    }

                    if (hitCode == Block.HIT_RIGHT) {
                        collideToRightBlock = true;
                    } else if (hitCode == Block.HIT_BOTTOM) {
                        collideToBottomBlock = true;
                    } else if (hitCode == Block.HIT_LEFT) {
                        collideToLeftBlock = true;
                    } else if (hitCode == Block.HIT_TOP) {
                        collideToTopBlock = true;
                    }

                }
                //TODO hit to break and some work here...
                //System.out.println("Paddle in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }

    //Initialization callback
    @Override
    public void onInit() {
    }

    //Physics update callback
    @Override
    public void onPhysicsUpdate() {
        //Checks if the player has finished the level
        checkDestroyedCount();
        setPhysicsToBall();

        //Checks if the time elapsed since the last time there was a gold ball is greater than 5s
        if (time - goldTime > 5000) {
            //Returns to default
            ball.setFill(new ImagePattern(new Image("ball.png")));
            root.getStyleClass().remove("goldRoot");
            isGoldStatus = false;
        }

        //Iterates through the bonuses list
        for (Bonus bonus : bonuses) {
            //If the Y-coordinate of the bonus is greater than the sceneHeight or if bonus is taken
            if (bonus.y > sceneHeight || bonus.taken) {
                continue;
            }
            //If the bonus is within the vertical and horizontal range of the paddle
            if (bonus.y >= yPaddle && bonus.y <= yPaddle + paddleHeight && bonus.x >= xPaddle && bonus.x <= xPaddle + paddleWidth) {
                System.out.println("You Got it and +3 score for you");
                bonus.taken = true;
                bonus.bonus.setVisible(false);
                score += 3;
                new Score().show(bonus.x, bonus.y, 3, this);
            }
            //Updates the Y-coordinate of the 'bonus' based on the elapsed time since its creation
            //bonus is "falling"
            bonus.y += ((time - bonus.timeCreated) / 1000.000) + 1.000;
        }

        //System.out.println("time is:" + time + " goldTime is " + goldTime);

    }

    //Time update callback
    //for the class to stay in sync with the overall game time
    @Override
    public void onTime(long time) {
        this.time = time;
    }
}