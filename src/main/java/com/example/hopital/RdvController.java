package com.example.hopital;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;

public class RdvController implements Initializable {
    private LocalDate currentDate;
    private GridPane calendarGrid;
    private Label monthLabel;


    public void initialize(URL url, ResourceBundle rb){
        currentDate = LocalDate.now();

        // Create layout elements
        calendarGrid = createCalendarGrid(currentDate);
        monthLabel = new Label(currentDate.getMonth().toString() + " " + currentDate.getYear());
        monthLabel.setFont(new Font(20));
        Button leftButton = new Button("<-");
        Button rightButton = new Button("->");

        // Handle button clicks to change months
        leftButton.setOnAction(event -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar(currentDate);
        });
        rightButton.setOnAction(event -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar(currentDate);
        });

        // Create main layout
        HBox buttonBox = new HBox(10, leftButton, monthLabel, rightButton);
        buttonBox.setPadding(new Insets(10));
        VBox mainVBox = new VBox(10, buttonBox, calendarGrid);
        mainVBox.setPadding(new Insets(20));


    }

    private GridPane createCalendarGrid(LocalDate date) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));

        // Define day labels
        String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < weekdays.length; i++) {
            Label dayLabel = new Label(weekdays[i]);
            dayLabel.setFont(Font.font(12));
            grid.add(dayLabel, i, 0);
        }

        // Calculate first day of the month and offset
        YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());
        int firstDay = yearMonth.atDay(1).getDayOfWeek().getValue() - 1; // 0-based index

        // Fill calendar grid with days, highlighting current day
        int dayCount = 1;
        for (int row = 1; row <= 6; row++) {
            for (int col = 0; col < 7; col++) {
                int index = row * 7 + col;
                if (index < firstDay || index >= firstDay + yearMonth.lengthOfMonth()) {
                    // Empty cell outside current month
                    grid.add(new Label(""), col, row);
                } else {
                    Label dayLabel = new Label(Integer.toString(dayCount));
                    if (dayCount == currentDate.getDayOfMonth()) {
                        dayLabel.setStyle("-fx-background-color: lightblue;"); // Highlight current day
                    }
                    grid.add(dayLabel, col, row);
                    dayCount++;
                }
            }
        }

        return grid;
    }


    private void updateCalendar(LocalDate newDate) {
        calendarGrid.getChildren().clear();  // Remove existing calendar elements
        calendarGrid = createCalendarGrid(newDate);  // Recreate calendar with new date
        monthLabel.setText(newDate.getMonth().toString() + " " + newDate.getYear());
    }


}
