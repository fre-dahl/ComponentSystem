package adt;

@FunctionalInterface
public interface ArrayIterator<T extends ArrayID> {
    void next(T item);
}
