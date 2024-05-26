package com.example.hopital;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientController implements Initializable {
    @FXML
    private TextField adresse_patient;

    @FXML
    private TableColumn<?, ?> adresse_patient_col;

    @FXML
    private Button carnet_patient;

    @FXML
    private TextField email_patient;

    @FXML
    private TableColumn<?, ?> email_patient_col;

    @FXML
    private Button enregistrer_patient;

    @FXML
    private TableColumn<?, ?> id_patient_col;

    @FXML
    private Button imprimer_patient;

    @FXML
    private Button modifier_patient;

    @FXML
    private DatePicker naissance_patient;

    @FXML
    private TableColumn<?, ?> naissance_patient_col;

    @FXML
    private ComboBox<String> nationalite_patient;

    @FXML
    private TableColumn<?, ?> nationalite_patient_col;

    @FXML
    private TextField nom_patient;

    @FXML
    private TableColumn<?, ?> nom_patient_col;

    @FXML
    private TextField prenom_patient;

    @FXML
    private TableColumn<?, ?> prenom_patient_col;

    @FXML
    private Button save_patient;

    @FXML
    private RadioButton sexe_f_patient;

    @FXML
    private RadioButton sexe_m_patient;

    @FXML
    private TableColumn<?, ?> sexe_patient_col;

    @FXML
    private Button supprimer_patient;

    @FXML
    private TableView<Patient> table_patient;

    @FXML
    private TextField telephone_patient;

    @FXML
    private TableColumn<?, ?> telephone_patient_col;

    @FXML
    private ComboBox<?> trier_patient;

    private DatabaseManager dbManager;

    public PatientController() {
        dbManager = DatabaseManager.getInstance();
    }

    //charger les donnees dans le select option
    void loadFonctionData() {
        String query = "SELECT id, libelle FROM nationalite";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                String libelle = rs.getString("libelle");

                nationalite_patient.getItems().add(libelle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void chargerDonnees() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM patient")) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("adresse");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                LocalDate dateNaissance = rs.getDate("naissance").toLocalDate(); // Convertir java.sql.Date en LocalDate
                String nationalite = rs.getString("nationalite");
                String sexe = rs.getString("sexe");

                // Créer un objet Eleve avec les informations récupérées de la base de données
                Patient patient = new Patient(id, nom, prenom,adresse,email,telephone,dateNaissance,nationalite, sexe);

                // Ajouter l'objet Eleve au TableView
                table_patient.getItems().add(patient);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("INSERT INTO patient (nom, prenom, adresse, email, telephone, naissance, nationalite, sexe) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            String nom = nom_patient.getText();
            String prenom = prenom_patient.getText();
            String adresse = adresse_patient.getText();
            String email = email_patient.getText().isEmpty() ? "null" : email_patient.getText();
            String telephone = telephone_patient.getText();
            LocalDate naissance = naissance_patient.getValue();
            String nationalite = nationalite_patient.getValue(); // Assurez-vous que ce ComboBox est configuré correctement pour renvoyer une chaîne
            String sexe = sexe_m_patient.isSelected() ? "M" : "F";

            // Vérification que tous les champs requis sont remplis
            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || naissance == null || nationalite.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs requis.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, adresse);
            pst.setString(4, email);
            pst.setString(5, telephone);
            pst.setDate(6, java.sql.Date.valueOf(naissance));
            pst.setString(7, nationalite);
            pst.setString(8, sexe);

            int result = pst.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patient enregistré avec succès.", ButtonType.OK);
                alert.showAndWait();
                // Vider le TableView avant de charger de nouvelles données
                table_patient.getItems().clear();

                // Charger les nouvelles données
                chargerDonnees();

                nom_patient.setText("");
                prenom_patient.setText("");
                adresse_patient.setText("");
                email_patient.setText("");
                telephone_patient.setText("");
                naissance_patient.setValue(null);
                nationalite_patient.getSelectionModel().clearSelection();
                sexe_m_patient.setSelected(false);
                sexe_f_patient.setSelected(false);
                nom_patient.requestFocus();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement du Patient.", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    private void configurerSelectionTable() {
        table_patient.setOnMouseClicked(event -> {
            Patient patient = (Patient) table_patient.getSelectionModel().getSelectedItem();
            if (patient != null) {


                adresse_patient.setText("");
                email_patient.setText("");
                telephone_patient.setText("");
                naissance_patient.setValue(null);
                nationalite_patient.getSelectionModel().clearSelection();
                sexe_m_patient.setSelected(false);
                sexe_f_patient.setSelected(false);
                // Remplir les champs de texte et autres contrôles avec les informations de l'utilisateur sélectionné
                nom_patient.setText(patient.getNom());

                adresse_patient.setText(patient.getAdresse());
                email_patient.setText(patient.getEmail());
                telephone_patient.setText(patient.getTelephone());
                prenom_patient.setText(patient.getPrenom());
                //nationalite_patient.setText(patient.getNationalite());
                prenom_patient.setText(patient.getPrenom());
                prenom_patient.setText(patient.getPrenom());

                naissance_patient.setValue(patient.getNaissance());
                //fonction_user.setValue(user.getFonction());  // Assurez-vous que fonction_user peut sélectionner la valeur correctement

                // Sélectionner le bon bouton radio pour le sexe
                if (patient.getSexe().equals("M")) {
                    sexe_m_patient.setSelected(true);
                } else if (patient.getSexe().equals("F")) {
                    sexe_f_patient.setSelected(true);
                }
            }
        });
    }

    @FXML
    void updatefonction(ActionEvent event) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(
                     "UPDATE patient SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, naissance = ?, nationalite = ?, sexe = ? WHERE id = ?")) {

            // Obtenez l'utilisateur sélectionné dans le TableView
            Patient patient = (Patient) table_patient.getSelectionModel().getSelectedItem();

            if (patient != null) { // Vérifiez si un utilisateur est sélectionné avant de mettre à jour
                String nom = nom_patient.getText();
                String prenom = prenom_patient.getText();
                String adresse = adresse_patient.getText();
                String email = email_patient.getText();
                String telephone = telephone_patient.getText();
                LocalDate naissance = naissance_patient.getValue();
                String nationalite = nationalite_patient.getValue();  // Assurez-vous que ce ComboBox est configuré pour renvoyer une chaîne
                String sexe = sexe_m_patient.isSelected() ? "M" : "F";

                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, adresse);
                pst.setString(4, email);
                pst.setString(5, telephone);
                pst.setDate(6, java.sql.Date.valueOf(naissance));
                pst.setString(7, nationalite);
                pst.setString(8, sexe);
                pst.setInt(9, patient.getId());  // Assurez-vous que getId() renvoie un int

                int result = pst.executeUpdate();
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Patient modifié avec succès.", ButtonType.OK);
                    alert.showAndWait();
                    // Vider le TableView avant de charger de nouvelles données
                    table_patient.getItems().clear();

                    // Charger les nouvelles données
                    chargerDonnees();

                    // Réinitialiser les champs
                    nom_patient.setText("");
                    prenom_patient.setText("");
                    adresse_patient.setText("");
                    email_patient.setText("");
                    telephone_patient.setText("");
                    naissance_patient.setValue(null);
                    nationalite_patient.getSelectionModel().clearSelection();
                    sexe_m_patient.setSelected(false);
                    sexe_f_patient.setSelected(false);
                    nom_patient.requestFocus();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification du patient.", ButtonType.OK);
                    alert.showAndWait();
                }
            } else {
                // Afficher un message d'avertissement si aucun utilisateur n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un Patient à mettre à jour.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void deletefonction(ActionEvent event) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM patient WHERE id = ?")) {

            Patient patient = (Patient) table_patient.getSelectionModel().getSelectedItem();

            if (patient != null) { // Vérifiez si un élève est sélectionné avant de mettre à jour

                // Définir les paramètres de la déclaration
                pst.setInt(1, patient.getId());
                // Exécuter la requête de suppression
                pst.executeUpdate();

                // Réinitialiser le formulaire
                nom_patient.setText("");
                prenom_patient.setText("");
                adresse_patient.setText("");
                email_patient.setText("");
                telephone_patient.setText("");
                naissance_patient.setValue(null);
                nationalite_patient.getSelectionModel().clearSelection();
                sexe_m_patient.setSelected(false);
                sexe_f_patient.setSelected(false);
                nom_patient.requestFocus();

                // Afficher une alerte d'information sur l'enregistrement réussi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(" Utilisateur");
                alert.setHeaderText("Suppression de l'utilisateur");
                alert.setContentText(" Utilisateur Supprimé avec succès");
                alert.showAndWait();

                // Vider le TableView avant de charger de nouvelles données
                table_patient.getItems().clear();

                // Charger les nouvelles données
                chargerDonnees();


            } else {
                // Affichez un message d'avertissement si aucun élève n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFonctionData();

        id_patient_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom_patient_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_patient_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresse_patient_col.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        email_patient_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephone_patient_col.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        naissance_patient_col.setCellValueFactory(new PropertyValueFactory<>("naissance"));
        nationalite_patient_col.setCellValueFactory(new PropertyValueFactory<>("nationalite"));
        sexe_patient_col.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        chargerDonnees();
        configurerSelectionTable();

        // Création du groupe de boutons radio
        ToggleGroup groupeSexe = new ToggleGroup();

        // Assignation des boutons radio au groupe
        sexe_m_patient.setToggleGroup(groupeSexe);
        sexe_f_patient.setToggleGroup(groupeSexe);
    }
}
