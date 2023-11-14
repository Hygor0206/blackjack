module ifc {
    requires javafx.controls;
    requires javafx.fxml;

    opens ifc to javafx.fxml;
    exports ifc;
}
