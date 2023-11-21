package collections;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{
    private List<T> list;

    public MyList()
    {
        list = new ArrayList<>();
    }
    //The array will be resized if it runs out of space so no need of throwing FullADT exception.
    @Override
    public void add(T e) {
        list.add(e);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public String toString() {
        return list.toString();
    }

}
