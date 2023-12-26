module com.example.test123 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.test123 to javafx.fxml;
    exports com.example.test123;
}