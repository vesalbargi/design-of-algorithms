package com.convexhull.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ConvexHullController {
    @FXML
    private Button themeButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField inputFieldX;
    @FXML
    private TextField inputFieldY;
    @FXML
    private RadioButton blindSearch;
    @FXML
    private RadioButton quickHull;
    @FXML
    private RadioButton grahamScan;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private CheckBox showXY;
    @FXML
    private Canvas canvas;

    private static final String CSS_DARK_FILE = "/com/convexhull/css/dark-theme.css";
    private static final String CSS_LIGHT_FILE = "/com/convexhull/css/light-theme.css";
    private static final String MOON_FILE = "/com/convexhull/images/moon.png";
    private static final String SUN_FILE = "/com/convexhull/images/sun.png";

    private ArrayList<Point2D> points = new ArrayList<>();
    private boolean isDarkMode = true;
    private ImageView moonImageView;
    private ImageView sunImageView;
    private GraphicsContext gc;

    @FXML
    private void initialize() {
        initializeToggleGroup();
        initializeCanvas();
        initializeImage();
    }

    private void initializeImage() {
        moonImageView = new ImageView(
                new Image(getClass().getResource(MOON_FILE).toExternalForm()));
        sunImageView = new ImageView(
                new Image(getClass().getResource(SUN_FILE).toExternalForm()));
        themeButton.setGraphic(moonImageView);
    }

    private void initializeCanvas() {
        gc = canvas.getGraphicsContext2D();
        if (isDarkMode) {
            gc.setFill(Color.rgb(60, 60, 60));
        } else {
            gc.setFill(Color.rgb(211, 211, 211));
        }
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void initializeToggleGroup() {
        toggleGroup = new ToggleGroup();
        blindSearch.setToggleGroup(toggleGroup);
        quickHull.setToggleGroup(toggleGroup);
        grahamScan.setToggleGroup(toggleGroup);
        clearButton.setVisible(false);
    }

    private void addPoint(double x, double y) {
        Point2D point = new Point2D(x, y);
        points.add(point);
        clearButton.setVisible(true);
        displayPoints();
    }

    private void displayPoints() {
        canvasClear();
        for (Point2D p : points) {
            drawPoint(p.getX(), p.getY());
            if (showXY.isSelected()) {
                gc.setFill(Color.BLACK);
                gc.fillText("(" + (int) p.getX() + ", " + (int) p.getY() + ")",
                        p.getX() + 10, p.getY() + 10);
            }
        }
    }

    private void canvasClear() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        initializeCanvas();
    }

    private void drawPoint(double x, double y) {
        gc.setFill(Color.RED);
        gc.fillOval(x - 5, y - 5, 10, 10);
    }

    @FXML
    private void handleAddAction() {
        double x = Double.parseDouble(inputFieldX.getText());
        double y = Double.parseDouble(inputFieldY.getText());
        addPoint(x, y);
        inputFieldX.clear();
        inputFieldY.clear();
    }

    @FXML
    private void handleCanvasClick(MouseEvent event) {
        addPoint(event.getX(), event.getY());
    }

    @FXML
    private void handleShowXYAction() {
        displayPoints();
    }

    @FXML
    private void handleAlgorithmSelection() {
        if (blindSearch.isSelected()) {
        } else if (quickHull.isSelected()) {
        } else if (grahamScan.isSelected()) {
        }
    }

    @FXML
    private void handleCanvasClear() {
        points.clear();
        canvasClear();
        clearButton.setVisible(false);
    }

    @FXML
    private void handleThemeToggle() {
        Scene scene = themeButton.getScene();
        if (isDarkMode) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(CSS_LIGHT_FILE).toExternalForm());
            themeButton.setGraphic(sunImageView);
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(CSS_DARK_FILE).toExternalForm());
            themeButton.setGraphic(moonImageView);
        }
        isDarkMode = !isDarkMode;
        initializeCanvas();
        displayPoints();
    }
}
