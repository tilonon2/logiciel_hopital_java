package com.example.hopital;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

public class SpecialisteController {

    @FXML
    private GridPane board;

    @FXML
    private Label statusLabel;

    private boolean playerXTurn = true;
    private Button[] buttons = new Button[9];
    private char[] boardState = new char[9];

    @FXML
    public void initialize() {
        for (int i = 0; i < 9; i++) {
            buttons[i] = (Button) board.getChildren().get(i);
            boardState[i] = ' ';
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int buttonIndex = GridPane.getRowIndex(clickedButton) * 3 + GridPane.getColumnIndex(clickedButton);

        if (boardState[buttonIndex] == ' ' && !isGameOver()) {
            boardState[buttonIndex] = playerXTurn ? 'X' : 'O';
            clickedButton.setText(String.valueOf(boardState[buttonIndex]));
            playerXTurn = !playerXTurn;
            updateStatus();
        }
    }

    @FXML
    public void handleRestart(ActionEvent event) {
        for (int i = 0; i < 9; i++) {
            boardState[i] = ' ';
            buttons[i].setText("");
        }
        playerXTurn = true;
        statusLabel.setText("Player X's turn");
    }

    private void updateStatus() {
        if (checkWinner('X')) {
            statusLabel.setText("Player X wins!");
        } else if (checkWinner('O')) {
            statusLabel.setText("Player O wins!");
        } else if (isBoardFull()) {
            statusLabel.setText("It's a draw!");
        } else {
            statusLabel.setText("Player " + (playerXTurn ? "X" : "O") + "'s turn");
        }
    }

    private boolean checkWinner(char player) {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6} // Diagonals
        };

        for (int[] pattern : winPatterns) {
            if (boardState[pattern[0]] == player && boardState[pattern[1]] == player && boardState[pattern[2]] == player) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (char c : boardState) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }

    private boolean isGameOver() {
        return checkWinner('X') || checkWinner('O') || isBoardFull();
    }
}
