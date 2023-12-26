package Domain;

public interface MyIStack<T> {
    T pop();
    void push(T v);

    boolean isEmpty();
    @Override
    String toString();
}
