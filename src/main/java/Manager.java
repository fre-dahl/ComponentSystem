import adt.V2OBJ;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings("unchecked")

public abstract class Manager<T extends Component> extends V2OBJ implements Comparable<Manager<Component>>{


    final Class<T> componentClass = ((Class<T>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0]);

    public void add(GameObject object) {

        T component = object.getComponent(componentClass);

        if (component != null) {

            component.attach((Manager<Component>) this);

            add(component);
        }
    }

    public final void remove(GameObject o) {

        T component = o.getComponent(componentClass);

        if (component != null) {

            component.attach((Manager<Component>) null);

            remove(component);
        }
    }

    public abstract void start();

    public abstract void update();

    public abstract void add(T component);

    public abstract void remove(T component);

    public abstract void cleanUp();
}
