package bank;

import java.util.HashMap;

/**
 *
 * @author Hooman
 */

class JavaHashMap<K, D> extends SearchStructure<K, D> {
    HashMap<K, D> items = new HashMap<K, D>();

    @Override
    public boolean insert(K key, D data) {
        if (items.get(key) == null) {
            items.put(key, data);
            return true;
        } else
            return false;
    }

    @Override
    public boolean delete(K key) {
        if (items.remove(key) != null)
            return true;
        else
            return false;
    }

    @Override
    public D search(K key) {
        return items.get(key);
    }

    @Override
    public void print() {
        System.out.println(items);
    }
}