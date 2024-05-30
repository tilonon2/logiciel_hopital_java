package com.example.hopital;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TraitementController {

    @FXML
    private TableView<Activity> activityTable;

    @FXML
    private TableColumn<Activity, String> typeColumn;

    @FXML
    private TableColumn<Activity, String> descriptionColumn;

    @FXML
    private TableColumn<Activity, LocalDate> dateColumn;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private PieChart pieChart;

    private DatabaseManager dbManager;

    @FXML
    public void initialize() {
        dbManager = DatabaseManager.getInstance();

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Load initial data
        showConsultations(null);
        loadChartData();
    }

    @FXML
    public void showConsultations(ActionEvent event) {
        List<Activity> activities = loadActivities("consultation");
        activityTable.getItems().setAll(activities);
        loadChartData();
    }

    @FXML
    public void showConstantes(ActionEvent event) {
        List<Activity> activities = loadActivities("constante");
        activityTable.getItems().setAll(activities);
        loadChartData();
    }

    @FXML
    public void showRendezVous(ActionEvent event) {
        List<Activity> activities = loadActivities("rendez_vous");
        activityTable.getItems().setAll(activities);
        loadChartData();
    }

    @FXML
    public void showPatients(ActionEvent event) {
        List<Activity> activities = loadActivities("patient");
        activityTable.getItems().setAll(activities);
        loadChartData();
    }

    private List<Activity> loadActivities(String tableName) {
        List<Activity> activities = new ArrayList<>();
        String query = "";
        switch (tableName) {
            case "consultation":
                query = "SELECT id, diagnostic AS description, rdv AS date FROM consultation";
                break;
            case "constante":
                query = "SELECT id, CONCAT(taille, ', ', poids, ', ', frequence_cardiaque) AS description, 'N/A' AS date FROM constante";
                break;
            case "rendez_vous":
                query = "SELECT id, titre_rdv AS description, date_rdv AS date FROM rendez_vous";
                break;
            case "patient":
                query = "SELECT id, CONCAT(nom, ' ', prenom) AS description, naissance AS date FROM patient";
                break;
        }

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String type = tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
                String description = rs.getString("description");
                LocalDate date = rs.getDate("date") != null ? rs.getDate("date").toLocalDate() : null;
                activities.add(new Activity(type, description, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }

    private void loadChartData() {
        int consultationsCount = getActivityCount("consultation");
        int constantesCount = getActivityCount("constante");
        int rendezVousCount = getActivityCount("rendez_vous");
        int patientsCount = getActivityCount("patient");

        // Update Bar Chart
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Consultations", consultationsCount));
        series.getData().add(new XYChart.Data<>("Constantes", constantesCount));
        series.getData().add(new XYChart.Data<>("Rendez-vous", rendezVousCount));
        series.getData().add(new XYChart.Data<>("Patients", patientsCount));
        barChart.getData().add(series);

        // Update Pie Chart
        pieChart.getData().clear();
        pieChart.getData().add(new PieChart.Data("Consultations", consultationsCount));
        pieChart.getData().add(new PieChart.Data("Constantes", constantesCount));
        pieChart.getData().add(new PieChart.Data("Rendez-vous", rendezVousCount));
        pieChart.getData().add(new PieChart.Data("Patients", patientsCount));
    }

    private int getActivityCount(String tableName) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM " + tableName;

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
