package com.convexhull.models;

import javafx.geometry.Point2D;

/**
 *
 * @author Hooman
 */

public class MyLinkedList {
    class Node {
        Point2D point;
        Node next;
        Node previous;

        public Node(Point2D point) {
            this.point = point;
            next = null;
            previous = null;
        }
    }

    public Node head = null;
    public Node tail = null;

    public boolean insert(Point2D point) {
        Node node = new Node(point);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.previous = tail;
            tail = node;
        }
        return true;
    }

    public boolean delete(Point2D point) {
        if (head == null) {
            return false;
        }
        if (head.point.equals(point)) {
            head = head.next;
            if (head != null) {
                head.previous = null;
            } else {
                tail = null;
            }
            return true;
        }
        if (tail.point.equals(point)) {
            tail = tail.previous;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
            return true;
        }
        Node pointer = head.next;
        while (pointer != null) {
            if (pointer.point.equals(point)) {
                pointer.previous.next = pointer.next;
                if (pointer.next != null) {
                    pointer.next.previous = pointer.previous;
                }
                return true;
            }
            pointer = pointer.next;
        }
        return false;
    }

    public Point2D search(Point2D point) {
        Node pointer = head;
        while (pointer != null) {
            if (pointer.point.equals(point)) {
                return pointer.point;
            }
            pointer = pointer.next;
        }
        return null;
    }

    public void print() {
        Node pointer = head;
        while (pointer != null) {
            System.out.print(pointer.point);
            pointer = pointer.next;
        }
        System.out.println();
    }
}
