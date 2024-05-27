package com.example.hopital;

import com.jfoenix.controls.JFXButton;
//import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class dashboardController {
    @FXML
    private ImageView img_attente;

    @FXML
    private ImageView img_consultation;

    @FXML
    private ImageView img_dashboard;

    @FXML
    private ImageView img_medicament;

    @FXML
    private ImageView img_patient;

    @FXML
    private ImageView img_rdv;

    @FXML
    private ImageView img_specialiste;

    @FXML
    private ImageView img_traitement;

    @FXML
    private ImageView img_type_user;

    @FXML
    private ImageView img_user;

    // @FXML
    // private Button dashboard;

    @FXML
    private StackPane principale;

    // @FXML
    // private Button utilisateurs;


    // @FXML
    // private Button dashboard1;

    // @FXML
    // private Button dashboard11;

    // @FXML
    // private Button utilisateurs1;

    // @FXML
    // private Button utilisateurs11;

    // @FXML
    // private Button utilisateurs111;

    // @FXML
    // private Button utilisateurs11111;

    // @FXML
    // private Button utilisateurs111111;

    // @FXML
    // private Button utilisateurs12;

    // @FXML
    // private Button utilisateurs2;
   
    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton dashboard1;

    @FXML
    private JFXButton dashboard11;

    @FXML
    private JFXButton utilisateurs;

    @FXML
    private JFXButton utilisateurs1;

    @FXML
    private JFXButton utilisateurs11;

    @FXML
    private JFXButton utilisateurs11111;

    @FXML
    private JFXButton utilisateurs111111;

    @FXML
    private JFXButton utilisateurs12;

    @FXML
    private JFXButton utilisateurs2;

    // Méthode pour charger la vue dashboard
    @FXML
    private void loadDashboard() {
        loadView("dashnew.fxml");
    }

    // Méthode pour charger la vue utilisateurs
    @FXML
    private void loadUtilisateurs() {
        loadView("utilisateurs.fxml");
    }

    @FXML
    private void loadConsultation() {
        loadView("consultation_new.fxml");
    }

    @FXML
    private void loadMedicament() {
        loadView("medicament.fxml");
    }

    @FXML
    private void loadPatient() {
        loadView("patient.fxml");
    }

    @FXML
    private void loadRdv() {
        loadView("rdv.fxml");
    }

    @FXML
    private void loadSalle_attente() {
        loadView("salle_attente.fxml");
    }

    @FXML
    private void loadTraitement() {
        loadView("traitement.fxml");
    }

    @FXML
    private void loadtype_utilisateur() {
        loadView("type_utilisateur.fxml");
    }
    @FXML
    private void loadtype_specialiste() {
        loadView("specialiste.fxml");
    }


    // Méthode générique pour charger des vues
    private void loadView(String fxmlFile) {
        try {
            // Crée un FXMLLoader pour le fichier FXML spécifié
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            // Charge le fichier FXML et obtient la racine (root) de la vue chargée
            Node view = loader.load();
            // Met à jour le contenu du StackPane principale avec la vue chargée
            principale.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace(); // Gestion des erreurs en cas de problème de chargement du fichier FXML
        }
    }

    public void initializeUserRights(TypeUtilisateur userType) {
        // Pour le tableau de bord
        boolean accessDashboard = userType.isAccesDashboard();
        dashboard.setVisible(accessDashboard);
        img_dashboard.setVisible(accessDashboard);

        // Pour les utilisateurs
        boolean accessUsers = userType.isAccesUtilisateur();
        utilisateurs.setVisible(accessUsers);
        img_user.setVisible(accessUsers);

        // Pour les consultations
        boolean accessConsultation = userType.isAccesConsultation();
        utilisateurs11.setVisible(accessConsultation);
        img_consultation.setVisible(accessConsultation);

        // Pour les médicaments
        // boolean accessMedicament = userType.isAccesMedicament();
        // utilisateurs111.setVisible(accessMedicament);
        // img_medicament.setVisible(accessMedicament);

        // Pour les patients
        boolean accessPatient = userType.isAccesPatient();
        utilisateurs1.setVisible(accessPatient);
        img_patient.setVisible(accessPatient);

        // Pour les rendez-vous
        boolean accessRdv = userType.isAccesRdv();
        utilisateurs111111.setVisible(accessRdv);
        img_rdv.setVisible(accessRdv);

        // Pour la salle d'attente
        boolean accessSalleAttente = userType.isAccesSalleAttente();
        utilisateurs2.setVisible(accessSalleAttente);
        img_attente.setVisible(accessSalleAttente);

        // Pour les traitements
        boolean accessTraitement = userType.isAccesTraitement();
        utilisateurs11111.setVisible(accessTraitement);
        img_traitement.setVisible(accessTraitement);

        // Pour les types d'utilisateurs
        boolean accessTypeUtilisateur = userType.isAccesTypeUtilisateur();
        utilisateurs2.setVisible(accessTypeUtilisateur);  // Note: utilisateurs2 semble utilisé pour deux droits différents?
        img_type_user.setVisible(accessTypeUtilisateur);

        // Pour les spécialistes
        boolean accessSpecialiste = userType.isAccesSpecialiste();
        dashboard1.setVisible(accessSpecialiste);
        img_specialiste.setVisible(accessSpecialiste);
    }


}
