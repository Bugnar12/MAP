package collections;

import java.util.List;

public interface MyIList<T> {
    void add(T e);

    List<T> getList();

    void clear();
}
