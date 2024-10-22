package bank;

import java.util.Objects;

import searchstructures.SearchStructure;

public class MyHashMap<K, V> extends SearchStructure {
    private MyLinkedList<K, V>[] entry;

    public MyHashMap() {
        entry = new MyLinkedList[100];
        for (int i = 0; i < 100; i++) {
            entry[i] = new MyLinkedList<K, V>();
        }
    }

    public void put(K key, V value) {
        int hashKey = getHash(key);
        entry[hashKey].insert(key, value);
    }

    public boolean delete(K key) {
        int hashKey = getHash(key);
        return entry[hashKey].delete(key);
    }

    public V get(K key) {
        int hashKey = getHash(key);
        return entry[hashKey].search(key);
    }

    private int getHash(K key) {
        return Math.abs(Objects.hashCode(key)) % entry.length;
    }

    @Override
    public boolean insert(Integer key, Integer data) {
        K k = (K) key;
        V v = (V) data;
        int hashKey = getHash(key);
        return entry[hashKey].insert(k, v);
    }

    @Override
    public boolean delete(Integer key) {
        int hashKey = getHash(key);
        return entry[hashKey].delete((K) key);
    }

    @Override
    public Integer search(Integer key) {
        int hashKey = getHash(key);
        V value = entry[hashKey].search((K) key);
        return (Integer) value;
    }

    @Override
    public void print() {
        for (int i = 0; i < entry.length; i++) {
            System.out.print("Entry " + i + ": ");
            entry[i].print();
        }
    }

    private int getHash(Integer key) {
        return Math.abs(Objects.hashCode(key)) % entry.length;
    }
}

/**
 *
 * @author Hooman
 */

class Item<K, V> {
    public K key;
    public V data;

    public Item() {
        // key = null;
        // data = null;
    }

    public Item(K key, V data) {
        this.key = key;
        this.data = data;
    }

    @Override
    public String toString() {
        return key + " -> " + data + " , ";
    }
}

class MyLinkedList<K, V> {
    class Node {
        Item<K, V> item;
        Node next;

        public Node(K key, V data) {
            this.item = new Item<>(key, data);
            next = null;
            // previous = null;
        }
    }

    public Node head = null;

    public boolean insert(K key, V data) {
        Node node = new Node(key, data);
        if (head == null) {
            head = node;
            return true;
        } else {
            Node last = head;
            while (last.next != null) {
                if (last.item.key.equals(key)) {
                    return false;
                }
                last = last.next;
            }
            if (last.item.key.equals(key)) {
                return false;
            }
            last.next = node;
        }
        return true;
    }

    public boolean delete(K key) {
        if (head == null)
            return false;

        if (head.item.key.equals(key)) {
            head = head.next;
            return true;
        }

        Node prev = head;
        Node pointer = head.next;

        while (pointer != null) {
            if (pointer.item.key.equals(key)) {
                prev.next = pointer.next;
                return true;
            }
            prev = pointer;
            pointer = pointer.next;
        }
        return false;
    }

    public V search(K key) {
        Node pointer = head;
        while (pointer != null) {
            if (pointer.item.key.equals(key)) {
                return pointer.item.data;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public void print() {
        Node pointer = head;
        while (pointer != null) {
            System.out.print(pointer.item);
            pointer = pointer.next;
        }
        System.out.println();
    }
}
