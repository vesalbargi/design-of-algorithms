package com.convexhull.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.geometry.Point2D;

public class QuickHull extends Algorithm {
    @Override
    public ArrayList<Point2D> ComputeConvexHull(ArrayList<Point2D> points) {
        ArrayList<Point2D> extrems = quickHull(points);
        sortByAngles(extrems);
        return extrems;
    }

    private ArrayList<Point2D> quickHull(ArrayList<Point2D> points) {
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
