package com.convexhull.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.geometry.Point2D;

public class Algorithm {
    public ArrayList<Point2D> blindSearch(ArrayList<Point2D> points) {
        ArrayList<Point2D> extrems = blind(points);
        sortByAngles(extrems);
        return extrems;
    }

    public ArrayList<Point2D> quickHull(ArrayList<Point2D> points) {
        ArrayList<Point2D> extrems = quick(points);
        sortByAngles(extrems);
        return extrems;
    }

    public ArrayList<Point2D> grahamScan(ArrayList<Point2D> points) {
        sortByAngles(points);
        ArrayList<Point2D> extrems = graham(points);
        return extrems;
    }

    private ArrayList<Point2D> graham(ArrayList<Point2D> points) {
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

    private ArrayList<Point2D> quick(ArrayList<Point2D> points) {
        ArrayList<Point2D> extrems = new ArrayList<>();
        Point2D p1 = points.get(0);
        Point2D p2 = points.get(0);
        for (Point2D point : points) {
            if (point.getX() < p1.getX()) {
                p1 = point;
            }
            if (point.getX() > p2.getX()) {
                p2 = point;
            }
        }
        hull(points, p1, p2, 1, extrems);
        hull(points, p1, p2, -1, extrems);
        return extrems;
    }

    private void hull(ArrayList<Point2D> points, Point2D p1, Point2D p2, int side, ArrayList<Point2D> extrems) {
        int ind = -1;
        double maxDist = 0;
        for (int i = 0; i < points.size(); i++) {
            Point2D point = points.get(i);
            double temp = lineDist(p1, p2, point);
            if (findSide(p1, p2, point) == side && temp > maxDist) {
                ind = i;
                maxDist = temp;
            }
        }
        if (ind == -1) {
            if (!isPointInList(extrems, p1)) {
                extrems.add(p1);
            }
            if (!isPointInList(extrems, p2)) {
                extrems.add(p2);
            }
            return;
        }
        Point2D maxPoint = points.get(ind);
        hull(points, maxPoint, p1, -findSide(maxPoint, p1, p2), extrems);
        hull(points, maxPoint, p2, -findSide(maxPoint, p2, p1), extrems);
    }

    private double lineDist(Point2D p1, Point2D p2, Point2D p) {
        return Math.abs(angleTurn(p1, p2, p));
    }

    private int findSide(Point2D p1, Point2D p2, Point2D p) {
        double turn = angleTurn(p1, p2, p);
        if (turn > 0) {
            return 1;
        } else if (turn < 0) {
            return -1;
        }
        return 0;
    }

    private boolean isPointInList(ArrayList<Point2D> list, Point2D point) {
        for (Point2D p : list) {
            if (p.equals(point)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Point2D> blind(ArrayList<Point2D> points) {
        ArrayList<Point2D> extrems = new ArrayList<Point2D>();
        for (Point2D p : points) {
            boolean inside = false;
            for (int i = 0; i < points.size() && inside == false; i++) {
                for (int j = 0; j < points.size() && inside == false; j++) {
                    for (int k = 0; k < points.size(); k++) {
                        Point2D p1 = points.get(i);
                        Point2D p2 = points.get(j);
                        Point2D p3 = points.get(k);
                        if ((angleTurn(p1, p2, p) < 0) && (angleTurn(p2, p3, p) < 0)
                                && (angleTurn(p3, p1, p) < 0)) {
                            inside = true;
                            break;
                        }
                    }
                }
            }
            if (inside == false) {
                extrems.add(p);
            }
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
