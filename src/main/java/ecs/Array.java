package ecs;

import ecs.adt.ArrayID;
import ecs.adt.ArrayIterator;
import ecs.exceptions.DuplicateElementException;
import ecs.exceptions.ElementNotFoundException;
import ecs.exceptions.EmptyCollectionException;

@SuppressWarnings("unchecked")

// auto resizable array specifically for O(1) removal
// requires the generic class to implement ArrayID
// all V2OBJ implement this functionality:
// ecs.Scene, ecs.Component, ecs.GameObject, ecs.Manager

public class Array<T extends ArrayID> {

    T[] items;
    int count = 0;
    int targetCapacity;


    public Array(int targetCapacity) {

        this.targetCapacity = Math.max(1,targetCapacity);

        items = (T[]) new ArrayID[this.targetCapacity];
    }


    public void iterate(final ArrayIterator<T> iterator) {

        for (int i = 0; i < count; i++)

            iterator.next(items[i]);
    }


    public void add(T item) throws IllegalStateException, DuplicateElementException {

        if (item.arrayID() != ArrayID.NONE) {

            if (item.arrayID() >= 0 && item.arrayID() < count){

                if (item.equals(items[item.arrayID()]))

                    throw new DuplicateElementException("Element already in ecs.Array");
            }
            throw new IllegalStateException("Element cannot inhabit more than one ecs.Array");
        }
        if (count == items.length)

            resize(Math.max(8,(int)(count * 1.75f)));

        item.setArrayID(count);

        items[count++] = item;
    }

    public void remove(T item) throws ElementNotFoundException, EmptyCollectionException {

        if (count == 0)

            throw new EmptyCollectionException("Empty array");

        int key = item.arrayID();

        if (key < 0 || key >= count) {

            if (key == ArrayID.NONE)

                throw new ElementNotFoundException("ID = NONE");

            else if (key < ArrayID.NONE)

                throw new ElementNotFoundException("Invalid ID");

            else throw new ElementNotFoundException("ID out of bounds");
        }

        T requestedItem = items[key];

        if (!item.equals(requestedItem))

            throw new ElementNotFoundException("Items does not match");

        requestedItem.setArrayID(ArrayID.NONE);

        int last = count - 1;

        if (key == last) {

            items[last] = null;
        }

        else {

            T lastItem = items[last];

            items[last] = null;

            lastItem.setArrayID(key);

            items[key] = lastItem;
        }

        count--;

        if (isEmpty()) {

            if (targetCapacity < capacity()) {

                items = (T[]) new ArrayID[targetCapacity];

            }
        }
    }

    public boolean contains(T item) {

        int key = item.arrayID();

        if (key < 0 || key >= count)

            return false;

        return item.equals(items[key]);
    }

    public void clear () {

        if (isEmpty()) return;

        for (int i = 0; i < count; i++) {
            items[i].setArrayID(ArrayID.NONE);
            items[i] = null;
        }

        if (targetCapacity < capacity()) {

            items = (T[]) new ArrayID[targetCapacity];
        }
        count = 0;
    }

    private void resize(int size) {

        T[] items = this.items;

        T[] newItems = (T[])new ArrayID[size];

        System.arraycopy(
                items,
                0,
                newItems,
                0,
                Math.min(count, newItems.length));

        this.items = newItems;
    }

    public int size() {

        return count;
    }

    public int capacity() {

        return items.length;
    }

    public float loadFactor() {

        return ((float)count / capacity());
    }

    public boolean isEmpty() {

        return count == 0;
    }

    @Override
    public String toString() {

        return "Size: " + count + " | Capacity: " + capacity() + " | LoadFactor: " + loadFactor();
    }
}
