package bank;

/**
 *
 * @author Hooman
 */

public abstract class SearchStructure<K, D> {
    abstract public boolean insert(K key, D data);

    abstract public boolean delete(K key);

    abstract public D search(K key);

    abstract public void print();
}
