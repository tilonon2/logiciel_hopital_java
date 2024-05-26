module com.example.hopital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires fontawesomefx;


    opens com.example.hopital to javafx.fxml;
    exports com.example.hopital;
}