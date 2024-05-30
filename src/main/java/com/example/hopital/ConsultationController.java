package com.example.hopital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultationController implements Initializable {
    @FXML
    private ComboBox<String> acte_medicale;

    @FXML
    private TextArea ant;

    @FXML
    private ComboBox<String> attente;

    @FXML
    private TextArea diagnostic;

    @FXML
    private ImageView image;

    @FXML
    private ImageView image1;

    @FXML
    private TextArea observation;

    @FXML
    private TextArea prescription;

    @FXML
    private DatePicker rdv;

    @FXML
    private Button save;

    private DatabaseManager dbManager;
    private Map<String, Integer> patientMap = new HashMap<>();
    private ObservableList<String> waitingPatients = FXCollections.observableArrayList();

    public ConsultationController() {
        dbManager = DatabaseManager.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadWaitingPatients();
        attente.setItems(waitingPatients);

        save.setOnAction(e -> saveConsultationData());

        acte_medicale.getItems().add("-Selectionner l'acte medicale");
        acte_medicale.getItems().add("radio");
        acte_medicale.getItems().add("prise_de_sang");
        acte_medicale.getItems().add("RAS");
        checkAndDisplayImage(); // Vérifier et afficher l'image au démarrage
    }

    private void loadWaitingPatients() {
        String query = "SELECT p.id, p.nom FROM siege s JOIN patient p ON s.id_patient = p.id WHERE s.occupe = 1";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                waitingPatients.add(nom);
                patientMap.put(nom, id);


            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkAndDisplayImage() {
        String query = "SELECT COUNT(*) FROM siege WHERE occupe = 1";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    image.setVisible(true);
                    image1.setVisible(false);
                } else {
                    image.setVisible(false);
                    image1.setVisible(true);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void saveConsultationData() {
        String insertConsultationQuery = "INSERT INTO consultation (id_patient, id_medecin, antecedent, diagnostic, prescription, observation, acte_medicale, rdv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateSiegeQuery = "UPDATE siege SET occupe = 0 WHERE id_patient = ?";
        String insertRendezVousQuery = "INSERT INTO rendez_vous (heure_rdv, titre_rdv, date_rdv) VALUES (?, ?, ?)";
        String insertExamenSupQuery = "INSERT INTO examen_sup (id_consultation, type_examen, objectif, resultat, diagnostic, date_examen, rdv) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstInsertConsultation = conn.prepareStatement(insertConsultationQuery, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement pstUpdateSiege = conn.prepareStatement(updateSiegeQuery);
             PreparedStatement pstInsertRendezVous = conn.prepareStatement(insertRendezVousQuery);
             PreparedStatement pstInsertExamenSup = conn.prepareStatement(insertExamenSupQuery)) {


            // Récupérer les valeurs des champs
            String selectedPatientName = attente.getValue();
            int selectedPatientId = patientMap.getOrDefault(selectedPatientName, -1);
            int currentUserId = Session.getInstance().getUserId();

            String diagnosticText = diagnostic.getText();
            String prescriptionText = prescription.getText();
            String acteMedicaleText = acte_medicale.getValue();
            String observationText = observation.getText();
            String antText = ant.getText();
            java.sql.Date rdvDate = java.sql.Date.valueOf(rdv.getValue());
            java.sql.Date rdvDate_2 = java.sql.Date.valueOf(rdv.getValue());

            // Définir les valeurs pour la requête d'insertion de consultation
            pstInsertConsultation.setInt(1, selectedPatientId);
            pstInsertConsultation.setInt(2, currentUserId);
            pstInsertConsultation.setString(3, antText);
            pstInsertConsultation.setString(4, diagnosticText);
            pstInsertConsultation.setString(5, prescriptionText);
            pstInsertConsultation.setString(6, observationText);
            pstInsertConsultation.setString(7, acteMedicaleText);
            pstInsertConsultation.setDate(8, rdvDate);

            pstInsertConsultation.executeUpdate();
            System.out.println("Consultation enregistrée avec succès.");

            ResultSet generatedKeys = pstInsertConsultation.getGeneratedKeys();
            int idConsultation = -1;
            if (generatedKeys.next()) {
                idConsultation = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la création de la consultation, aucun ID obtenu.");
            }

            // Définir les valeurs pour la requête de mise à jour du siège
            pstUpdateSiege.setInt(1, selectedPatientId);
            pstUpdateSiege.executeUpdate();
            System.out.println("Statut du siège mis à jour avec succès.");

            // Préparer les valeurs pour la requête d'insertion de rendez-vous
            String timeString = "16:00:00";
            Time time = Time.valueOf(timeString);
            String titreRdv = acteMedicaleText + " - " + selectedPatientName;

            pstInsertRendezVous.setTime(1, time);
            pstInsertRendezVous.setString(2, titreRdv);
            pstInsertRendezVous.setDate(3, rdvDate);

            pstInsertRendezVous.executeUpdate();
            System.out.println("Rendez-vous enregistré avec succès.");

            // Préparer les valeurs pour la requête d'insertion de examen_sup
            pstInsertExamenSup.setInt(1, idConsultation);
            pstInsertExamenSup.setString(2, acteMedicaleText);
            pstInsertExamenSup.setString(3, "Objectif par défaut");  // Remplacez par une valeur réelle si disponible
            pstInsertExamenSup.setString(4, "Résultat par défaut");  // Remplacez par une valeur réelle si disponible
            pstInsertExamenSup.setString(5, "diagnostic par défaut");         // Vous pouvez utiliser une autre valeur si nécessaire
            pstInsertExamenSup.setDate(6, rdvDate);                  // Utiliser la date de rendez-vous pour la date de l'examen
            pstInsertExamenSup.setDate(7, rdvDate_2);                     // Utiliser l'heure du rendez-vous pour l'heure de l'examen

            pstInsertExamenSup.executeUpdate();
            System.out.println("Examen supplémentaire enregistré avec succès.");

            // Vérifier et afficher l'image après mise à jour

            // Vérifier et afficher l'image après mise à jour
            checkAndDisplayImage();


            // Afficher une alerte d'information sur l'enregistrement réussi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(" Consultation");
            alert.setHeaderText("Enregistrement de consultation");
            alert.setContentText(" consultation enregistré avec succès");
            alert.showAndWait();

        } catch (SQLException ex) {
            // Affichez un message d'avertissement si aucun élève n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur");
            alert.showAndWait();
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    @FXML
    private void handleShowConstantes(ActionEvent event) {
        String selectedPatientName = attente.getValue();
        if (selectedPatientName == null || selectedPatientName.isEmpty()) {
            showAlert("Erreur", "Veuillez sélectionner un patient en attente.");
            return;
        }

        int selectedPatientId = patientMap.getOrDefault(selectedPatientName, -1);
        if (selectedPatientId == -1) {
            showAlert("Erreur", "Patient non trouvé.");
            return;
        }

        String query = "SELECT c.taille, c.poids, c.frequence_cardiaque, c.frequence_respiratoire, " +
                "c.pression_arterielle, c.temperature_corporelle, c.saturation_oxygene " +
                "FROM constante c JOIN siege s ON c.id = s.id_constant WHERE s.id_patient = ? AND s.occupe = 1";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, selectedPatientId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    double taille = rs.getDouble("taille");
                    double poids = rs.getDouble("poids");
                    int frequenceCardiaque = rs.getInt("frequence_cardiaque");
                    int frequenceRespiratoire = rs.getInt("frequence_respiratoire");
                    String pressionArterielle = rs.getString("pression_arterielle");
                    double temperatureCorporelle = rs.getDouble("temperature_corporelle");
                    double saturationOxygene = rs.getDouble("saturation_oxygene");

                    // Afficher les constantes dans un popup
                    showConstantesPopup(taille, poids, frequenceCardiaque, frequenceRespiratoire,
                            pressionArterielle, temperatureCorporelle, saturationOxygene);
                } else {
                    showAlert("Erreur", "Aucune constante trouvée pour ce patient.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Erreur", "Erreur lors de la récupération des constantes.");
        }
    }

    private void showConstantesPopup(double taille, double poids, int frequenceCardiaque, int frequenceRespiratoire,
                                     String pressionArterielle, double temperatureCorporelle, double saturationOxygene) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Constantes du patient");
        alert.setHeaderText(null);
        alert.setContentText("Taille : " + taille + " cm\n" +
                "Poids : " + poids + " kg\n" +
                "Fréquence cardiaque : " + frequenceCardiaque + " bpm\n" +
                "Fréquence respiratoire : " + frequenceRespiratoire + " rpm\n" +
                "Pression artérielle : " + pressionArterielle + "\n" +
                "Température corporelle : " + temperatureCorporelle + " °C\n" +
                "Saturation en oxygène : " + saturationOxygene + " %");

        alert.showAndWait();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    @FXML
    private void handleShowExamen(ActionEvent event) {
        String selectedPatientName = attente.getValue();
        if (selectedPatientName == null || selectedPatientName.isEmpty()) {
            showAlert("Erreur", "Veuillez sélectionner un patient en attente.");
            return;
        }

        int selectedPatientId = patientMap.getOrDefault(selectedPatientName, -1);
        if (selectedPatientId == -1) {
            showAlert("Erreur", "Patient non trouvé.");
            return;
        }

        int idConsultation = getIdConsultationByPatientId(selectedPatientId);
        if (idConsultation == -1) {
            showAlert("Erreur", "Aucune consultation trouvée pour ce patient.");
            return;
        }

        String query = "SELECT type_examen, objectif, resultat, diagnostic, date_examen, rdv FROM examen_sup WHERE id_consultation = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, idConsultation);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String typeExamen = rs.getString("type_examen");
                    String objectif = rs.getString("objectif");
                    String resultat = rs.getString("resultat");
                    String diagnostic = rs.getString("diagnostic");
                    Date dateExamen = rs.getDate("date_examen");
                    Date rdv = rs.getDate("rdv");

                    // Afficher les informations de l'examen dans un popup
                    showExamenPopup(typeExamen, objectif, resultat, diagnostic, dateExamen, rdv);
                } else {
                    showAlert("Erreur", "Aucun examen trouvé pour ce patient.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
            showAlert("Erreur", "Erreur lors de la récupération des informations de l'examen.");
        }
    }

    private void showExamenPopup(String typeExamen, String objectif, String resultat, String diagnostic, Date dateExamen, Date rdv) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations sur l'examen");
        alert.setHeaderText(null);
        alert.setContentText("Type d'examen : " + typeExamen + "\n" +
                "Objectif : " + objectif + "\n" +
                "Résultat : " + resultat + "\n" +
                "Diagnostic : " + diagnostic + "\n" +
                "Date de l'examen : " + dateExamen + "\n" +
                "RDV : " + rdv);

        alert.showAndWait();
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
            Logger.getLogger(ConsultationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; // Retourner -1 si aucune consultation n'est trouvée
    }
}
