module com.timeapi.timeapi {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.timeapi.timeapi to javafx.fxml;
    exports com.timeapi.timeapi;
}