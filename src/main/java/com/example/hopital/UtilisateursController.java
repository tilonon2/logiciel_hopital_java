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

public class UtilisateursController implements Initializable {
    @FXML
    private TableView tableau_user;
    @FXML
    private ToggleGroup groupeSexe;
    @FXML
    private DatePicker date_naissance_user;

    @FXML
    private TableColumn<?, ?> col_fonction;

    @FXML
    private TableColumn<?, ?> col_naissance;

    @FXML
    private TableColumn<?, ?> col_nom;

    @FXML
    private TableColumn<?, ?> col_prenom;

    @FXML
    private TableColumn<?, ?> col_sexe;

    @FXML
    private ChoiceBox<TypeUtilisateur> fonction_user;

    @FXML
    private ChoiceBox<String> grade_user;


    @FXML
    private TextField nom_user;

    @FXML
    private TextField mdp_user;
    @FXML
    private TextField contact_user;

    @FXML
    private TextField specialite_user;

    @FXML
    private TextField prenom_user;

    @FXML
    private RadioButton sexe_f_user;

    @FXML
    private RadioButton sexe_m_user;

    private DatabaseManager dbManager;

    public UtilisateursController() {
        dbManager = DatabaseManager.getInstance();
    }

    void loadFonctionData() {
        String query = "SELECT id, libelle FROM type_user";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String libelle = rs.getString("libelle");
                TypeUtilisateur typeUtilisateur = new TypeUtilisateur(id, libelle);
                fonction_user.getItems().add(typeUtilisateur);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    void updatefonction(ActionEvent event) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("UPDATE user SET nom = ?, prenom = ?,sexe = ?, date_naissance = ?, fonction = ?, mdp = ?, type_user = ?,contact = ?,specialite = ?,grade = ? WHERE id = ?")) {

            // Obtenez l'élève sélectionné dans le TableView
            Utilisateur user = (Utilisateur) tableau_user.getSelectionModel().getSelectedItem();

            if (user != null) { // Vérifiez si un élève est sélectionné avant de mettre à jour
                String nom = nom_user.getText();
                String mdp = mdp_user.getText();
                String prenom = prenom_user.getText();
                String sexe = sexe_m_user.isSelected() ? "M" : "F";
                LocalDate dateNaissance = date_naissance_user.getValue();
                TypeUtilisateur selectedType = fonction_user.getValue();

                String contact = contact_user.getText();
                String specialite = specialite_user.getText();

                String grade = grade_user.getValue();

                pst.setString(1, nom);
                pst.setString(2, prenom);
                pst.setString(3, sexe);
                pst.setDate(4, java.sql.Date.valueOf(dateNaissance));
                pst.setString(5, selectedType.getLibelle());
                pst.setString(6, mdp);
                pst.setInt(7, selectedType.getId());
                pst.setString(8, contact);
                pst.setString(9, specialite);
                pst.setString(10, grade);
                pst.setString(11, String.valueOf(user.getId()));



                int result = pst.executeUpdate();
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Utilisateur modifié avec succès.", ButtonType.OK);
                    alert.showAndWait();
                    // Vider le TableView avant de charger de nouvelles données
                    tableau_user.getItems().clear();

                    // Charger les nouvelles données
                    chargerDonnees();

                    nom_user.setText("");
                    prenom_user.setText("");
                    mdp_user.setText("");
                    nom_user.requestFocus();
                    sexe_f_user.setSelected(false);
                    sexe_m_user.setSelected(false);
                    contact_user.setText("");
                    specialite_user.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification de l'utilisateur.", ButtonType.OK);
                    alert.showAndWait();
                }
            }else {
                // Affichez un message d'avertissement si aucun élève n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un utilisateur à mettre à jour.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    @FXML
    void deletefonction(ActionEvent event) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM user WHERE id = ?")) {

            Utilisateur user = (Utilisateur) tableau_user.getSelectionModel().getSelectedItem();

            if (user != null) { // Vérifiez si un élève est sélectionné avant de mettre à jour

                // Définir les paramètres de la déclaration
                pst.setInt(1, user.getId());
                // Exécuter la requête de suppression
                pst.executeUpdate();

                // Réinitialiser le formulaire
                nom_user.setText("");
                contact_user.setText("");
                specialite_user.setText("");
                prenom_user.setText("");
                mdp_user.setText("");
                nom_user.requestFocus();
                sexe_f_user.setSelected(false);
                sexe_m_user.setSelected(false);

                // Afficher une alerte d'information sur l'enregistrement réussi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(" Utilisateur");
                alert.setHeaderText("Suppression de l'utilisateur");
                alert.setContentText(" Utilisateur Supprimé avec succès");
                alert.showAndWait();

                // Vider le TableView avant de charger de nouvelles données
                tableau_user.getItems().clear();

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




    private void chargerDonnees() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM user")) {
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String sexe = rs.getString("sexe");
                LocalDate dateNaissance = rs.getDate("date_naissance").toLocalDate(); // Convertir java.sql.Date en LocalDate
                String fonction = rs.getString("fonction");
                String mdp = rs.getString("mdp");
                int typeUser = rs.getInt("type_user");

                String contact = rs.getString("contact");
                String specialite = rs.getString("specialite");
                String grade = rs.getString("grade");



                // Créer un objet Eleve avec les informations récupérées de la base de données
                Utilisateur user = new Utilisateur(id, nom, prenom, sexe,dateNaissance,fonction,mdp,typeUser,contact,specialite,grade);

                // Ajouter l'objet Eleve au TableView
                tableau_user.getItems().add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    void loadFonctionData2() {
        String query = "SELECT id, libelle FROM grade";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                String libelle = rs.getString("libelle");

                grade_user.getItems().add(libelle);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("INSERT INTO user (nom, prenom, sexe, date_naissance, fonction, mdp, type_user, contact, specialite, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            String nom = nom_user.getText();
            String mdp = mdp_user.getText();
            String prenom = prenom_user.getText();
            String sexe = sexe_m_user.isSelected() ? "M" : "F";
            LocalDate dateNaissance = date_naissance_user.getValue();
            TypeUtilisateur selectedType = fonction_user.getValue();
            String contact = contact_user.getText();
            String specialite = specialite_user.getText();
            String grade = grade_user.getValue();

            if (nom.isEmpty() || prenom.isEmpty() || dateNaissance == null || selectedType == null || contact == null || specialite == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs requis.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            pst.setString(1, nom);
            pst.setString(2, prenom);
            pst.setString(3, sexe);
            pst.setDate(4, java.sql.Date.valueOf(dateNaissance));
            pst.setString(5, selectedType.getLibelle());
            pst.setString(6, mdp);
            pst.setInt(7, selectedType.getId());
            pst.setString(8, contact);
            pst.setString(9, specialite);
            pst.setString(10, grade);

            int result = pst.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Utilisateur enregistré avec succès.", ButtonType.OK);
                alert.showAndWait();
                // Vider le TableView avant de charger de nouvelles données
                tableau_user.getItems().clear();

                // Charger les nouvelles données
                chargerDonnees();

                nom_user.setText("");
                prenom_user.setText("");
                mdp_user.setText("");
                nom_user.requestFocus();
                sexe_f_user.setSelected(false);
                sexe_m_user.setSelected(false);
                contact_user.setText("");
                specialite_user.setText("");


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'enregistrement de l'utilisateur.", ButtonType.OK);
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void configurerSelectionTable() {
        tableau_user.setOnMouseClicked(event -> {
            Utilisateur user = (Utilisateur) tableau_user.getSelectionModel().getSelectedItem();
            if (user != null) {
                // Remplir les champs de texte et autres contrôles avec les informations de l'utilisateur sélectionné
                nom_user.setText(user.getNom());
                prenom_user.setText(user.getPrenom());
                //mdp_user.setText(user.getMdp());
                date_naissance_user.setValue(user.getDateNaissance());

                contact_user.setText(user.getContact());
                specialite_user.setText(user.getSpecialite());

                //fonction_user.setValue(user.getFonction());  // Assurez-vous que fonction_user peut sélectionner la valeur correctement

                // Sélectionner le bon bouton radio pour le sexe
                if (user.getSexe().equals("M")) {
                    sexe_m_user.setSelected(true);
                } else if (user.getSexe().equals("F")) {
                    sexe_f_user.setSelected(true);
                }
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFonctionData();
        loadFonctionData2();

        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        col_naissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        col_fonction.setCellValueFactory(new PropertyValueFactory<>("fonction"));
        chargerDonnees();

        configurerSelectionTable();

        // Création du groupe de boutons radio
        groupeSexe = new ToggleGroup();

        // Assignation des boutons radio au groupe
        sexe_m_user.setToggleGroup(groupeSexe);
        sexe_f_user.setToggleGroup(groupeSexe);
    }
}
