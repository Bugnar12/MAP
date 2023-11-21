package collections;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws Exception;
    void push(T v);
    boolean isEmpty();
    List<T> reverse_stack();

    MyIStack<T> copy();
}
