module com.example.hopital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hopital to javafx.fxml;
    exports com.example.hopital;
}