package com.example.hopital;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1381, 980);
        stage.setTitle("MIAGE Bien-Être");
        stage.setScene(scene);
        stage.show();

        // Ajout d'une icône
        // Charger l'icône à partir du dossier de ressources
        stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/images/logo_2.png")));

        // Rendre la fenêtre non redimensionnable
        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}