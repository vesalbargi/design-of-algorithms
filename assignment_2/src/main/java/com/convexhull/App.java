package com.convexhull;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    private static final String FXML_FILE = "/com/convexhull/fxml/convex-hull.fxml";
    private static final String CSS_DARK_FILE = "/com/convexhull/css/dark-theme.css";
    private static final String ICON_FILE = "/com/convexhull/images/icon.png";
    private static final String APP_TITLE = "Convex Hull";
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 600;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = createScene();
        configureStage(stage, scene);
        stage.show();
    }

    private Scene createScene() throws IOException {
        Parent root = loadFXML();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(loadCSS());
        return scene;
    }

    private Parent loadFXML() throws IOException {
        return FXMLLoader.load(getClass().getResource(FXML_FILE));
    }

    private String loadCSS() {
        return getClass().getResource(CSS_DARK_FILE).toExternalForm();
    }

    private void configureStage(Stage stage, Scene scene) {
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource(ICON_FILE).toExternalForm()));
    }
}
