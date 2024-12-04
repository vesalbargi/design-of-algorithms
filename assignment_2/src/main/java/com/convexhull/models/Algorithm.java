package com.convexhull.models;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public abstract class Algorithm {
    abstract public ArrayList<Point2D> ComputeConvexHull(ArrayList<Point2D> points);
}
