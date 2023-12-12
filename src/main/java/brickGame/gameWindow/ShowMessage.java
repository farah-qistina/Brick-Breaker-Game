package brickGame.gameWindow;

import brickGame.GraphicsMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * a class to display messages on the root pane
 */
public class ShowMessage {
    GraphicsMain graphicsMain = new GraphicsMain();

    /**
     * shows the score or -1 to signal heart loss
     * @param x x coordinate of message
     * @param y y coordinate of message
     * @param score score update
     */
    public void show(final double x, final double y, int score) {
        String sign;
        if (score >= 0) {
            sign = "+";
        } else {
            sign = "";
        }
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        //add label to the JavaF application thread
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GraphicsMain.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        label.setScaleX(i);
                        label.setScaleY(i);
                        label.setOpacity((20 - i) / 20.0);
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * shows messages such as game over and level up
     * @param message message to be displayed
     */
    public void showMessage(String message) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GraphicsMain.root.getChildren().add(label);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 21; i++) {
                    try {
                        label.setScaleX(Math.abs(i-10));
                        label.setScaleY(Math.abs(i-10));
                        label.setOpacity((20 - i) / 20.0);
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * displays "Game Over" and the option to restart, restarting the game if prompted to
     */
    public void showGameOver() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //2 game over labels?
                Label label = new Label("Game Over :(");
                label.setTranslateX(200);
                label.setTranslateY(250);
                label.setScaleX(2);
                label.setScaleY(2);

                Button restart = new Button("Restart");
                restart.setTranslateX(220);
                restart.setTranslateY(300);
                restart.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    //restarts the game when the button is clicked
                    public void handle(ActionEvent event) {
                        graphicsMain.restartGame();
                    }
                });
                GraphicsMain.root.getChildren().addAll(label, restart);
            }
        });
    }

    /**
     * shows a win label
     */
    public void showWin() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label("You Win :)");
                label.setTranslateX(200);
                label.setTranslateY(250);
                label.setScaleX(2);
                label.setScaleY(2);

                GraphicsMain.root.getChildren().addAll(label);
            }
        });
    }
}
