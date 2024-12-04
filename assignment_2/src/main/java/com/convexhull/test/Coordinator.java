package com.convexhull.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.convexhull.models.Algorithm;
import com.convexhull.models.BlindSearch;
import com.convexhull.models.GrahamScan;
import com.convexhull.models.QuickHull;

import javafx.geometry.Point2D;

/**
 *
 * @author Hooman
 */

public class Coordinator {
    private int[] generateX(int n, int min, int max) {
        int[] x = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = (int) (Math.random() * (double) (max - min) + min);
        }
        return x;
    }

    private int[] generateY(int n, int min, int max) {
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            y[i] = (int) (Math.random() * (double) (max - min) + min);
        }
        return y;
    }

    private ArrayList<Point2D> generatePoint(int n, int[] x, int[] y) {
        ArrayList<Point2D> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Point2D point = new Point2D(x[i], y[i]);
            points.add(point);
        }
        return points;
    }

    private long getTime() {
        return System.nanoTime();
    }

    public void experiment(int n, int maxRep) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"));
        Scanner sc = new Scanner(System.in);
        for (int num = 100; num <= n; num += 50) {
            for (int rep = 0; rep < maxRep; rep++) {
                System.out.println("Testing n= " + num);
                BlindSearch blindSearch = new BlindSearch();
                QuickHull quickHull = new QuickHull();
                GrahamScan grahamScan = new GrahamScan();

                ArrayList<Algorithm> algorithms = new ArrayList<Algorithm>();
                algorithms.add(blindSearch);
                algorithms.add(quickHull);
                algorithms.add(grahamScan);

                if (num > 500) {
                    algorithms.remove(blindSearch);
                }

                writer.write(num + ",");
                int[] x = generateX(num, 10, 10000);
                int[] y = generateY(num, 20, 20000);
                ArrayList<Point2D> points = generatePoint(num, x, y);

                for (Algorithm algorithm : algorithms) {
                    long begin = getTime();
                    for (int i = 0; i < num; i++) {
                        algorithm.ComputeConvexHull(points);
                    }
                    long finish = getTime();
                    writer.write((finish - begin) / num + ",");
                }
                writer.write("\n");
            }
        }
        writer.close();
    }

    public void autoTest() {
        int[] x = generateX(100, 10, 10000);
        int[] y = generateY(100, 20, 20000);
        ArrayList<Point2D> points = generatePoint(100, x, y);

        BlindSearch blindSearch = new BlindSearch();
        QuickHull quickHull = new QuickHull();
        GrahamScan grahamScan = new GrahamScan();

        ArrayList<Point2D> blindResult = blindSearch.ComputeConvexHull(points);
        ArrayList<Point2D> quickResult = quickHull.ComputeConvexHull(points);
        ArrayList<Point2D> grahamResult = grahamScan.ComputeConvexHull(points);

        testAlgorithm("QuickHull", quickResult, blindResult);
        testAlgorithm("GrahamScan", grahamResult, blindResult);
    }

    private void testAlgorithm(String algorithmName, ArrayList<Point2D> actual, ArrayList<Point2D> expected) {
        if (compare(expected, actual)) {
            System.out.println(algorithmName + " passed the test.");
        } else {
            System.out.println(algorithmName + " failed the test.");
        }
    }

    private static boolean compare(ArrayList<Point2D> actual, ArrayList<Point2D> expected) {
        if (expected.size() != actual.size()) {
            return false;
        }
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals(actual.get(i))) {
                return false;
            }
        }
        return true;
    }
}
