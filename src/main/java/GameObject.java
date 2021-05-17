import adt.V2OBJ;

import java.util.List;

public abstract class GameObject extends V2OBJ {

    private List<Component> components;

    public void addComponent(Component component) {

        component.attach(this);
    }

    public <T extends Component> boolean hasComponent(Class<T> componentClass) {

        for(Component c : components) {

            if (componentClass.isAssignableFrom(c.getClass()))

                return true;
        }
        return false;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {

        for (Component c : components) {

            if (componentClass.isAssignableFrom(c.getClass())) {

                try {
                    return componentClass.cast(c);

                } catch (ClassCastException e) {

                    e.printStackTrace();

                    assert false : "Error: (GameObject) Casting component.";
                }
            }
        }
        return null;
    }

    public void disposeComponents() {

        for (Component c : components)

            c.free();

        components.clear();

    }

    public void disposeComponent(Component component) {

        boolean found = false;

        for (Component c : components) {

            if (c.equals(component)) {

                component.free();

                found = true;
            }
        }
        if (found) components.remove(component);
    }

    public <T extends Component> void disposeComponent(Class<T> componentClass) {

        for (int i = 0; i < components.size(); i++) {

            Component component = components.get(i);

            if (componentClass.isAssignableFrom(component.getClass())) {

                component.free();

                components.remove(i);

                return;
            }
        }
    }

}
