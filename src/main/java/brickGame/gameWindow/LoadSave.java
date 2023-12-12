//package brickGame.gameWindow;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
////reading the saved state of the game from a file
//public class LoadSave {
//
//    //Save file paths
//    public static String savePath    = "./save/save.mdds";
//    public static String savePathDir = "./save/";
//    public boolean          isExistHeartBlock;
//    public boolean          isGoldStatus;
//    public boolean          goDownBall;
//    public boolean          goRightBall;
//    public boolean          collideToPaddle;
//    public boolean          collideToPaddleAndMoveToRight;
//    public boolean          collideToRightWall;
//    public boolean          collideToLeftWall;
//    public boolean          collideToRightBlock;
//    public boolean          collideToBottomBlock;
//    public boolean          collideToLeftBlock;
//    public boolean          collideToTopBlock;
//    public int              level;
//    public int              score;
//    public int              heart;
//    public int              destroyedBlockCount;
//    public double           xBall;
//    public double           yBall;
//    public double           xPaddle;
//    public double           yPaddle;
//    public double           centerPaddleX;
//    public long             time;
//    public long             goldTime;
//    public double           vX;
//    //stores instances of the serialized objects
//    public ArrayList<Map<String, Integer>> blocks = new ArrayList<Map<String, Integer>>();
//
//    //converts blocks to a map and later reconstructed
//    //TODO add color
//    public Map<String, Integer> BlockSerializable(int row, int column, int type){
//        Map<String, Integer> result = new HashMap<>();
//        result.put("row",row);
//        result.put("column", column);
//        result.put("type", type);
//        return result;
//    }
//
//    public void read() {
//        try {
//            //deserialize data stored in the file
//            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(Main.savePath)));
//
//
//            level = inputStream.readInt();
//            score = inputStream.readInt();
//            heart = inputStream.readInt();
//            destroyedBlockCount = inputStream.readInt();
//
//
//            xBall = inputStream.readDouble();
//            yBall = inputStream.readDouble();
//            xPaddle = inputStream.readDouble();
//            yPaddle = inputStream.readDouble();
//            centerPaddleX = inputStream.readDouble();
//            time = inputStream.readLong();
//            goldTime = inputStream.readLong();
//            vX = inputStream.readDouble();
//
//
//            isExistHeartBlock = inputStream.readBoolean();
//            isGoldStatus = inputStream.readBoolean();
//            goDownBall = inputStream.readBoolean();
//            goRightBall = inputStream.readBoolean();
//            collideToPaddle = inputStream.readBoolean();
//            collideToPaddleAndMoveToRight = inputStream.readBoolean();
//            collideToRightWall = inputStream.readBoolean();
//            collideToLeftWall = inputStream.readBoolean();
//            collideToRightBlock = inputStream.readBoolean();
//            collideToBottomBlock = inputStream.readBoolean();
//            collideToLeftBlock = inputStream.readBoolean();
//            collideToTopBlock = inputStream.readBoolean();
//
//
//            //serialized blocks array read separately
//            try {
//                blocks = (ArrayList<Map<String, Integer>>) inputStream.readObject();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //Save the current game state to a file
//    private void saveGame() {
//        //opens up a new thread to perform the save operation in the bg
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //creates the directory for saving the file
//                new File(savePathDir).mkdirs();
//                //create a file object representing the save file
//                File file = new File(savePath);
//                //Initializing the ObjectOutputStream for writing objects to the file
//                ObjectOutputStream outputStream = null;
//                try {
//                    outputStream = new ObjectOutputStream(new FileOutputStream(file));
//
//                    //write various game-related data to the file
//                    outputStream.writeInt(level);
//                    outputStream.writeInt(score);
//                    outputStream.writeInt(heart);
//                    outputStream.writeInt(destroyedBlockCount);
//
//
//                    outputStream.writeDouble(xBall);
//                    outputStream.writeDouble(yBall);
//                    outputStream.writeDouble(xPaddle);
//                    outputStream.writeDouble(yPaddle);
//                    outputStream.writeDouble(centerPaddleX);
//                    outputStream.writeLong(time);
//                    outputStream.writeLong(goldTime);
//                    outputStream.writeDouble(vX);
//
//
//                    outputStream.writeBoolean(isExistHeartBlock);
//                    outputStream.writeBoolean(isGoldStatus);
//                    outputStream.writeBoolean(goDownBall);
//                    outputStream.writeBoolean(goRightBall);
//                    outputStream.writeBoolean(collideToPaddle);
//                    outputStream.writeBoolean(collideToPaddleAndMoveToRight);
//                    outputStream.writeBoolean(collideToRightWall);
//                    outputStream.writeBoolean(collideToLeftWall);
//                    outputStream.writeBoolean(collideToRightBlock);
//                    outputStream.writeBoolean(collideToBottomBlock);
//                    outputStream.writeBoolean(collideToLeftBlock);
//                    outputStream.writeBoolean(collideToTopBlock);
//
//                    //create a list to store serializable representations of non-destroyed blocks
////                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
//                    ArrayList<Map<String, Integer>> blockSerializables = new ArrayList<Map<String, Integer>>();
//                    for (Block block : blocks) {
//                        //skip destroyed blocks
//                        if (block.isDestroyed) {
//                            continue;
//                        }
//                        //add a serializable representation of the block to the list
//                        //TODO add color
//                        blockSerializables.add(block.BlockSerializable(block.row, block.column, block.type));
//                    }
//
//                    //write the list of block serializables to the file
//                    outputStream.writeObject(blockSerializables);
//
//                    //show a message indicating that the game has been saved
//                    new Score().showMessage("Game Saved", Main.this);
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    //TODO use try-with-resources method
//                    try {
//                        outputStream.flush();
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//
//    //Load the game state from a file
//    private void loadGame() {
//
//        //Create an instance of the LoadSave class
//        brickGame.LoadSave loadSave = new brickGame.LoadSave();
//        //read the saved game data
//        loadSave.read();
//
//        //load the saved data into the current game state
//        isExistHeartBlock = loadSave.isExistHeartBlock;
//        isGoldStatus = loadSave.isGoldStatus;
//        goDownBall = loadSave.goDownBall;
//        goRightBall = loadSave.goRightBall;
//        collideToPaddle = loadSave.collideToPaddle;
//        collideToPaddleAndMoveToRight = loadSave.collideToPaddleAndMoveToRight;
//        collideToRightWall = loadSave.collideToRightWall;
//        collideToLeftWall = loadSave.collideToLeftWall;
//        collideToRightBlock = loadSave.collideToRightBlock;
//        collideToBottomBlock = loadSave.collideToBottomBlock;
//        collideToLeftBlock = loadSave.collideToLeftBlock;
//        collideToTopBlock = loadSave.collideToTopBlock;
//        level = loadSave.level;
//        score = loadSave.score;
//        heart = loadSave.heart;
//        destroyedBlockCount = loadSave.destroyedBlockCount;
//        xBall = loadSave.xBall;
//        yBall = loadSave.yBall;
//        xPaddle = loadSave.xPaddle;
//        yPaddle = loadSave.yPaddle;
//        centerPaddleX = loadSave.centerPaddleX;
//        time = loadSave.time;
//        goldTime = loadSave.goldTime;
//        vX = loadSave.vX;
//
//        //clear existing blocks and bonuses
//        blocks.clear();
//        bonuses.clear();
//
//        //create block objects based on the serialized data
//        for (Map<String, Integer> ser : loadSave.blocks) {
//            //TODO remove color
//            //generate a random number to determine the color
//            int r = new Random().nextInt(200);
//            //create a new block and add it to the blocks list
//            blocks.add(new Block(ser.get("row"), ser.get("column"), colors[r % colors.length], ser.get("type")));
//        }
//
//        try {
//            loadFromSave = true;
//            //start the game with the loaded state
//            start(primaryStage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
