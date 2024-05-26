package com.example.hopital;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.IOException;

public class HelloApplication extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connexion_new.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 1381, 980);

        Parent root = FXMLLoader.load(getClass().getResource("dashboard_new.fxml"));
        Scene scene = new Scene(root);

        // Charger le fichier CSS
        scene.getStylesheets().add(getClass().getResource("styleDash.css").toExternalForm());


        // Debut Autre manière d'implémenter

        //Parent root = FXMLLoader.load(getClass().getResource("calendar.fxml"));
        //stage.initStyle(StageStyle.TRANSPARENT);
        //Pour cacher les boutons du haut
        //stage.initStyle(StageStyle.TRANSPARENT);

        // Pour déplacer la fenetre en faisant un cliquer clisser

        // root.setOnMousePressed(new EventHandler<MouseEvent>() {
        //     @Override
        //     public void handle (MouseEvent event) {
        //         xOffset = event.getScreenX();
        //         yOffset = event.getScreenY();
        //     }
        // });
        // root.setOnMouseDragged( new EventHandler<MouseEvent>() {
        //     @Override
        //     public void handle (MouseEvent event) {
        //         stage.setX(event.getScreenX() - xOffset);
        //         stage.setY(event.getScreenY() - yOffset);
        //     }
        // });


        // Scene scene = new Scene(root);

        //Pour met le background en transparent
        scene.setFill(Color.TRANSPARENT);

        // Centrer la fenêtre
        stage.centerOnScreen();

        //Fin Autre manière d'implémenter




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