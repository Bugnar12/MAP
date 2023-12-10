package collections;

import java.util.Collection;
import java.util.Map;

/*The implementation of a map for the Symbol Table with its specific operations
* */
public interface MyIDictionary<K, V> {
    V lookUp(K key); //search and return element
    K getKey(V value);
    boolean isDefined(K key); //isDefined = check if exists
    void put(K key, V value) throws Exception; //put = add
    void update(K key, V value);
    MyIDictionary<K, V> copy() throws Exception;
    void delete(K key, V value);
    Map<K, V> getContent();
    Collection<V> getAllValues();
}
