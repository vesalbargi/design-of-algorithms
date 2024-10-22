package bank;

/**
 *
 * @author Hooman
 */

public abstract class SearchStructure<K, D> {
    abstract public boolean insert(K key, D data);

    abstract public boolean delete(K key); // return success

    abstract public D search(K key); // return data

    abstract public void print();
}
