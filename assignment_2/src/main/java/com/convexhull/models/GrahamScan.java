package com.convexhull.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.geometry.Point2D;

public class GrahamScan extends Algorithm {
    @Override
    public ArrayList<Point2D> ComputeConvexHull(ArrayList<Point2D> points) {
        sortByAngles(points);
        ArrayList<Point2D> extrems = grahamScan(points);
        return extrems;
    }

    private ArrayList<Point2D> grahamScan(ArrayList<Point2D> points) {
        MyLinkedList linkedList = new MyLinkedList();
        int size = 0;
        for (Point2D point : points) {
            MyLinkedList.Node node = linkedList.new Node(point);
            if (linkedList.head == null) {
                linkedList.head = node;
                linkedList.tail = node;
            } else {
                linkedList.tail.next = node;
                node.previous = linkedList.tail;
                linkedList.tail = node;
            }
            size++;
        }
        int i = 0;
        MyLinkedList.Node p1 = linkedList.head;
        while (i < size - 1) {
            MyLinkedList.Node p2 = p1.next;
            MyLinkedList.Node p3 = null;
            if (p2.next != null) {
                p3 = p2.next;
            } else {
                break;
            }
            double turn = angleTurn(p1.point, p2.point, p3.point);
            if (turn < 0) {
                p1.next = p3;
                p3.previous = p1;
                p1 = p1.previous;
            } else {
                p1 = p1.next;
                i++;
            }
        }
        ArrayList<Point2D> extrems = new ArrayList<>();
        MyLinkedList.Node current = linkedList.head;
        while (current != null) {
            extrems.add(current.point);
            current = current.next;
        }
        return extrems;
    }

    private double angleTurn(Point2D p1, Point2D p2, Point2D p3) {
        return (double) (p2.getX() - p1.getX()) * (p3.getY() - p1.getY()) -
                (p3.getX() - p1.getX()) * (p2.getY() - p1.getY());
    }

    private void sortByAngles(ArrayList<Point2D> extrems) {
        Collections.sort(extrems, Comparator.comparing(Point2D::getY).reversed().thenComparing(Point2D::getX));
        Point2D pS = extrems.get(0);
        extrems.remove(0);
        Collections.sort(extrems,
                Comparator.comparing(x -> Math.atan2((x.getY() - pS.getY()), (x.getX() - pS.getX()))));
        extrems.add(0, pS);
    }
}
