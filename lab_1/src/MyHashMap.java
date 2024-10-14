import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Program {
    public static void main(String[] args) {
        MyHashMap myHashMap = new MyHashMap();
        myHashMap.test();
        // myHashMap.print();
    }
}

public class MyHashMap {
    private MyLinkedList[] entry;

    public MyHashMap() {
        entry = new MyLinkedList[100];
        for (int i = 0; i < 100; i++) {
            entry[i] = new MyLinkedList();
        }
    }

    public void test() {
        handleFile("Ex1.txt");
        int emptyEntries = emptyEntries();
        System.out.println("The number of empty entries in the hash table is: " + emptyEntries);
        int longestChain = longestChain();
        System.out.println("The length of the longest chain is: " + longestChain);
    }

    public void insert(int data) {
        int hashKey = data % 100;
        entry[hashKey].insert(data);
    }

    public int emptyEntries() {
        int empty = 0;
        for (int i = 0; i < 100; i++) {
            if (entry[i].head == null) {
                empty++;
            }
        }
        return empty;
    }

    public int longestChain() {
        int longest = 0;
        for (int i = 0; i < 100; i++) {
            int length = chainLength(entry[i]);
            if (length > longest) {
                longest = length;
            }
        }
        return longest;
    }

    public int chainLength(MyLinkedList list) {
        int length = 0;
        MyLinkedList.Node pointer = list.head;
        while (pointer != null) {
            length++;
            pointer = pointer.next;
        }
        return length;
    }

    public void handleFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                int data = Integer.parseInt(line.trim());
                insert(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for (int i = 0; i < entry.length; i++) {
            entry[i].print();
        }
    }
}

/**
 *
 * @author Hooman
 */

class MyLinkedList {
    class Node {
        Integer data;
        Node next;

        public Node(Integer data) {
            this.data = data;
            this.next = null;
        }
    }

    public Node head = null;

    public boolean insert(Integer data) {
        Node node = new Node(data);
        node.next = null;
        if (head == null)
            head = node;
        else {
            Node last = head;
            while (last.next != null) {
                if (last.data.equals(data))
                    return false;
                last = last.next;
            }
            if (last.data.equals(data))
                return false;
            last.next = node;
        }
        return true;
    }

    public boolean delete(Integer data) {
        if (head == null)
            return false;
        if (head.data.equals(data)) {
            head = head.next;
            return true;
        }
        Node prev = head;
        Node pointer = head.next;
        while (pointer != null) {
            if (pointer.data.equals(data)) {
                prev.next = pointer.next;
                return true;
            }
            prev = pointer;
            pointer = pointer.next;
        }
        return false;
    }

    public Integer search(Integer data) {
        Node pointer = head;
        while (pointer != null) {
            if (pointer.data.equals(data)) {
                return pointer.data;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public void print() {
        Node pointer = head;
        while (pointer != null) {
            System.out.print(pointer.data + " -> ");
            pointer = pointer.next;
        }
        System.out.println();
    }
}
