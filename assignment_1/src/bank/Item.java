package bank;

/**
 *
 * @author Hooman
 */

public class Item<K, D> {
    public K key;
    public D data;

    public Item() {
        // key = null;
        // data = null;
    }

    public Item(K key, D data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public String toString() {
        return key + " -> " + data + " , ";
    }
}
