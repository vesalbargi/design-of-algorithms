package bank;

import java.util.ArrayList;
import java.util.Objects;

public class MyHashMap<K, D> extends SearchStructure<K, D> {
    private MyLinkedList<K, D>[] entry;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        entry = new MyLinkedList[100];
        for (int i = 0; i < 100; i++) {
            entry[i] = new MyLinkedList<K, D>();
        }
    }

    public boolean put(K key, D data) {
        int hashKey = getHash(key);
        return entry[hashKey].put(key, data);
    }

    @Override
    public boolean insert(K key, D data) {
        int hashKey = getHash(key);
        return entry[hashKey].insert(key, data);
    }

    @Override
    public boolean delete(K key) {
        int hashKey = getHash(key);
        return entry[hashKey].delete(key);
    }

    @Override
    public D search(K key) {
        int hashKey = getHash(key);
        return entry[hashKey].search(key);
    }

    @Override
    public void print() {
        System.out.print("{");
        boolean firstEntry = true;
        for (MyLinkedList<K, D> list : entry) {
            if (list.head != null) {
                if (!firstEntry) {
                    System.out.print(", ");
                }
                list.print();
                firstEntry = false;
            }
        }
        System.out.println("}");
    }

    private int getHash(K key) {
        return Math.abs(Objects.hashCode(key)) % entry.length;
    }

    public ArrayList<D> getValues() {
        ArrayList<D> valuesList = new ArrayList<>();
        for (MyLinkedList<K, D> list : entry) {
            if (list.head != null) {
                valuesList.addAll(list.getValues());
            }
        }
        return valuesList;
    }

    public ArrayList<K> getKeySet() {
        ArrayList<K> valuesList = new ArrayList<>();
        for (MyLinkedList<K, D> list : entry) {
            if (list.head != null) {
                valuesList.addAll(list.getKeySet());
            }
        }
        return valuesList;
    }
}
