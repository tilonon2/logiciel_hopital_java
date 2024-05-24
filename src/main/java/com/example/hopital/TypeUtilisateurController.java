package com.example.hopital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TypeUtilisateurController implements Initializable {
    private DatabaseManager dbManager;
    @FXML
    private TextField libelle_typeuser;

    @FXML
    private RadioButton utilisateurvue,typeutilisateurvue,consultationvue,medicamentvue,rdvvue,dashboardvue,salleattentevue,traitementvue,patientvue,specialistevue;

    @FXML
    private Button valider_type_user;


    @FXML
    private TableView table_type_user;
    @FXML

    PreparedStatement pst;
    @FXML
    private TableColumn<Object, Object> collibelle;
    @FXML
    private TableColumn<Object, Object> coluser;
    @FXML
    private TableColumn<Object, Object> coltypeuser;
    @FXML
    private TableColumn<Object, Object> colconsult;
    @FXML
    private TableColumn<Object, Object> colmedic;
    @FXML
    private TableColumn<Object, Object> colrdv;
    @FXML
    private TableColumn<Object, Object> coldash;
    @FXML
    private TableColumn<Object, Object> colsalle;
    @FXML
    private TableColumn<Object, Object> coltrait;
    @FXML
    private TableColumn<Object, Object> colpatient;
    @FXML
    private TableColumn<Object, Object> colspe;


    public TypeUtilisateurController() {
        dbManager = DatabaseManager.getInstance(); // Utiliser l'instance Singleton
    }


    @FXML
    void ajouter_type_utilisateur(ActionEvent event) {
        Connection conn = dbManager.getConnection();
        String libelle = libelle_typeuser.getText();

        // Vérifier si le libellé est vide
        if (libelle == null || libelle.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation de la saisie");
            alert.setContentText("Le libellé ne peut pas être vide.");
            alert.showAndWait();
            return; // Stop further execution
        }

        boolean user = utilisateurvue.isSelected();
        boolean type_user = typeutilisateurvue.isSelected();
        boolean consult = consultationvue.isSelected();
        boolean medic = medicamentvue.isSelected();
        boolean rdv = rdvvue.isSelected();
        boolean dashboard = dashboardvue.isSelected();
        boolean attente = salleattentevue.isSelected();
        boolean trait = traitementvue.isSelected();
        boolean patient = patientvue.isSelected();
        boolean specialiste = specialistevue.isSelected();

        try {
            pst = conn.prepareStatement("insert into type_user(libelle, acces_utilisateur, acces_type_utilisateur, acces_consultation, acces_medicament, acces_rdv, acces_dashboard, acces_salle_attente, acces_traitement, acces_patient, acces_specialiste) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, libelle);
            pst.setBoolean(2, user);
            pst.setBoolean(3, type_user);
            pst.setBoolean(4, consult);
            pst.setBoolean(5, medic);
            pst.setBoolean(6, rdv);
            pst.setBoolean(7, dashboard);
            pst.setBoolean(8, attente);
            pst.setBoolean(9, trait);
            pst.setBoolean(10, patient);
            pst.setBoolean(11, specialiste);
            pst.executeUpdate();

            // Afficher une alerte d'information sur l'enregistrement réussi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Type Utilisateur");
            alert.setHeaderText("Enregistrement du Type Utilisateur");
            alert.setContentText("Type Utilisateur enregistré avec succès");
            alert.showAndWait();

            // Réinitialiser le formulaire
            libelle_typeuser.setText("");
            libelle_typeuser.requestFocus();
            utilisateurvue.setSelected(false);
            typeutilisateurvue.setSelected(false);
            consultationvue.setSelected(false);
            medicamentvue.setSelected(false);
            rdvvue.setSelected(false);
            dashboardvue.setSelected(false);
            salleattentevue.setSelected(false);
            traitementvue.setSelected(false);
            patientvue.setSelected(false);
            specialistevue.setSelected(false);

            // Vider le TableView avant de charger de nouvelles données
            table_type_user.getItems().clear();

            // Charger les nouvelles données
            chargerDonnees();
        } catch (SQLException ex) {
            Logger.getLogger(TypeUtilisateurController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void chargerDonnees() {

        try {
            Connection conn = dbManager.getConnection();
            pst = conn.prepareStatement("SELECT * FROM type_user");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String libelle = rs.getString("libelle");
                boolean accesUtilisateur = rs.getBoolean("acces_utilisateur");
                boolean accesTypeUtilisateur = rs.getBoolean("acces_type_utilisateur");
                boolean accesConsultation = rs.getBoolean("acces_consultation");
                boolean accesMedicament = rs.getBoolean("acces_medicament");
                boolean accesRdv = rs.getBoolean("acces_rdv");
                boolean accesDashboard = rs.getBoolean("acces_dashboard");
                boolean accesSalleAttente = rs.getBoolean("acces_salle_attente");
                boolean accesTraitement = rs.getBoolean("acces_traitement");
                boolean accesPatient = rs.getBoolean("acces_patient");
                boolean accesSpecialiste = rs.getBoolean("acces_specialiste");

                // Créer un objet Eleve avec les informations récupérées de la base de données
                TypeUtilisateur type_utilisateur = new TypeUtilisateur(id, libelle, accesUtilisateur, accesTypeUtilisateur,accesConsultation,accesMedicament,accesRdv,accesDashboard,accesSalleAttente,accesTraitement,accesPatient,accesSpecialiste);

                // Ajouter l'objet Eleve au TableView
                table_type_user.getItems().add(type_utilisateur);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    void updatefonction(ActionEvent event) {
        Connection conn = dbManager.getConnection();
        try {
            // Obtenez l'élève sélectionné dans le TableView
            TypeUtilisateur type_utilisateur = (TypeUtilisateur) table_type_user.getSelectionModel().getSelectedItem();

            if (type_utilisateur != null) { // Vérifiez si un élève est sélectionné avant de mettre à jour

                // Écrire la requête de mise à jour
                String sql = "UPDATE type_user SET libelle = ?, acces_utilisateur = ?, acces_type_utilisateur = ?, acces_consultation = ?, acces_medicament = ?, acces_rdv = ?, acces_dashboard = ?, acces_salle_attente = ?, acces_traitement = ?, acces_patient = ?, acces_specialiste = ? WHERE id = ?";


                // Préparer la déclaration
                pst = conn.prepareStatement(sql);

                // Préparation des valeurs booléennes basées sur les états sélectionnés des RadioButtons
                boolean type_user = typeutilisateurvue.isSelected();
                boolean consult = consultationvue.isSelected();
                boolean medic = medicamentvue.isSelected();
                boolean rdv = rdvvue.isSelected();
                boolean dashboard = dashboardvue.isSelected();
                boolean attente = salleattentevue.isSelected();
                boolean trait = traitementvue.isSelected();
                boolean patient = patientvue.isSelected();
                boolean specialiste = specialistevue.isSelected();

// Configurer le PreparedStatement avec ces valeurs
                pst.setString(1, libelle_typeuser.getText());
                pst.setBoolean(2, utilisateurvue.isSelected()); // Ici, je suppose que 'utilisateurvue' est un RadioButton et nous voulons savoir s'il est sélectionné
                pst.setBoolean(3, type_user);
                pst.setBoolean(4, consult);
                pst.setBoolean(5, medic);
                pst.setBoolean(6, rdv);
                pst.setBoolean(7, dashboard);
                pst.setBoolean(8, attente);
                pst.setBoolean(9, trait);
                pst.setBoolean(10, patient);
                pst.setBoolean(11, specialiste);



                pst.setString(12, String.valueOf(type_utilisateur.getId()));

                // Exécuter la requête de mise à jour
                pst.executeUpdate();

                // Réinitialiser le formulaire
                libelle_typeuser.setText("");
                libelle_typeuser.requestFocus();
                utilisateurvue.setSelected(false);
                typeutilisateurvue.setSelected(false);
                consultationvue.setSelected(false);
                medicamentvue.setSelected(false);
                rdvvue.setSelected(false);
                dashboardvue.setSelected(false);
                salleattentevue.setSelected(false);
                traitementvue.setSelected(false);
                patientvue.setSelected(false);
                specialistevue.setSelected(false);

                // Afficher une alerte d'information sur l'enregistrement réussi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Type Utilisateur");
                alert.setHeaderText("Modification du Type Utilisateur");
                alert.setContentText("Type Utilisateur modifié avec succès");
                alert.showAndWait();

                // Vider le TableView avant de charger de nouvelles données
                table_type_user.getItems().clear();

                // Charger les nouvelles données
                chargerDonnees();

            } else {
                // Affichez un message d'avertissement si aucun élève n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un type utilisateur à mettre à jour.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    @FXML
    void deletefonction(ActionEvent event) {
        Connection conn = dbManager.getConnection();
        try {
            // Obtenez l'élève sélectionné dans le TableView
            TypeUtilisateur type_utilisateur = (TypeUtilisateur) table_type_user.getSelectionModel().getSelectedItem();

            if (type_utilisateur != null) { // Vérifiez si un élève est sélectionné avant de supprimer

                // Écrire la requête de suppression
                String sql = "DELETE FROM type_user WHERE id = ?";

                // Préparer la déclaration
                pst = conn.prepareStatement(sql);

                // Définir les paramètres de la déclaration
                pst.setString(1, String.valueOf(type_utilisateur.getId()));

                // Exécuter la requête de suppression
                pst.executeUpdate();

                // Réinitialiser le formulaire
                libelle_typeuser.setText("");
                libelle_typeuser.requestFocus();
                utilisateurvue.setSelected(false);
                typeutilisateurvue.setSelected(false);
                consultationvue.setSelected(false);
                medicamentvue.setSelected(false);
                rdvvue.setSelected(false);
                dashboardvue.setSelected(false);
                salleattentevue.setSelected(false);
                traitementvue.setSelected(false);
                patientvue.setSelected(false);
                specialistevue.setSelected(false);

                // Afficher une alerte d'information sur l'enregistrement réussi
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Type Utilisateur");
                alert.setHeaderText("Suppression du Type Utilisateur");
                alert.setContentText("Type Utilisateur Supprimé avec succès");
                alert.showAndWait();

                // Vider le TableView avant de charger de nouvelles données
                table_type_user.getItems().clear();

                // Charger les nouvelles données
                chargerDonnees();

            } else {
                // Affichez un message d'avertissement si aucun élève n'est sélectionné
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aucune sélection");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner un Type utilisateur à supprimer.");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    // Ajoutez cette méthode à votre code
    private void configurerSelectionTable() {
        table_type_user.setOnMouseClicked(event -> {
            TypeUtilisateur type_user = (TypeUtilisateur) table_type_user.getSelectionModel().getSelectedItem();
            if (type_user != null) {
                // Remplir les champs de texte avec les informations de l'élève sélectionné

                libelle_typeuser.setText(type_user.getLibelle());

                if(type_user.isAccesTypeUtilisateur()){
                    typeutilisateurvue.setSelected(true);
                } else {
                    typeutilisateurvue.setSelected(false);
                }
                if(type_user.isAccesUtilisateur()){
                    utilisateurvue.setSelected(true);
                } else {
                    utilisateurvue.setSelected(false);
                }
                if(type_user.isAccesConsultation()){
                    consultationvue.setSelected(true);
                } else {
                    consultationvue.setSelected(false);
                }
                if(type_user.isAccesMedicament()){
                    medicamentvue.setSelected(true);
                } else {
                    medicamentvue.setSelected(false);
                }
                if(type_user.isAccesRdv()){
                    rdvvue.setSelected(true);
                } else {
                    rdvvue.setSelected(false);
                }
                if(type_user.isAccesDashboard()){
                    dashboardvue.setSelected(true);
                } else {
                    dashboardvue.setSelected(false);
                }
                if(type_user.isAccesSalleAttente()){
                    salleattentevue.setSelected(true);
                } else {
                    salleattentevue.setSelected(false);
                }
                if(type_user.isAccesTraitement()){
                    traitementvue.setSelected(true);
                } else {
                    traitementvue.setSelected(false);
                }
                if(type_user.isAccesPatient()){
                    patientvue.setSelected(true);
                } else {
                    patientvue.setSelected(false);
                }
                if(type_user.isAccesSpecialiste()){
                    specialistevue.setSelected(true);
                } else {
                    specialistevue.setSelected(false);
                }


            }
        });
    }





    @Override
    public void initialize(URL url, ResourceBundle rb){
        Connection conn = dbManager.getConnection();
        // Logique d'initialisation suivant la connexion




        // Associer les colonnes du TableView aux attributs de la classe Eleve
        collibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        coluser.setCellValueFactory(new PropertyValueFactory<>("accesUtilisateur"));
        coltypeuser.setCellValueFactory(new PropertyValueFactory<>("accesTypeUtilisateur"));
        colconsult.setCellValueFactory(new PropertyValueFactory<>("accesConsultation"));
        colmedic.setCellValueFactory(new PropertyValueFactory<>("accesMedicament"));
        colrdv.setCellValueFactory(new PropertyValueFactory<>("accesRdv"));
        coldash.setCellValueFactory(new PropertyValueFactory<>("accesDashboard"));
        colsalle.setCellValueFactory(new PropertyValueFactory<>("accesSalleAttente"));
        coltrait.setCellValueFactory(new PropertyValueFactory<>("accesTraitement"));
        colpatient.setCellValueFactory(new PropertyValueFactory<>("accesPatient"));
        colspe.setCellValueFactory(new PropertyValueFactory<>("accesSpecialiste"));
        chargerDonnees();
        configurerSelectionTable();

    }
}
