package brickGame.gameWindow;


//game logic
public class GameEngine {

    //updating game state, initializing game, updating physics, tracking time
    private OnAction onAction;
    private int frameDuration = 15;
    //thread for handling game loop and updating the game
    private Thread updateThread;
    //thread for physics calculations
    private Thread physicsThread;
    //flag to indicate whether game has stopped
    //tracks elapsed time in game
    private long time = 0;

    //thread for updating elapsed time
    private Thread timeThread;
    public boolean isStopped = true;

    //sets interface for handling game actions
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * @param fps set fps and we convert it to millisecond
     */
    //TODO check logic
    //converts to duration of each frame in ms
    public void setFps(int fps) {
        this.frameDuration = (int) 1000 / fps;
    }

    //initializes update thread
    private synchronized void Update() {
        updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!updateThread.isInterrupted()) {
                    try {
                        onAction.onUpdate();
                        Thread.sleep(frameDuration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        updateThread.start();
    }

    //calls the onInit method to initialize the game
    //TODO check redundancy
    private void Initialize() {
        onAction.onInit();
    }

    private synchronized void PhysicsCalculation() {
        physicsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!physicsThread.isInterrupted()) {
                    try {
                        onAction.onPhysicsUpdate();
                        Thread.sleep(frameDuration);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        physicsThread.start();
    }

    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            updateThread.stop();
            physicsThread.stop();
            timeThread.stop();
        }
    }

    private void TimeStart() {
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        time++;
                        onAction.onTime(time);
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }
}