import ecs.Scene;

public class Window {

    private static Window instance = null;
    private Scene currentScene = null;


    private Window() {}

    public static Window get() {

        if (instance == null) {
            instance = new Window();
        }
        return instance;
    }

    public void start(WinConfig config, Scene scene) {
        currentScene = scene;
        initialize(config);
        mainLoop();
        terminate();
    }

    private void initialize(WinConfig config) {

    }

    private void mainLoop() {

    }

    private void terminate() {

    }
}
