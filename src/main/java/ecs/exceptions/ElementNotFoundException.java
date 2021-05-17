package ecs.exceptions;

public class ElementNotFoundException extends RuntimeException{

    public ElementNotFoundException(String note) {
        super(note);
    }
}
