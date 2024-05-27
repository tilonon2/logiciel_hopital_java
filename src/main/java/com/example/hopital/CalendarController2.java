package com.example.hopital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController2 implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    @FXML
    private VBox bookContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        // List of activities for a given month
        Map<Integer, List<CalendarActivity>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        // Check for leap year
        if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.GRAY);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight / 7) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j + 1) + (7 * i);
                if (calculatedDate > dateOffset) {
                    int currentDate = calculatedDate - dateOffset;
                    if (currentDate <= monthMaxDate) {
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = -(rectangleHeight / 2) * 0.2;
                        date.setTranslateY(textTranslationY);
                        date.setFill(Color.web("#2F3192"));
                        date.setStyle("-fx-font-weight: bold;");

                        // Vérifiez si c'est la date actuelle pour entourer avec un cercle violet
                        if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate) {
                            Circle circle = new Circle(rectangleWidth / 2);
                            circle.setFill(Color.TRANSPARENT);
                            circle.setStroke(Color.web("#2F3192"));
                            circle.setStrokeWidth(2);
                            stackPane.getChildren().add(circle);
                        }

                        stackPane.getChildren().add(date);

                        // Vérifiez s'il y a des activités pour cette date pour ajouter un point rouge
                        List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
                        if (calendarActivities != null && !calendarActivities.isEmpty()) {
                            Circle eventIndicator = new Circle(3, Color.RED);
                            eventIndicator.setTranslateY(rectangleHeight / 2 - 5);
                            stackPane.getChildren().add(eventIndicator);
                        }
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
        updateBooksVertical();
    }

    private void updateBooksVertical() {
        if (bookContainer != null) {
            bookContainer.getChildren().clear();
            String jdbcURL = "jdbc:mysql://localhost:3306/java_hopital";
            String dbUser = "root";
            String dbPassword = "";

            String query = "SELECT heure_rdv, titre_rdv FROM rendez_vous WHERE date_rdv = ?";

            try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setDate(1, java.sql.Date.valueOf(dateFocus.toLocalDate()));

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String heureRdv = resultSet.getString("heure_rdv");
                        String titreRdv = resultSet.getString("titre_rdv");
                        Book book = new Book();
                        book.setImageSrc("/images/calendar/icons8-calendrier-bébé-100.png");
                        book.setAuthor(titreRdv);
                        book.setName(heureRdv);

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                        HBox bookBox = fxmlLoader.load();
                        BookController bookController = fxmlLoader.getController();
                        bookController.setData(book);
                        bookContainer.getChildren().add(bookBox);
                    }
                }

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("bookContainer est null !");
        }
    }

    private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) {
        Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

        // Génération de quelques activités fictives pour le mois en cours
        Random random = new Random();
        int monthMaxDate = dateFocus.getMonth().maxLength();
        for (int i = 1; i <= monthMaxDate; i++) {
            List<CalendarActivity> activities = new ArrayList<>();
            // Ajoutez ici votre logique pour récupérer les activités réelles de la base de données
            // Pour l'instant, je vais générer des activités fictives
            int numberOfActivities = random.nextInt(3); // Maximum 3 activités par jour
            for (int j = 0; j < numberOfActivities; j++) {
                int hour = random.nextInt(24); // Heure aléatoire
                int minute = random.nextInt(60); // Minute aléatoire
                ZonedDateTime activityDateTime = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), i, hour, minute, 0, 0, dateFocus.getZone());
                CalendarActivity activity = new CalendarActivity(activityDateTime, "Titre activité", 123); // Remplacez "Titre activité" par le titre réel de l'activité
                activities.add(activity);
            }
            calendarActivityMap.put(i, activities);
        }

        return calendarActivityMap;
    }
}
