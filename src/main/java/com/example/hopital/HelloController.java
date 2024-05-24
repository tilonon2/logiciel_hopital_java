package com.example.hopital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;



public class HelloController implements Initializable {
    @FXML
    private TextField pseudo;
    @FXML
    private TextField mdp;
    private DatabaseManager dbManager;

    // Méthode appelée lors du clic sur le bouton de connexion

    public HelloController() {
        dbManager = DatabaseManager.getInstance();
    }

    @FXML
    private TypeUtilisateur getTypeUtilisateurById(int typeId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM type_user WHERE id = ?")) {
            pst.setInt(1, typeId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new TypeUtilisateur(
                            rs.getInt("id"),
                            rs.getString("libelle"),
                            rs.getBoolean("acces_utilisateur"),
                            rs.getBoolean("acces_type_utilisateur"),
                            rs.getBoolean("acces_consultation"),
                            rs.getBoolean("acces_medicament"),
                            rs.getBoolean("acces_rdv"),
                            rs.getBoolean("acces_dashboard"),
                            rs.getBoolean("acces_salle_attente"),
                            rs.getBoolean("acces_traitement"),
                            rs.getBoolean("acces_patient"),
                            rs.getBoolean("acces_specialiste")
                    );
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }




    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = pseudo.getText();
        String password = mdp.getText();
        if (authenticate(username, password)) {
            int id_recup = retourne_id(username, password);
            TypeUtilisateur userType = getTypeUtilisateurById(id_recup);
            loadDashboard(userType);

        } else {
            // Gérer l'échec de connexion
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur sur les identifiants");
            alert.setHeaderText(null);
            alert.setContentText("Erreur sur les identifiants.");
            alert.showAndWait();
        }
    }
    @FXML
    private int retourne_id(String username, String password) {
        // Vérifier les identifiants de l'utilisateur dans la base de données
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT id, type_user FROM user WHERE nom = ? AND mdp = ?")) {
            pst.setString(1, username); // Utilisez directement les paramètres de la méthode
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("type_user"); // Retourner l'ID du type d'utilisateur
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1; // Retourner -1 si l'authentification échoue ou si aucun utilisateur n'est trouvé
    }

    @FXML
    private boolean authenticate(String username, String password) {
        // Vérifier les identifiants de l'utilisateur dans la base de données
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement("SELECT id, type_user FROM user WHERE nom = ? AND mdp = ?")) {
            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return true; // Si au moins une ligne est trouvée, l'utilisateur est authentifié avec succès
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; // Retourner false si aucun utilisateur correspondant n'est trouvé
    }


    private void loadDashboard(TypeUtilisateur userType) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hopital/dashboard.fxml"));
            Parent root = loader.load();

            dashboardController controller = loader.getController();
            controller.initializeUserRights(userType);

            Stage stage = (Stage) pseudo.getScene().getWindow();  // Utiliser `pseudo` pour obtenir la fenêtre actuelle
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }



}