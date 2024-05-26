package com.example.hopital;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {
    @FXML
    private ComboBox<?> acte_medicale;

    @FXML
    private TextArea ant;

    @FXML
    private ComboBox<?> attente;

    @FXML
    private TextArea diagnostic;

    @FXML
    private ImageView image;

    @FXML
    private TextArea observation;

    @FXML
    private TextArea prescription;

    @FXML
    private DatePicker rdv;

    @FXML
    private Button save;


    private DatabaseManager dbManager;

    public ConsultationController() {
        dbManager = DatabaseManager.getInstance();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
