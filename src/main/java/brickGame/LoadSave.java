package brickGame;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

//reading the saved state of the game from a file
public class LoadSave {
    public boolean          isExistHeartBlock;
    public boolean          isGoldStatus;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          collideToPaddle;
    public boolean          collideToPaddleAndMoveToRight;
    public boolean          collideToRightWall;
    public boolean          collideToLeftWall;
    public boolean          collideToRightBlock;
    public boolean          collideToBottomBlock;
    public boolean          collideToLeftBlock;
    public boolean          collideToTopBlock;
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xPaddle;
    public double           yPaddle;
    public double           centerPaddleX;
    public long             time;
    public long             goldTime;
    public double           vX;
    //stores instances of the serialized objects
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();

    public void read() {
        try {
            //deserialize data stored in the file
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(Main.savePath)));


            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();


            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xPaddle = inputStream.readDouble();
            yPaddle = inputStream.readDouble();
            centerPaddleX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();


            isExistHeartBlock = inputStream.readBoolean();
            isGoldStatus = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            collideToPaddle = inputStream.readBoolean();
            collideToPaddleAndMoveToRight = inputStream.readBoolean();
            collideToRightWall = inputStream.readBoolean();
            collideToLeftWall = inputStream.readBoolean();
            collideToRightBlock = inputStream.readBoolean();
            collideToBottomBlock = inputStream.readBoolean();
            collideToLeftBlock = inputStream.readBoolean();
            collideToTopBlock = inputStream.readBoolean();


            //serialized blocks array read separately
            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
