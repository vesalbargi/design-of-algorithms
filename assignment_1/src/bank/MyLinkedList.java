package bank;

import java.util.ArrayList;

/**
 *
 * @author Hooman
 */

public class MyLinkedList<K, D> extends SearchStructure<K, D> {
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

    public boolean put(K key, D data) {
        Node pointer = head;
        while (pointer != null) {
            if (pointer.item.key.equals(key)) {
                pointer.item.data = data;
                return true;
            }
            pointer = pointer.next;
        }
        Node node = new Node(key, data);
        if (head == null) {
            head = node;
            return true;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = node;
        }
        return true;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void print() {
        Node pointer = head;
        boolean firstItem = true;
        while (pointer != null) {
            if (!firstItem) {
                System.out.print(", ");
            }
            System.out.print(pointer.item.key + "=" + pointer.item.data);
            pointer = pointer.next;
            firstItem = false;
        }
    }

    public ArrayList<D> getValues() {
        ArrayList<D> valuesList = new ArrayList<>();
        Node pointer = head;
        while (pointer != null) {
            valuesList.add(pointer.item.data);
            pointer = pointer.next;
        }
        return valuesList;
    }

    public ArrayList<K> getKeySet() {
        ArrayList<K> valuesList = new ArrayList<>();
        Node pointer = head;
        while (pointer != null) {
            valuesList.add(pointer.item.key);
            pointer = pointer.next;
        }
        return valuesList;
    }
}
