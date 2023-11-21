package collections;

import exception.ExistingVariable;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    private Map<K, V> dict;
    public MyDictionary()
    {
        dict = new HashMap<>(); //since Map<>() is abstract, we must use an ADT that inherits from it ~ Map cannot be instantiated
    }

    @Override
    public V lookUp(K key) {
        return dict.get(key);
    }

    @Override
    public K getKey(V value) {
        for(K key : dict.keySet())
            if(dict.get(key) == value)
                return key;
        return null;
    }

    @Override
    public boolean isDefined(K key) {
        if(dict.get(key) == null)
            return false;
        return true;
    }

    @Override
    public void put(K key, V value) throws Exception{
        if(dict.containsKey(key))
            throw new ExistingVariable("Key already exists!");
        dict.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public MyIDictionary<K, V> copy() throws Exception{
        MyDictionary<K, V> clone_dict = new MyDictionary<>(); //raw type
        for(K key : this.dict.keySet()){
            if(clone_dict.isDefined(key))
                throw new ExistingVariable("Key already exists!");
            clone_dict.put(key, this.dict.get(key));
        }
        return clone_dict;
    }

    @Override
    public String toString() {
        return dict.toString();
    }

    @Override
    public void delete(K key, V value)
    {
        dict.remove(key, value);
    }

}
