module com.example.studentgrade {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentgrade to javafx.fxml;
    exports com.example.studentgrade;
}