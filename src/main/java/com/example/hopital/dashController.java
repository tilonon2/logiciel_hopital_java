package com.example.hopital;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class dashController implements Initializable {
    @FXML
    private HBox cardLayoout;
    @FXML
    private VBox bookContainer;
    private List<Book> recentlyAdded;
    private List<Book> recommended;
    private List<Book> booksVertical;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        recentlyAdded = new ArrayList<>(recentlyAdded());
        //verticalement
        booksVertical = new ArrayList<>(booksVertical());
        //Pour une Grid
        // recommended = new ArrayList<>(books());
        // int column = 0;
        // int row = 1;
        try{
            //HBox
            for (Book value : recentlyAdded) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("card.fxml"));
                HBox cardBox = fxmlLoader.load();
                CardController cardController = fxmlLoader.getController();
                cardController.setData(value);
                cardLayoout.getChildren().add(cardBox);
            }
            //VBox
            for (Book book : booksVertical) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("book.fxml"));
                HBox bookBox = fxmlLoader.load();  // Utilisez HBox ici car (le conteneur racine de "book.fxml" est un HBOX)
                BookController bookController = fxmlLoader.getController();
                bookController.setData(book);
                bookContainer.getChildren().add(bookBox);
            }
            //Grid
            // for (Book book : recommended) {
            //     FXMLLoader fxmlLoader = new FXMLLoader();
            //     fxmlLoader.setLocation(getClass().getResource("book.fxml"));
            //     VBox bookBox = fxmlLoader.load();
            //     BookController bookController = fxmlLoader.getController();
            //     bookController.setData(book);

            //     if (column == 1){
            //         column = 0;
            //         ++row;

            //     }

            //     bookContainer.add(bookBox,column++, row);
            //     GridPane.setMargin(bookBox, new Insets(10));

            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //HBOX
    private List<Book> recentlyAdded() {
        List<Book> ls = new ArrayList<>();

        Book book = new Book();
        book.setImageSrc("/images/newpatients.png");
        book.setAuthor("Nouveaux patients");
        book.setName("3");
        ls.add(book);

        //Ajout
        book = new Book();
        book.setImageSrc("/images/rdvprevuscejour.png");
        book.setAuthor("RDV prévus ce jour");
        book.setName("0");
        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();
        book.setImageSrc("/images/totaldespraticiens.png");
        book.setAuthor("Total des praticiens");
        book.setName("10");
        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/totalpatient.png");
        book.setAuthor("Total des patients");
        book.setName("9");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/nouveaurdv.png");
        book.setAuthor("Nouveau RDV");
        book.setName("1");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/nouvellesconsultations.png");
        book.setAuthor("Nouvelles consultations");
        book.setName("1");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/consultation-medicale.png");
        book.setAuthor("Nouvelles hospitalisations");
        book.setName("1");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/nouveausoins.png");
        book.setAuthor("Nouveau soins");
        book.setName("1");

        ls.add(book);
        //Fin ajout


        //Ajout
        book = new Book();

        book.setImageSrc("/images/consultationencours.png");
        book.setAuthor("Consultation en cours...");
        book.setName("0");

        ls.add(book);
        //Fin ajout
        //Ajout
        book = new Book();

        book.setImageSrc("/images/hospitalisationsencours.png");
        book.setAuthor("Hospitalisations en cours...");
        book.setName("0");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/totaldesprescriptions.png");
        book.setAuthor("Total des prescriptions");
        book.setName("5");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/totaldesconsultations.png");
        book.setAuthor("Total des consultations");
        book.setName("5");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/totaldessoins.png");
        book.setAuthor("Total des soins");
        book.setName("5");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();

        book.setImageSrc("/images/totaldeshospitalisations.png");
        book.setAuthor("Total des hospitalisations");
        book.setName("5");

        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();
        book.setImageSrc("/images/litoccupes.png");
        book.setAuthor("Lits occupés");
        book.setName("1");
        ls.add(book);
        //Fin ajout

        //Ajout
        book = new Book();
        book.setImageSrc("/images/litlibre.png");
        book.setAuthor("Lits libre");
        book.setName("10");
        ls.add(book);
        //Fin ajout


        return ls;

    }
    //GRIDPANE
    // private List<Book> books(){
    //     List<Book> ls = new ArrayList<>();
    //     Book book = new Book();
    //     book.setImageSrc("/images/newpatients.png");
    //     book.setAuthor("Nouveaux patients");
    //     book.setName("3");
    //     ls.add(book);

    //     //Ajout
    //     book = new Book();
    //     book.setImageSrc("/images/rdvprevuscejour.png");
    //     book.setAuthor("RDV prévus ce jour");
    //     book.setName("0");
    //     ls.add(book);
    //     //Fin ajout


    //     //return
    //     return ls;

    // }

    //VBOX
    private List<Book> booksVertical(){
        List<Book> ls = new ArrayList<>();

        Book book = new Book();
        book.setImageSrc("/images/calendar/icons8-calendrier-bébé-100.png");
        book.setAuthor("Sekou");
        book.setName("13:40");
        ls.add(book);

        //Ajout
        book = new Book();

        book.setImageSrc("/images/calendar/icons8-calendrier-bébé-100.png");
        book.setAuthor("Examen de Radiologie");
        book.setName("08:20");

        ls.add(book);
        //Fin ajout


        //return
        return ls;

    }
    //pour switch 2 bouton en fonction de 2 pane sur la meme page
    // private void handleButtonAction(ActionEvent event) {
    //     if (event.getSource() == button1) {
    //         paneSignin.toFront();
    //         //Autre
    //         button1.setStyle("-fx-background-color: #000000");
    //         button2.setStyle("-fx-background-color: #ffffff");
    //         cardLayoout.setVisible(true);
    //         bookContainer.setVisible(false);
    //     } else if (event.getSource() == button2) {
    //         paneLogin.toFront();
    //         //Autre
    //         button2.setStyle("-fx-background-color: #000000");
    //         button1.setStyle("-fx-background-color: #ffffff");
    //         cardLayoout.setVisible(false);
    //         bookContainer.setVisible(true);
    //     }
    // }
}
