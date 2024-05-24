package com.example.hopital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PriseContactController implements Initializable {
    @FXML
    private Label alertmob;

    @FXML
    private Label alertname1;

    @FXML
    private Button btnback;

    @FXML
    private Button btnconfirm;

    @FXML
    private ComboBox<String> liste_patient;

    @FXML
    private TextField txtfrequencecardiaque;

    @FXML
    private TextField txtfrequencerespiration;

    @FXML
    private TextField txtpoids;

    @FXML
    private TextField txtpressionarterielle;

    @FXML
    private TextField txtsaturationoxygene1;

    @FXML
    private TextField txtsaturationoxygene11;

    @FXML
    private TextField txttaille;

    @FXML
    private TextField txttemperature;
    private DatabaseManager dbManager;
    public PriseContactController() {
        dbManager = DatabaseManager.getInstance();
    }

    private ObservableList<String> allPatients = FXCollections.observableArrayList();

    void loadFonctionData() {
        String query = "SELECT id, prenom FROM patient";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String prenom = rs.getString("prenom");
                allPatients.add(prenom);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFonctionData();

        FilteredList<String> filteredPatients = new FilteredList<>(allPatients, p -> true);

        txtsaturationoxygene1.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPatients.setPredicate(prenom -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return prenom.toLowerCase().contains(lowerCaseFilter);
            });
        });

        liste_patient.setItems(filteredPatients);
    }

}
