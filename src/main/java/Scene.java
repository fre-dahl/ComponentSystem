import adt.V2OBJ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public abstract class Scene extends V2OBJ {

    private String title;
    private boolean running;
    private boolean initialized;
    private Stack<GameObject> dead;
    private Array<GameObject> gameObjects;
    private ArrayList<Manager<Component>> managers;


    public Scene(String title, int capacity) {

        this.title = title;

        gameObjects = new Array<>(capacity);

        managers = new ArrayList<>();

        dead = new Stack<>();
    }


    public final void initialize() {

        if (!initialized) {

            create();

            start();

            initialized = true;
        }
    }


    private void start() throws IllegalStateException {

        if (!initialized)

            throw new IllegalStateException("Initialize to start");

        if (!running) {

            for (Manager<Component> manager : managers) {

                manager.start();

                gameObjects.iterate(manager::add);
            }
            running = true;
        }
    }

    public void update() {

        while (!dead.empty()) {

            GameObject deadObject = dead.pop();

            deadObject.disposeComponents();

            gameObjects.remove(deadObject);
        }

        handleInput();

        for (Manager<Component> manager : managers) {

            manager.update();
        }
    }

    public void endScene() throws IllegalStateException {

        if (!initialized)

            throw new IllegalStateException("Calling end-scene on uninitialized Scene");

        initialized = false;

        running = false;

        save();

        for (Manager<Component> manager : managers)

            manager.cleanUp();

        managers = null;

        gameObjects = null;

        dead = null;

        cleanUp();
    }

    public final void addGameObject(GameObject object) {

        gameObjects.add(object);

        for (Manager<Component> manager : managers) {

            manager.add(object);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})

    public final void addManager(Manager manager) throws IllegalStateException{

        if (running)

            throw new IllegalStateException("Adding manager at runtime not allowed");

        managers.add(manager);

        Collections.sort(managers);
    }

    public final void destroy(GameObject object) {

        // todo: implement check for: "instance of poolable"

        dead.push(object);
    }

    public void save() {}

    public void handleInput() {}

    public abstract void create();

    public abstract void cleanUp();

}
