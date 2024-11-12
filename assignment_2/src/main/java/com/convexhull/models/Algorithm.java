package com.convexhull.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.geometry.Point2D;

public class Algorithm {
    public ArrayList<Point2D> blindSearch(ArrayList<Point2D> points) {
        ArrayList<Point2D> extrems = findExtrems(points);
        sortByAngles(extrems);
        return extrems;
    }

    private ArrayList<Point2D> findExtrems(ArrayList<Point2D> points) {
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
