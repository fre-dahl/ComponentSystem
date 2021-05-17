package ecs.adt;

public abstract class V2OBJ implements ArrayID {

    transient private int arrayID = ArrayID.NONE;


    @Override
    public int arrayID() {
        return arrayID;
    }

    @Override
    public void setArrayID(int id) {
        arrayID = id;
    }
}
