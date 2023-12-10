package collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Heap<K, V> implements IHeap<K, V>{
    private Map<K, V> heap;
    private int freePosition = 1;
    public Heap()
    {
        this.heap = new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this.heap.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.heap.get(key);
    }

    @Override
    public boolean containsKey(int key) {
        return this.heap.containsKey(key);
    }

    @Override
    public void setContent(Map<K, V> newHeap) {
        this.heap.clear();
        this.heap.putAll(newHeap);
    }

    @Override
    public void setFirstFreeAddress() {
        freePosition = freePosition + 1;
    }

    @Override
    public int getFirstFreeAddress() {
        int copyOfFirstPosition = this.freePosition;
        setFirstFreeAddress();
        return copyOfFirstPosition;
    }

    @Override
    public void update(K key, V value) {
        this.heap.replace(key, value);
    }

    @Override
    public Map<K, V> getContent() {
        return this.heap;
    }

    @Override
    public Collection<V> getAllValues() {
        return this.heap.values();
    }

    //toString() concatenates all present keys and values in the heap
    @Override
    public String toString() {
        String heapVals = "";
        for(K key : this.heap.keySet())
            heapVals = heapVals.concat("[" + key + "->" + this.heap.get(key).toString()+"] ");
        return heapVals;
    }
}
