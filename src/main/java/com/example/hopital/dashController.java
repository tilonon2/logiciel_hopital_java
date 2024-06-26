package com.example.hopital;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class dashController implements Initializable {
    @FXML
    private HBox cardLayoout;
    @FXML
    private VBox bookContainer;
    private List<Book> recentlyAdded;
    private List<Book> booksVertical;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private Label afficher_Nom_Utilisateur;
    @FXML
    private Label afficher_Prenom_Utilisateur;
    @FXML
    private Label afficher_NomEtPrenom_Utilisateur;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniLineChart();

        recentlyAdded = new ArrayList<>(recentlyAdded());
        booksVertical = new ArrayList<>(booksVertical());

        try {
            // HBox
            for (Book value : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                cardLayoout.getChildren().add(cardBox);
            }
            // VBox
            for (Book book : booksVertical) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                HBox bookBox = fxmlLoader.load();
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);
                bookContainer.getChildren().add(bookBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iniLineChart() {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data<>("Konaté Tilonon", 8));
        series.getData().add(new XYChart.Data<>("Diakité Sekou", 12));
        series.getData().add(new XYChart.Data<>("Radji Sad", 10));
        series.getData().add(new XYChart.Data<>("Yeo François", 15));
        series.getData().add(new XYChart.Data<>("Dr Javad", 12));
        series.getData().add(new XYChart.Data<>("Dr Desiré", 8));
        series.getData().add(new XYChart.Data<>("Dr Fofana", 5));
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("fx-background-color: transparent;");
        series.getNode().setStyle("fx-stroke: #2F3192");
    }

    private List<Book> recentlyAdded() {
        List<Book> ls = new ArrayList<>();
        int rdvCount = getTodayRdvCount();
        ls.add(createBook("/images/newpatients.png", "Nouveaux patients", String.valueOf(countNewPatients())));
        ls.add(createBook("/images/rdvprevuscejour.png", "RDV prévus ce jour", String.valueOf(rdvCount)));
        ls.add(createBook("/images/totaldespraticiens.png", "Total des praticiens", "10"));
        ls.add(createBook("/images/totalpatient.png", "Total des patients", String.valueOf(countTotalPatients())));
        ls.add(createBook("/images/nouveaurdv.png", "Nouveau RDV", String.valueOf(countNewRDV())));
        ls.add(createBook("/images/nouvellesconsultations.png", "Nouvelles consultations", "1"));
        ls.add(createBook("/images/consultation-medicale.png", "Nouvelles hospitalisations", "1"));
        ls.add(createBook("/images/nouveausoins.png", "Nouveau soins", "1"));
        ls.add(createBook("/images/consultationencours.png", "Consultation en cours...", "0"));
        ls.add(createBook("/images/hospitalisationsencours.png", "Hospitalisations en cours...", "0"));
        ls.add(createBook("/images/totaldesprescriptions.png", "Total des prescriptions", "5"));
        ls.add(createBook("/images/totaldesconsultations.png", "Total des consultations", "5"));
        ls.add(createBook("/images/totaldessoins.png", "Total des soins", "5"));
        ls.add(createBook("/images/totaldeshospitalisations.png", "Total des hospitalisations", "5"));
        ls.add(createBook("/images/litoccupes.png", "Lits occupés", "1"));
        ls.add(createBook("/images/litlibre.png", "Lits libre", "10"));
        return ls;
    }

    private List<Book> booksVertical() {
        List<Book> ls = new ArrayList<>();
        String jdbcURL = "jdbc:mysql://localhost:3306/java_hopital";
        String dbUser = "root";
        String dbPassword = "";

        String query = "SELECT heure_rdv, titre_rdv FROM rendez_vous WHERE date_rdv = CURDATE()";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String heureRdv = resultSet.getString("heure_rdv");
                String titreRdv = resultSet.getString("titre_rdv");
                ls.add(createBook("/images/calendar/icons8-calendrier-bébé-100.png", titreRdv, heureRdv));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ls;
    }

    // Méthode pour avoir le nombre de rendez-vous prévu en fonction du jour
    private int getTodayRdvCount() {
        int count = 0;
        String jdbcURL = "jdbc:mysql://localhost:3306/java_hopital";
        String dbUser = "root";
        String dbPassword = "";

        String query = "SELECT COUNT(*) AS count FROM rendez_vous WHERE date_rdv = CURDATE()";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // Méthode pour avoir le nombre de nouveaux rendez-vous enregistrés à partir de la date actuelle
    private int countNewRDV() {
        int count = 0;
        String jdbcURL = "jdbc:mysql://localhost:3306/java_hopital";
        String dbUser = "root";
        String dbPassword = "";

        String query = "SELECT COUNT(*) AS total FROM rendez_vous WHERE date_rdv >= CURDATE()";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // Méthode pour avoir le nombre total de patients
    private int countTotalPatients() {
        int count = 0;
        String jdbcURL = "jdbc:mysql://localhost:3306/java_hopital";
        String dbUser = "root";
        String dbPassword = "";

        String query = "SELECT COUNT(*) AS total FROM patient";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // Méthode pour avoir le nombre de nouveaux patients enregistrés à partir de la date actuelle
    private int countNewPatients() {
        int count = 0;
        String jdbcURL = "jdbc:mysql://localhost:3306/java_hopital";
        String dbUser = "root";
        String dbPassword = "";

        // Requête SQL pour compter les patients ajoutés à partir d'aujourd'hui
        String query = "SELECT COUNT(*) AS total FROM patient WHERE DATE(naissance) >= CURDATE()";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    private Book createBook(String imageSrc, String author, String name) {
        Book book = new Book();
        book.setImageSrc(imageSrc);
        book.setAuthor(author);
        book.setName(name);
        return book;
    }
}
