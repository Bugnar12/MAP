package collections;

import java.util.HashMap;

public interface ILatchTable {
    int getFreeAddress();
    void put(int key, int value);
    int get(int key);
    boolean containsKey(int key);
    void update(int key, int value);
    void remove(int key);
    void setContent(HashMap<Integer, Integer> newLatchTable);
    HashMap<Integer,Integer> getContent();
}
