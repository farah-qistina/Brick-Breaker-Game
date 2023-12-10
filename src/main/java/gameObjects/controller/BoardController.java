package gameObjects.controller;

import brickGame.Block;
import brickGame.Bonus;
import brickGame.Score;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class BoardController {
    //Flags to track ball movement and collisions

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

    //should the ball move down and right
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;



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

//    Random random = new Random();
//    //generates random x and y coordinates for the ball
//    xBall = random.nextInt(sceneWidth) + 1;
//    //Ensures the ball starts below the blocks ((level + 1) * Block.getHeight() + 15) and above a certain threshold (sceneHeight - 200)
//    yBall = random.nextInt(sceneHeight - 200) + (level + 1) * Block.getHeight() + 15;

    //Block coordinates
//    x = (column * width) + paddingLeft;
//    y = (row * height) + paddingTop;

    //Ball physics
    private void setPhysicsToBall() {

//        BallController.move(goDownBall);
//        BallController.move(goRightBall);

        //Handle collision with walls
        //TODO change boundary
        if (xBall >= sceneWidth) {
            resetCollideFlags();
            collideToRightWall = true;
        }

        if (xBall <= 0) {
            resetCollideFlags();
            collideToLeftWall = true;
        }
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
