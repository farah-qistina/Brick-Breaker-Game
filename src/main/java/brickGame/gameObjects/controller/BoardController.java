package brickGame.gameObjects.controller;

import brickGame.GraphicsMain;
import brickGame.gameObjects.model.ball.BallFactory;
import brickGame.gameObjects.model.ball.BallModel;
import brickGame.gameObjects.model.ball.GoldBall;
import brickGame.gameObjects.model.block.ImpactDirection;
import brickGame.gameObjects.model.board.BoardModel;
import brickGame.gameObjects.model.board.Level;
import brickGame.gameObjects.model.board.LevelFactory;
import brickGame.gameObjects.model.paddle.PaddleModel;
import brickGame.gameWindow.GameBoard;
import brickGame.gameWindow.GameEngine;
import brickGame.gameWindow.ShowMessage;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class BoardController {
    private Random rnd;
    private ArrayList<BlockController> level;
    private final ArrayList<BonusController> bonuses = new ArrayList<BonusController>();
    private LevelFactory levelFactory;
    private BallFactory ballFactory;
    private BoardModel boardModel = new BoardModel();

    private int lvl;

    private long goldTime = 0;

    private boolean isGoldStatus      = false;

    //should the ball move down and right
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;

    //Game engine
    private GameEngine engine;


    //Top of the paddle, 640 units from the top of the game window
    private double yPaddle = 640.0f;

    public BoardController(int lvl) {
        levelFactory = new LevelFactory();
        ballFactory = new BallFactory();

        level = levelFactory.makeLevel(lvl);

        this.lvl = lvl;

        rnd = new Random();

        //generates random x and y coordinates for the ball
        int xBall = rnd.nextInt(GameBoard.getSceneWidth()) + 1;
        //Ensures the ball starts below the blocks ((level + 1) * Block.getHeight() + 15) and above a certain threshold (sceneHeight - 200)
        int yBall = rnd.nextInt(GameBoard.getSceneHeight() - 200) + (lvl + 1) * GameBoard.getSceneHeight() + 15;
        setBall("NORMAL", xBall, yBall);

        setRandVelocity(getBall());

        boardModel.setPaddle(GameBoard.getSceneWidth() + PaddleModel.getHalfPaddleWidth(), yPaddle);
    }

    public void setBall(String ballType, double xBall, double yBall) {
        boardModel.setBall(ballFactory.makeBall(ballType, xBall, yBall));
    }

    public BallController getBall() {
        return boardModel.getBall();
    }

    public PaddleController getPaddle() {
        return PaddleController.getUniquePlayer();
    }

    private void setRandVelocity(BallController ball) {
        double vX, vY;
        do {
            vX = rnd.nextInt(10) - 5;
        } while (vX == 0);
        do {
            vY = -rnd.nextInt(7);
        } while (vY == 0);

        ball.setvX(vX);
        ball.setvY(vY);
    }


    /**
     * Method to find the impact made by the ball if impact made between ball and
     * player, ball change direction if impact made between ball and wall, the
     * amount of bricks decreases
     */

    //Ball physics
    public void findImpacts() {
        BallController.moveY(goDownBall);
        BallController.moveX(goRightBall);

        //Handle collision with walls
        wallImpact();

        //Handle collision with the paddle
        paddleImpact();
    }

    public void paddleImpact() {
        double xBall = getBall().getBallFace().getCenterX();
        double centerPaddleX = getPaddle().getCenterPaddleX();
        switch (getPaddle().findImpact(getBall())) {
            case UP_IMPACT:
                goDownBall = false;
                //Adjust velocity based on the position of the collision on the paddle
                //calculates the position of the ball with respect to the centre of the paddle
                double relation = (xBall - centerPaddleX) / ((double) PaddleModel.getPaddleWidth() / 2);

                //if the ball is near the centre of the paddle
                if (Math.abs(relation) <= 0.3) {
                    getBall().setvX(Math.abs(relation));
                    //if the ball is towards the edges of the paddle
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    getBall().setvX((Math.abs(relation) * 1.5) + (lvl / 3.500));
                    //System.out.println("vX " + vX);
                    //if the ball is at the far edges
                } else {
                    getBall().setvX((Math.abs(relation) * 2) + (lvl / 3.500));
                    //System.out.println("vX " + vX);
                }

                //determines whether the ball should move to the right relative to the centre
                goRightBall = xBall - centerPaddleX > 0;
                //System.out.println("Collide2");
                break;

            case RIGHT_IMPACT:
                goRightBall = false;
                break;

            case LEFT_IMPACT:
                goRightBall = true;

            default:
                return;
        }
    }

    public void blockImpact() {
        double xBall = getBall().getBallFace().getCenterX();
        double yBall = getBall().getBallFace().getCenterY();
        //Check if the yBall coordinate is within a certain range, indicating that the ball is within the area where the blocks are present
        if (yBall >= Level.paddingTop && yBall <= (BlockController.getBlockHeight() * (lvl + 1)) + Level.paddingTop) {
            //Iterates through blocks and checks for collisions between the ball and each block
            for (final BlockController br : getBlocks()) {
                ImpactDirection impactDirection = br.findImpact(getBall());
                if (impactDirection != ImpactDirection.NO_IMPACT) {
                    //A hit is detected
                    setScore(getScore() + 1);
                    GraphicsMain main;
                    new ShowMessage().show(br.getX(), br.getY(), 1);

                    //Hides the block
                    br.getBlockFace().setVisible(false);
                    //Marks the block as destroyed
                    br.setBroken(true);
                    setDestroyedBlockCount(getDestroyedBlockCount() + 1);
                    //System.out.println("size is " + blocks.size());

                    //Additional reactions based on block type

                    if (Objects.equals(br.getName(), "Chocolate Block")) {
                        //Creates a new bonus object bonus
                        final BonusController bonus = new BonusController(br.getX(), br.getY());
                        //Set its creation time
                        bonus.setTimeCreated(GraphicsMain.getTime());
                        //Adds its graphical presentation to the root JavaFX container
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                GraphicsMain.root.getChildren().add(bonus.getBonusFace());
                            }
                        });
                        //Bonus objects added to the bonuses list
                        bonuses.add(bonus);
                    }

                    if (Objects.equals(br.getName(), "Gold Block")) {
                        goldTime = GraphicsMain.getTime();
                        //Changes the pattern of the ball
                        setBall("GOLD", xBall, yBall);
                        System.out.println("gold ball");
                        //Adds a style class to the 'root'
                        GraphicsMain.root.getStyleClass().add("goldRoot");
                        isGoldStatus = true;
                    }

                    if (Objects.equals(br.getName(), "Heart Block")) {
                        setHeart(getHeart() + 1);
                    }

                    if (impactDirection == ImpactDirection.RIGHT_IMPACT) {
                        goRightBall = true;
                    } else if (impactDirection == ImpactDirection.DOWN_IMPACT) {
                        goDownBall = true;
                    } else if (impactDirection == ImpactDirection.LEFT_IMPACT) {
                        goRightBall = false;
                    } else if (impactDirection == ImpactDirection.UP_IMPACT) {
                        goDownBall = false;
                    }
                }
                //System.out.println("Paddle in row:" + block.row + " and column:" + block.column + " hit");
            }
        }
    }

    public void wallImpact() {
        int sceneWidth = GameBoard.getSceneWidth();
        int sceneHeight = GameBoard.getSceneHeight();
        if (getBall().getRight() >= sceneWidth) {
            goRightBall = false;
        } else if (getBall().getLeft() <= 0) {
            goRightBall = true;
        } else if (getBall().getUp() >= 0) {
            goDownBall = true;
        } else if (getBall().getDown() >= sceneHeight) {
            //Handle game over
            if (!isGoldStatus) {
                //TODO gameover
                setHeart(getHeart() - 1);
                new ShowMessage().show((double) sceneWidth / 2, (double) sceneHeight / 2, -1);

                if (getHeart() == 0) {
                    new ShowMessage().showGameOver();
                    engine.stop();
                    return;
                }
            }
            getBall().moveTo(getPaddle().getCenterPaddleX(), getPaddle().getPaddleFace().getY() + BallModel.getBallRadius());
            getPaddle().moveTo((double) sceneWidth / 2 + PaddleModel.getHalfPaddleWidth());
        }
    }

    /**
     * Getter to get the array of brick objects
     *
     * @return An array of brick objects
     */

    public ArrayList<BlockController> getBlocks() {
        return level;
    }

    /**
     * Setter to set the array of brick objects
     *
     * @param blocks An Array of brick objects
     */

    public void setBlocks(ArrayList<BlockController> blocks) {
        boardModel.setBlocks(blocks);
    }

    public ArrayList<BonusController> getBonuses() {
        return bonuses;
    }

    /**
     * Setter to set the score
     *
     * @param score The score integer
     */

    public void setScore(int score) {
        boardModel.setScore(score);

    }

    /**
     * Getter to get the current score
     *
     * @return The score integer
     */

    public int getScore() {
        return boardModel.getScore();
    }
    public void setHeart(int heart) {boardModel.setHeart(heart);}
    public int getHeart() {return boardModel.getHeart();}

    public void setDestroyedBlockCount(int i) {
        boardModel.setDestroyedBlockCount(i);
    }

    public int getDestroyedBlockCount() {
        return boardModel.getDestroyedBlockCount();

    }

    public long getGoldTime() {
        return goldTime;
    }

    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }
    public boolean getGoldStatus() {
        return isGoldStatus;
    }
}
