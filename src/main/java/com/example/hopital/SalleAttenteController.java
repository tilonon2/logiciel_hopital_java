package com.example.hopital;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DateTimeException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalleAttenteController {
    SeatDetails sd = new SeatDetails();
    PayeeDetails pd = new PayeeDetails();
    DatabaseList dl = new DatabaseList();
    int enter = 0; int exit=0; int click = 0;


    @FXML
    private CheckBox a1;

    @FXML
    private CheckBox a2;

    @FXML
    private CheckBox a3;

    @FXML
    private CheckBox a4;

    @FXML
    private CheckBox a5;

    @FXML
    private CheckBox a6;

    @FXML
    private CheckBox b1;

    @FXML
    private CheckBox b2;

    @FXML
    private CheckBox b3;

    @FXML
    private CheckBox b4;

    @FXML
    private CheckBox b5;

    @FXML
    private CheckBox b6;

    @FXML
    private Button btnbook;

    @FXML
    private Button btnseat;

    @FXML
    private CheckBox c1;

    @FXML
    private CheckBox c2;

    @FXML
    private CheckBox c3;

    @FXML
    private CheckBox c4;

    @FXML
    private CheckBox c5;

    @FXML
    private CheckBox c6;

    @FXML
    private CheckBox d1;

    @FXML
    private CheckBox d2;

    @FXML
    private CheckBox d3;

    @FXML
    private CheckBox d4;

    @FXML
    private CheckBox d5;

    @FXML
    private CheckBox d6;

    @FXML
    private DatePicker date;

    @FXML
    private ImageView ia1;

    @FXML
    private ImageView ia2;

    @FXML
    private ImageView ia3;

    @FXML
    private ImageView ia4;

    @FXML
    private ImageView ia5;

    @FXML
    private ImageView ia6;

    @FXML
    private ImageView ib1;

    @FXML
    private ImageView ib2;

    @FXML
    private ImageView ib3;

    @FXML
    private ImageView ib4;

    @FXML
    private ImageView ib5;

    @FXML
    private ImageView ib6;

    @FXML
    private ImageView ic1;

    private DatabaseManager dbManager;

    @FXML
    private ImageView ic2;

    @FXML
    private ImageView ic3;

    @FXML
    private ImageView ic4;

    @FXML
    private ImageView ic5;

    @FXML
    private ImageView ic6;

    @FXML
    private ImageView id1;

    @FXML
    private ImageView id2;

    @FXML
    private ImageView id3;

    @FXML
    private ImageView id4;

    @FXML
    private ImageView id5;

    @FXML
    private ImageView id6;

    @FXML
    private ChoiceBox<String> movies;

    //@FXML
    //private AnchorPane seatpreview;

    @FXML
    private VBox seatpreview;

    //Image image = new Image ("/images/chaise/icons8-chair-100.png");

    //Image image1 = new Image ("/images/chaise/rouge.png");

    Image image = new Image(getClass().getResource("/images/chaise/icons8-chair-100.png").toExternalForm());
    Image image1 = new Image(getClass().getResource("/images/chaise/rouge.png").toExternalForm());


    public SalleAttenteController() {
        dbManager = DatabaseManager.getInstance();
    }


    @FXML private void initialize() {

        movies.setValue("-Selectionner le motif");
        movies.getItems().add("-Selectionner le motif");
        movies.getItems().add("Radio");
        movies.getItems().add("Ecographie");
        movies.getItems().add("Prise de sang");
        movies.getItems().add("PCR");
        movies.getItems().add("Autre examen!");

        loadSeatData();


        btnbook.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int newWindow = onBookClicked();
                
                if (newWindow == 1) {
                    try {


                        // Charger le fichier FXML
                        Parent payee_details_parent = FXMLLoader.load(getClass().getResource("constante_constante.fxml"));
                        
                        // Créer une nouvelle scène avec le parent chargé
                        Scene payee_details_scene = new Scene(payee_details_parent);
                        
                        // Créer une nouvelle fenêtre (stage)
                        Stage new_stage = new Stage();
                        
                        // Définir la scène de la nouvelle fenêtre
                        new_stage.setScene(payee_details_scene);
                        
                        // Afficher la nouvelle fenêtre
                        new_stage.show();
                        
                        // Optionnel: cacher la fenêtre actuelle si nécessaire
                        // Stage current_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        // current_stage.hide();
        
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        btnseat.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                onSeatClicked();

            }
        });

        // ->a

        a1.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =1;
                onmousedragged();
            }
        });
        a1.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=1;
                onmouseexited();

            }
        });
        a1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =1;
                onmouseclicked();

            }
        });
        a2.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =2;
                onmousedragged();

            }
        });
        a2.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=2;
                onmouseexited();

            }
        });
        a2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =2;
                onmouseclicked();

            }
        });
        a3.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =3;
                onmousedragged();

            }
        });
        a3.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=3;
                onmouseexited();

            }
        });
        a3.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =3;
                onmouseclicked();

            }
        });
        a4.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =4;
                onmousedragged();

            }
        });
        a4.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=4;
                onmouseexited();

            }
        });
        a4.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =4;
                onmouseclicked();

            }
        });
        a5.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =5;
                onmousedragged();

            }
        });
        a5.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=5;
                onmouseexited();

            }
        });
        a5.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =5;
                onmouseclicked();

            }
        });
        a6.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =6;
                onmousedragged();

            }
        });
        a6.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=6;
                onmouseexited();

            }
        });
        a6.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =6;
                onmouseclicked();

            }
        });

        // ->b

        b1.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =12;
                onmousedragged();

            }
        });
        b1.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=12;
                onmouseexited();

            }
        });
        b1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =12;
                onmouseclicked();

            }
        });


        b2.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =13;
                onmousedragged();

            }
        });
        b2.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=13;
                onmouseexited();

            }
        });
        b2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =13;
                onmouseclicked();

            }
        });


        b3.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =14;
                onmousedragged();

            }
        });
        b3.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=14;
                onmouseexited();

            }
        });
        b3.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =14;
                onmouseclicked();

            }
        });


        b4.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =15;
                onmousedragged();

            }
        });
        b4.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=15;
                onmouseexited();

            }
        });
        b4.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =15;
                onmouseclicked();

            }
        });
        b5.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =16;
                onmousedragged();

            }
        });
        b5.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=16;
                onmouseexited();

            }
        });
        b5.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =16;
                onmouseclicked();

            }
        });
        b6.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =17;
                onmousedragged();

            }
        });
        b6.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=17;
                onmouseexited();

            }
        });
        b6.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =17;
                onmouseclicked();

            }
        });

        // ->c


        c1.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =22;
                onmousedragged();

            }
        });

        c1.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=22;
                onmouseexited();

            }
        });
        c1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =22;
                onmouseclicked();

            }
        });


        c2.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =23;
                onmousedragged();

            }
        });
        c2.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=23;
                onmouseexited();

            }
        });
        c2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =23;
                onmouseclicked();

            }
        });


        c3.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =24;
                onmousedragged();

            }
        });
        c3.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=24;
                onmouseexited();

            }
        });
        c3.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =24;
                onmouseclicked();

            }
        });

        c4.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =25;
                onmousedragged();

            }
        });
        c4.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=25;
                onmouseexited();

            }
        });
        c4.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =25;
                onmouseclicked();

            }
        });
        c5.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =26;
                onmousedragged();

            }
        });
        c5.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=26;
                onmouseexited();

            }
        });
        c5.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =26;
                onmouseclicked();

            }
        });
        c6.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =27;
                onmousedragged();

            }
        });
        c6.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=27;
                onmouseexited();

            }
        });
        c6.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =27;
                onmouseclicked();

            }
        });


        // ->d

        d1.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =32;
                onmousedragged();

            }
        });
        d1.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=32;
                onmouseexited();

            }
        });
        d1.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =32;
                onmouseclicked();

            }
        });


        d2.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =33;
                onmousedragged();

            }
        });
        d2.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=33;
                onmouseexited();

            }
        });
        d2.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =33;
                onmouseclicked();

            }
        });

        d3.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =34;
                onmousedragged();

            }
        });

        d3.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=34;
                onmouseexited();

            }
        });
        d3.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =34;
                onmouseclicked();

            }
        });


        d4.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =35;
                onmousedragged();

            }
        });
        d4.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=35;
                onmouseexited();

            }
        });
        d4.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =35;
                onmouseclicked();

            }
        });
        d5.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =36;
                onmousedragged();

            }
        });
        d5.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=36;
                onmouseexited();

            }
        });
        d5.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =36;
                onmouseclicked();

            }
        });
        d6.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                enter =37;
                onmousedragged();

            }
        });
        d6.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                exit=37;
                onmouseexited();

            }
        });
        d6.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void	handle(MouseEvent event){
                click =37;
                onmouseclicked();

            }
        });

    }//ends intialize methods


    @FXML
    private void updateSeatStatus(ActionEvent event) {

        CheckBox source = (CheckBox) event.getSource();
        String fxid = source.getId();
        boolean isChecked = source.isSelected();

        System.out.println("FXID: " + fxid);
        System.out.println("Is Checked: " + isChecked);

        // Effectuez la mise à jour de la base de données
        String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Récupérer la date et l'heure actuelles
            LocalDate date = LocalDate.now();
            LocalTime heure = LocalTime.now();

            // Formater l'heure pour l'affichage
            DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String heureFormate = heure.format(heureFormatter);

            pst.setBoolean(1, isChecked);
            pst.setDate(2, Date.valueOf(date));
            pst.setString(3, heureFormate);
            pst.setString(4, movies.getValue());
            pst.setString(5, fxid);

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void loadSeatData() {

        String query = "SELECT id FROM siege WHERE occupe = TRUE";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String seatNumber = rs.getString("id");
                switch (seatNumber) {
                    case "a1":
                        a1.setSelected(true);
                        ia1.setImage(image1);
                        break;
                    case "a2":
                        a2.setSelected(true);
                        ia2.setImage(image1);
                        break;
                    case "a3":
                        a3.setSelected(true);
                        ia3.setImage(image1);
                        break;
                    case "a4":
                        a4.setSelected(true);
                        ia4.setImage(image1);
                        break;
                    case "a5":
                        a5.setSelected(true);
                        ia5.setImage(image1);
                        break;
                    case "a6":
                        a6.setSelected(true);
                        ia6.setImage(image1);
                        break;
                    case "b1":
                        b1.setSelected(true);
                        ib1.setImage(image1);
                        break;
                    case "b2":
                        b2.setSelected(true);
                        ib2.setImage(image1);
                        break;
                    case "b3":
                        b3.setSelected(true);
                        ib3.setImage(image1);
                        break;
                    case "b4":
                        b4.setSelected(true);
                        ib4.setImage(image1);
                        break;
                    case "b5":
                        b5.setSelected(true);
                        ib5.setImage(image1);
                        break;
                    case "b6":
                        b6.setSelected(true);
                        ib6.setImage(image1);
                        break;
                    case "c1":
                        c1.setSelected(true);
                        ic1.setImage(image1);
                        break;
                    case "c2":
                        c2.setSelected(true);
                        ic2.setImage(image1);
                        break;
                    case "c3":
                        c3.setSelected(true);
                        ic3.setImage(image1);
                        break;
                    case "c4":
                        c4.setSelected(true);
                        ic4.setImage(image1);
                        break;
                    case "c5":
                        c5.setSelected(true);
                        ic5.setImage(image1);
                        break;
                    case "c6":
                        c6.setSelected(true);
                        ic6.setImage(image1);
                        break;
                    case "d1":
                        d1.setSelected(true);
                        id1.setImage(image1);
                        break;
                    case "d2":
                        d2.setSelected(true);
                        id2.setImage(image1);
                        break;
                    case "d3":
                        d3.setSelected(true);
                        id3.setImage(image1);
                        break;
                    case "d4":
                        d4.setSelected(true);
                        id4.setImage(image1);
                        break;
                    case "d5":
                        d5.setSelected(true);
                        id5.setImage(image1);
                        break;
                    case "d6":
                        d6.setSelected(true);
                        id6.setImage(image1);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void onmousedragged(){
        switch(enter){
            case 0 :System.out.print("not working");
                break;

            // ->a

            case 1 :
                if(!a1.isSelected())
                    ia1.setImage(image1);
                break;
            case 2 :
                if(!a2.isSelected())
                    ia2.setImage(image1);
                break;
            case 3 :
                if(!a3.isSelected())
                    ia3.setImage(image1);
                break;
            case 4 :
                if(!a4.isSelected())
                    ia4.setImage(image1);
                break;
            case 5 :
                if(!a5.isSelected())
                    ia5.setImage(image1);
                break;
            case 6 :
                if(!a6.isSelected())
                    ia6.setImage(image1);
                break;
            // ->b

            case 12 :
                if(!b1.isSelected())
                    ib1.setImage(image1);
                break;
            case 13 :
                if(!b2.isSelected())
                    ib2.setImage(image1);
                break;
            case 14 :
                if(!b3.isSelected())
                    ib3.setImage(image1);
                break;
            case 15 :
                if(!b4.isSelected())
                    ib4.setImage(image1);
                break;
            case 16 :
                if(!b5.isSelected())
                    ib5.setImage(image1);
                break;
            case 17 :
                if(!b6.isSelected())
                    ib6.setImage(image1);
                break;

            // ->c
            case 22 :
                if(!c1.isSelected())
                    ic1.setImage(image1);
                break;
            case 23 :
                if(!c2.isSelected())
                    ic2.setImage(image1);
                break;
            case 24 :
                if(!c3.isSelected())
                    ic3.setImage(image1);
                break;
            case 25 :
                if(!c4.isSelected())
                    ic4.setImage(image1);
                break;
            case 26 :
                if(!c5.isSelected())
                    ic5.setImage(image1);
                break;
            case 27 :
                if(!c6.isSelected())
                    ic6.setImage(image1);
                break;
            // ->d
            case 32 :
                if(!d1.isSelected())
                    id1.setImage(image1);
                break;
            case 33 :
                if(!d2.isSelected())
                    id2.setImage(image1);
                break;
            case 34 :
                if(!d3.isSelected())
                    id3.setImage(image1);
                break;
            case 35 :
                if(!d4.isSelected())
                    id4.setImage(image1);
                break;
            case 36 :
                if(!d5.isSelected())
                    id5.setImage(image1);
                break;
            case 37 :
                if(!d6.isSelected())
                    id6.setImage(image1);
                break;
        }

    }//onmousedragged method ends

    private void onmouseexited(){

        switch(exit){
            case 0 :System.out.print("not working");
                break;

            // ->a
            case 1 :
                if(!a1.isSelected())
                    ia1.setImage(image);
                break;
            case 2 :
                if(!a2.isSelected())
                    ia2.setImage(image);
                break;
            case 3 :
                if(!a3.isSelected())
                    ia3.setImage(image);
                break;
            case 4 :
                if(!a4.isSelected())
                    ia4.setImage(image);
                break;
            case 5 :
                if(!a5.isSelected())
                    ia5.setImage(image);
                break;
            case 6 :
                if(!a6.isSelected())
                    ia6.setImage(image);
                break;

            // ->b
            case 12 :
                if(!b1.isSelected())
                    ib1.setImage(image);
                break;
            case 13 :
                if(!b2.isSelected())
                    ib2.setImage(image);
                break;
            case 14 :
                if(!b3.isSelected())
                    ib3.setImage(image);
                break;
            case 15 :
                if(!b4.isSelected())
                    ib4.setImage(image);
                break;
            case 16 :
                if(!b5.isSelected())
                    ib5.setImage(image);
                break;
            case 17 :
                if(!b6.isSelected())
                    ib6.setImage(image);
                break;
            // ->c
            case 22 :
                if(!c1.isSelected())
                    ic1.setImage(image);
                break;
            case 23 :
                if(!c2.isSelected())
                    ic2.setImage(image);
                break;
            case 24 :
                if(!c3.isSelected())
                    ic3.setImage(image);
                break;
            case 25 :
                if(!c4.isSelected())
                    ic4.setImage(image);
                break;
            case 26 :
                if(!c5.isSelected())
                    ic5.setImage(image);
                break;
            case 27 :
                if(!c6.isSelected())
                    ic6.setImage(image);
                break;
            // ->d
            case 32 :
                if(!d1.isSelected())
                    id1.setImage(image);
                break;
            case 33 :
                if(!d2.isSelected())
                    id2.setImage(image);
                break;
            case 34 :
                if(!d3.isSelected())
                    id3.setImage(image);
                break;
            case 35 :
                if(!d4.isSelected())
                    id4.setImage(image);
                break;
            case 36 :
                if(!d5.isSelected())
                    id5.setImage(image);
                break;
            case 37 :
                if(!d6.isSelected())
                    id6.setImage(image);
                break;
        }

    }

    private void onmouseclicked(){
        switch(click){
            case 0 :System.out.print("not working");
                break;
            // ->a
            case 1 :
                if(!a1.isSelected()){
                    ia1.setImage(image);
                }else{
                    ia1.setImage(image1);



                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = a1.getId();
                    boolean isChecked = a1.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }






                }
                break;
            case 2 :
                if(!a2.isSelected())
                    ia2.setImage(image);
                else{
                    ia2.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = a2.getId();
                    boolean isChecked = a2.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 3 :
                if(!a3.isSelected())
                    ia3.setImage(image);
                else{
                    ia3.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = a3.getId();
                    boolean isChecked = a3.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 4 :
                if(!a4.isSelected())
                    ia4.setImage(image);
                else{
                    ia4.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = a4.getId();
                    boolean isChecked = a4.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 5 :
                if(!a5.isSelected())
                    ia5.setImage(image);
                else{
                    ia5.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = a5.getId();
                    boolean isChecked = a5.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 6 :
                if(!a6.isSelected())
                    ia6.setImage(image);
                else{
                    ia6.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = a6.getId();
                    boolean isChecked = a6.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            // ->b
            case 12 :
                if(!b1.isSelected())
                    ib1.setImage(image);
                else{
                    ib1.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = b1.getId();
                    boolean isChecked = b1.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 13 :
                if(!b2.isSelected())
                    ib2.setImage(image);
                else{
                    ib2.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = b2.getId();
                    boolean isChecked = b2.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 14 :
                if(!b3.isSelected())
                    ib3.setImage(image);
                else{
                    ib3.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = b3.getId();
                    boolean isChecked = b3.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 15 :
                if(!b4.isSelected())
                    ib4.setImage(image);
                else{
                    ib4.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = b4.getId();
                    boolean isChecked = b4.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 16 :
                if(!b5.isSelected())
                    ib5.setImage(image);
                else{
                    ib5.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = b5.getId();
                    boolean isChecked = b5.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 17 :
                if(!b6.isSelected())
                    ib6.setImage(image);
                else{
                    ib6.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = b6.getId();
                    boolean isChecked = b6.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            // ->c
            case 22 :
                if(!c1.isSelected())
                    ic1.setImage(image);
                else{
                    ic1.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = c1.getId();
                    boolean isChecked = c1.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 23 :
                if(!c2.isSelected())
                    ic2.setImage(image);
                else{
                    ic2.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = c2.getId();
                    boolean isChecked = c2.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 24 :
                if(!c3.isSelected())
                    ic3.setImage(image);
                else{
                    ic3.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = c3.getId();
                    boolean isChecked = c3.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 25 :
                if(!c4.isSelected())
                    ic4.setImage(image);
                else{
                    ic4.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = c4.getId();
                    boolean isChecked = c4.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 26 :
                if(!c5.isSelected())
                    ic5.setImage(image);
                else{
                    ic5.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = c5.getId();
                    boolean isChecked = c5.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 27 :
                if(!c6.isSelected())
                    ic6.setImage(image);
                else{
                    ic6.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = c6.getId();
                    boolean isChecked = c6.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            // ->d
            case 32 :
                if(!d1.isSelected())
                    id1.setImage(image);
                else{
                    id1.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = d1.getId();
                    boolean isChecked = d1.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 33 :
                if(!d2.isSelected())
                    id2.setImage(image);
                else{
                    id2.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = d2.getId();
                    boolean isChecked = d2.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 34 :
                if(!d3.isSelected())
                    id3.setImage(image);
                else{
                    id3.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = d3.getId();
                    boolean isChecked = d3.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 35 :
                if(!d4.isSelected())
                    id4.setImage(image);
                else{
                    id4.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = d4.getId();
                    boolean isChecked = d4.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 36 :
                if(!d5.isSelected())
                    id5.setImage(image);
                else{
                    id5.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = d5.getId();
                    boolean isChecked = d5.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 37 :
                if(!d6.isSelected())
                    id6.setImage(image);
                else{
                    id6.setImage(image1);

                    //CheckBox source = (CheckBox) event.getSource();
                    String fxid = d6.getId();
                    boolean isChecked = d6.isSelected();

                    System.out.println(isChecked);
                    System.out.println(fxid);



                    System.out.println("FXID: " + fxid);
                    System.out.println("Is Checked: " + isChecked);

                    // Effectuez la mise à jour de la base de données
                    String query = "UPDATE siege SET occupe = ?, date = ?, heure = ?, motif = ? WHERE id = ?";
                    try (Connection conn = dbManager.getConnection();
                         PreparedStatement pst = conn.prepareStatement(query)) {

                        // Récupérer la date et l'heure actuelles
                        LocalDate date = LocalDate.now();
                        LocalTime heure = LocalTime.now();

                        // Formater l'heure pour l'affichage
                        DateTimeFormatter heureFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String heureFormate = heure.format(heureFormatter);

                        pst.setBoolean(1, isChecked);
                        pst.setDate(2, Date.valueOf(date));
                        pst.setString(3, heureFormate);
                        pst.setString(4, movies.getValue());
                        pst.setString(5, fxid);

                        pst.executeUpdate();
                    } catch (SQLException ex) {
                        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }//onmouseexited ends

    public int onBookClicked(){
        int temp = 0;

        // try{
        //     sd.setMovies(movies.getValue());
        // }

        // catch (NullPointerException e) {
        //     temp = 1;
        //     Alert alert = new Alert(Alert.AlertType.ERROR);
        //     alert.setContentText("Error: you haven't choose your movie,Please choose it.");
        //     alert.show();}


        try{
            sd.setMovies(movies.getValue());

        }

        catch (NullPointerException e) {
            temp = 1;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur : vous n'avez pas choisi le motif, veuillez en choisir!!");
            alert.show();}

        /*try{
            sd.setDate(date.getValue());
        }

        catch (IllegalArgumentException e) {
            temp = 1;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur : vous avez choisi la date avant aujourd'hui, veuillez choisir les dates à venir.");
            alert.show();}

        catch (DateTimeException e) {
            temp = 1;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur : vous avez choisi la date hors plage, veuillez choisir la date dans cette semaine.");
            alert.show();}

        catch (NullPointerException e) {
            temp = 1;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur : vous n'avez pas choisi votre date, veuillez la choisir.");
            alert.show();} */

        int temp2 = 0;
        int num = 0;
        String label = "";

        // ->a

        if(a1.isSelected()){
            label += "A1"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;


        }
        if(a2.isSelected()){
            label += "A2" +", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(a3.isSelected()){
            label += "A3" +", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(a4.isSelected()){
            label += "A4"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(a5.isSelected()){
            label += "A5"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(a6.isSelected()){
            label += "A6"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        // ->b
        if(b1.isSelected()){
            label += "B1"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(b2.isSelected()){
            label += "B2"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(b3.isSelected()){
            label += "B3"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(b4.isSelected()){
            label += "B4"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(b5.isSelected()){
            label += "B5"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(b6.isSelected()){
            label += "B6"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        // ->c
        if(c1.isSelected()){
            label += "C1"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(c2.isSelected()){
            label += "C2"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(c3.isSelected()){
            label += "C3"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(c4.isSelected()){
            label += "C4"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(c5.isSelected()){
            label += "C5"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(c6.isSelected()){
            label += "C6"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        // ->d

        if(d1.isSelected()){
            label += "D1"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(d2.isSelected()){
            label += "D2"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(d3.isSelected()){
            label += "D3"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(d4.isSelected()){
            label += "D4"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(d5.isSelected()){
            label += "D5"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }
        if(d6.isSelected()){
            label += "D6"+", ";
            sd.setTicketName(label);
            temp2 = 1;
            num++;
        }

        if(num>=1){
            sd.setNumTickets(num);
        }
        if(temp2==0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur : vous n’avez pas choisi votre/vos siège(s), Veuillez la choisir.");
            alert.show();}

        //->date


        int temp3 =1;


        // int temp3 =0;
        //	if(rd1.isSelected()){
        //		sd.setShowtime("10:00 AM");
        //		temp3 = 1;
        //	}
        //	if(rd2.isSelected()){
        //		sd.setShowtime("3:00 PM");
        //		temp3 = 1;
        //	}
        //	if(rd3.isSelected()){
        //		sd.setShowtime("10:30 PM");
        //		temp3 = 1;
        //	}
        //
        //	if(temp3 == 0){
        //		Alert alert = new Alert(AlertType.ERROR);
        //		alert.setContentText("Error: you haven't choose your showtime,Please choose it.");
        //		alert.show();
        //	}


        // if(temp == 0 && temp2==1 && num>=1 && temp3 ==1) {
        if(temp == 0 && temp2==1 && num>=1 && temp3 ==1) {

            File file = new File("Receipts.txt");
            try {
                PrintWriter out = new PrintWriter("Receipts.txt");

                out.print("-----------------Reception Details----------------");
                out.println();
                //out.print("City : "+ sd.getCity());
                //                out.println();
                //                out.print("Theatre: "+ sd.getTheatre());
                //                out.println();
                out.print("Motif : " + sd.getMovies());
                out.println();
                out.print("Date : " + sd.getDate());
                out.println();
                //out.print("Showtime: "+ sd.getShowtime() );
                //                out.println();
                out.print("Nombre de Sièges: " + sd.getNumTickets());
                out.println();
                out.print("Siège(s) reservé(s): " + sd.getTicketName().substring(0, sd.getTicketName().length() - 2));
                out.println();
                out.print("-----------------Reception Details----------------");



                out.close();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }

            dl.addSeat(sd);
            dl = new DatabaseList();

            return 1;
        }//if ends
        else{
            return 0;
        }

    }//onBookClicked ends


    private void onSeatClicked(){
        if(!seatpreview.isVisible()){
            seatpreview.setVisible(true);
        }
        else{
            seatpreview.setVisible(false);
        }
    }


}//ends the class