package bank;

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
        String result = "{";
        boolean firstEntry = true;

        for (MyLinkedList<K, D> list : entry) {
            if (list.head != null) {
                if (!firstEntry) {
                    result += ", ";
                }
                result += list.print();
                firstEntry = false;
            }
        }
        result += "}";
        System.out.println(result);
    }

    private int getHash(K key) {
        return Math.abs(Objects.hashCode(key)) % entry.length;
    }
}

/**
 *
 * @author Hooman
 */

class Item<K, D> {
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

class MyLinkedList<K, D> {
    class Node {
        Item<K, D> item;
        Node next;

        public Node(K key, D data) {
            this.item = new Item<>(key, data);
            next = null;
            // previous = null;
        }
    }

    public Node head = null;

    public boolean insert(K key, D data) {
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

    public D search(K key) {
        Node pointer = head;
        while (pointer != null) {
            if (pointer.item.key.equals(key)) {
                return pointer.item.data;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public String print() {
        String result = "";
        Node pointer = head;
        boolean firstItem = true;
        while (pointer != null) {
            if (!firstItem) {
                result += ", ";
            }
            result += pointer.item.key + "=" + pointer.item.data;
            pointer = pointer.next;
            firstItem = false;
        }
        return result;
    }
}
