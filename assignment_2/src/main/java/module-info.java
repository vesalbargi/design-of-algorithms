module com.convexhull {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.convexhull to javafx.fxml;
    opens com.convexhull.controllers to javafx.fxml;

    exports com.convexhull;
    exports com.convexhull.controllers;
}
