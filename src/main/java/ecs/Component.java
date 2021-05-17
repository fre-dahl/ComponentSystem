package ecs;

import ecs.adt.V2OBJ;

public abstract class Component extends V2OBJ {

    private GameObject gameObject;
    private Manager<Component> manager;

    public void attach(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void attach(Manager<Component> manager) {
        this.manager = manager;
    }

    public void free() {

        if (manager != null)

            manager.remove(this);

        gameObject = null;

        manager = null;
    }
}
