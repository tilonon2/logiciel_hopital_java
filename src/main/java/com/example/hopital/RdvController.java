package com.example.hopital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RdvController implements Initializable {
    @FXML
    private ComboBox<String> acte_medicale;

    @FXML
    private ComboBox<String> attente;

    @FXML
    private TextArea diagnostic;

    @FXML
    private ImageView image;

    @FXML
    private ImageView image1;

    @FXML
    private TextArea objeectif;

    @FXML
    private DatePicker rdv;

    @FXML
    private TextArea resultat;

    @FXML
    private Button save;


    private DatabaseManager dbManager;
    private Map<String, Integer> patientMap = new HashMap<>();
    private ObservableList<String> waitingPatients = FXCollections.observableArrayList();

    public RdvController() {
        dbManager = DatabaseManager.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadWaitingPatients();
        attente.setItems(waitingPatients);

        acte_medicale.getItems().add("radio");
        acte_medicale.getItems().add("prise_de_sang");
        acte_medicale.getItems().add("autre");
    }

    private void loadWaitingPatients() {
        String query = "SELECT id, id_patient FROM consultation WHERE acte_medicale IN ('radio', 'prise_de_sang')";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idPatient = rs.getInt("id_patient");
                String patientName = getPatientNameById(idPatient);

                waitingPatients.add(patientName);
                patientMap.put(patientName, id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getPatientNameById(int patientId) {
        String query = "SELECT nom FROM patient WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, patientId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nom");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // Retourner null si aucun nom n'est trouvé
    }

    @FXML
    private void saveExamenSupData() {
        String updateExamenSupQuery = "UPDATE examen_sup SET objectif = ?, resultat = ?, diagnostic = ?, date_examen = ?, rdv = ?, valider = ? WHERE id_consultation = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstUpdateExamenSup = conn.prepareStatement(updateExamenSupQuery)) {

            // Récupérer les valeurs des champs
            String selectedPatientName = attente.getValue();
            int selectedPatientId = patientMap.getOrDefault(selectedPatientName, -1);
            int idConsultation = getIdConsultationByPatientId(selectedPatientId);
            String acteMedicaleText = acte_medicale.getValue();
            String diagnosticText = diagnostic.getText();
            String objectifText = objeectif.getText();
            String resultatText = resultat.getText();
            LocalDate dateExamen = LocalDate.now();
            LocalDate rdvDate = rdv.getValue();
            LocalTime rdvTime = LocalTime.of(8, 0); // 08:00 par défaut

            // Définir les valeurs pour la requête de mise à jour de examen_sup

            pstUpdateExamenSup.setString(1, objectifText);
            pstUpdateExamenSup.setString(2, resultatText);
            pstUpdateExamenSup.setString(3, diagnosticText);
            pstUpdateExamenSup.setDate(4, java.sql.Date.valueOf(dateExamen));
            pstUpdateExamenSup.setDate(5, java.sql.Date.valueOf(rdvDate));
            pstUpdateExamenSup.setBoolean(6, (false));
            pstUpdateExamenSup.setInt(7, (idConsultation));


            pstUpdateExamenSup.executeUpdate();
            System.out.println("Examen supplémentaire mis à jour avec succès.");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Examen");
            alert.setHeaderText("Enregistrement dun examen");
            alert.setContentText(" examen enregistré avec succès");
            alert.showAndWait();


        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getIdConsultationByPatientId(int patientId) {
        String query = "SELECT id FROM consultation WHERE id_patient = ? ORDER BY id DESC LIMIT 1";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, patientId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RdvController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; // Retourner -1 si aucune consultation n'est trouvée
    }

}