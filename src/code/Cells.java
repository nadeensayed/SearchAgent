package code;

public abstract class Cells implements Cloneable{ //To have a grid of Cells instead of Objects
    public abstract String toString();
    @Override
    public Cells clone() throws CloneNotSupportedException {
        return (Cells)super.clone();
    }
}
