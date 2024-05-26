package com.example.hopital;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class newDashboardController implements Initializable {

    @FXML
    private Button dashboard;

    @FXML
    private Button dashboard1;

    @FXML
    private Button dashboard11;

    @FXML
    private ImageView img_attente;

    @FXML
    private ImageView img_attente1;

    @FXML
    private ImageView img_consultation;

    @FXML
    private ImageView img_consultation1;

    @FXML
    private ImageView img_dashboard;

    @FXML
    private ImageView img_dashboard1;

    @FXML
    private ImageView img_medicament;

    @FXML
    private ImageView img_medicament1;

    @FXML
    private ImageView img_patient;

    @FXML
    private ImageView img_patient1;

    @FXML
    private ImageView img_rdv;

    @FXML
    private ImageView img_rdv1;

    @FXML
    private ImageView img_specialiste;

    @FXML
    private ImageView img_specialiste1;

    @FXML
    private ImageView img_traitement;

    @FXML
    private ImageView img_traitement1;

    @FXML
    private ImageView img_type_user;

    @FXML
    private ImageView img_type_user1;

    @FXML
    private ImageView img_user;

    @FXML
    private ImageView img_user1;

    @FXML
    private ImageView menu;

    @FXML
    private AnchorPane pane1;

    @FXML
    private VBox pane2;

    @FXML
    private StackPane principale;

    @FXML
    private Button utilisateurs;

    @FXML
    private Button utilisateurs1;

    @FXML
    private Button utilisateurs11;

    @FXML
    private Button utilisateurs111;

    @FXML
    private Button utilisateurs11111;

    @FXML
    private Button utilisateurs111111;

    @FXML
    private Button utilisateurs12;

    @FXML
    private Button utilisateurs2;

    private boolean isPane2Open = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane1.setVisible(false);

        FadeTransition fadetransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadetransition.setFromValue(1);
        fadetransition.setToValue(0);
        fadetransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {
            pane1.setVisible(true);

            FadeTransition fadetransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadetransition1.setFromValue(0);
            fadetransition1.setToValue(0.15);
            fadetransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            if (isPane2Open) {
                translateTransition1.setByX(-600); // Ferme le panneau
            } else {
                translateTransition1.setByX(+600); // Ouvre le panneau
            }
            translateTransition1.play();

            // Inverse l'état de isPane2Open
            isPane2Open = !isPane2Open;
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadetransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadetransition1.setFromValue(0.15);
            fadetransition1.setToValue(0);
            fadetransition1.play();

            fadetransition1.setOnFinished(event1 -> pane1.setVisible(false));

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(-600); // Ferme le panneau
            translateTransition1.play();

            // Réinitialise l'état de isPane2Open
            isPane2Open = false;
        });
    }

    @FXML
    private void loadDashboard() {
        loadView("dash.fxml");
    }

    @FXML
    private void loadUtilisateurs() {
        loadView("utilisateurs.fxml");
    }

    @FXML
    private void loadConsultation() {
        loadView("consultation.fxml");
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

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node view = loader.load();
            principale.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initializeUserRights(TypeUtilisateur userType) {
        boolean accessDashboard = userType.isAccesDashboard();
        dashboard.setVisible(accessDashboard);
        img_dashboard.setVisible(accessDashboard);

        boolean accessUsers = userType.isAccesUtilisateur();
        utilisateurs.setVisible(accessUsers);
        img_user.setVisible(accessUsers);

        boolean accessConsultation = userType.isAccesConsultation();
        utilisateurs11.setVisible(accessConsultation);
        img_consultation.setVisible(accessConsultation);

        boolean accessMedicament = userType.isAccesMedicament();
        utilisateurs111.setVisible(accessMedicament);
        img_medicament.setVisible(accessMedicament);

        boolean accessPatient = userType.isAccesPatient();
        utilisateurs1.setVisible(accessPatient);
        img_patient.setVisible(accessPatient);

        boolean accessRdv = userType.isAccesRdv();
        utilisateurs111111.setVisible(accessRdv);
        img_rdv.setVisible(accessRdv);

        boolean accessSalleAttente = userType.isAccesSalleAttente();
        utilisateurs2.setVisible(accessSalleAttente);
        img_attente.setVisible(accessSalleAttente);

        boolean accessTraitement = userType.isAccesTraitement();
        utilisateurs11111.setVisible(accessTraitement);
        img_traitement.setVisible(accessTraitement);

        boolean accessTypeUtilisateur = userType.isAccesTypeUtilisateur();
        utilisateurs2.setVisible(accessTypeUtilisateur);
        img_type_user.setVisible(accessTypeUtilisateur);

        boolean accessSpecialiste = userType.isAccesSpecialiste();
        dashboard1.setVisible(accessSpecialiste);
        img_specialiste.setVisible(accessSpecialiste);
    }
}
