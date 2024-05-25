package com.example.hopital;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class ContactViewController {

    Scanner in ;


    @FXML
    private Button btnclose;

    @FXML
    private Button btnreceipt;

    @FXML
    private TextArea txtreceipt;

    @FXML private void initialize(){
		btnclose.setOnAction( new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				onbtncloseClicked();
			}
		});

		btnreceipt.setOnAction( new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				onbtnreceiptClicked();
			}
		});
	}//initialize method ends

	// private void onbtncloseClicked(){
	// 			Stage stage = (Stage) btnclose.getScene().getWindow();
	// 			stage.close();
	// 			System.exit(0);
	// }
	private void onbtncloseClicked(){
		Stage stage = (Stage) btnclose.getScene().getWindow();
		stage.close();
	}

	private void onbtnreceiptClicked(){

		try{
			in = new Scanner(new File("ConstanteX.txt"));
		}

		catch(Exception e){
			System.out.print("ne fonctionne pas!!");

		}

		while(in.hasNext()){

			txtreceipt.appendText(in.nextLine() + "\n");

		}
	}
}
