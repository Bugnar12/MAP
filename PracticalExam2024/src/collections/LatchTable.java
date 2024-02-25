package collections;

import java.util.HashMap;

public class LatchTable implements ILatchTable{
    private HashMap<Integer, Integer> myLatchTable;
    private int freeAddress = 0;

    public LatchTable(HashMap<Integer, Integer> myLatchTable, int freeAddress) {
        this.myLatchTable = myLatchTable;
        this.freeAddress = freeAddress;
    }

    @Override
    public int getFreeAddress() {
        synchronized (this)
        {
            this.freeAddress++;
            return freeAddress;
        }
    }

    @Override
    public void put(int key, int value) {
        synchronized (this)
        {
            this.myLatchTable.put(key, value);
        }
    }

    @Override
    public int get(int key) {
        synchronized (this)
        {
            return this.myLatchTable.get(key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        synchronized (this)
        {
            return this.myLatchTable.containsKey(key);
        }
    }

    @Override
    public void update(int key, int value) {
        synchronized (this)
        {
            this.myLatchTable.put(key,value);
        }
    }

    @Override
    public void remove(int key) {
        synchronized (this)
        {
            this.myLatchTable.remove(key);
        }
    }

    @Override
    public void setContent(HashMap<Integer, Integer> newLatchTable) {
        synchronized (this)
        {
            this.myLatchTable = newLatchTable;
        }
    }

    @Override
    public HashMap<Integer, Integer> getContent() {
        synchronized (this)
        {
            return this.myLatchTable;
        }
    }

    @Override
    public String toString() {
        return "LatchTable{" +
                "myLatchTable=" + myLatchTable +
                ", freeAddress=" + freeAddress +
                '}';
    }
}
