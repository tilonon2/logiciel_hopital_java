package com.example.hopital;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

public class constanteController implements Initializable {
    private Label alertfrequencecardiaque;

    @FXML
    private Label alertfrequencerespiration;

    @FXML
    private Label alertpoids;

    @FXML
    private Label alertpressionarterielle;

    @FXML
    private Label alertsaturationoxygene11;

    @FXML
    private Label alerttaille;

    @FXML
    private Label alerttemperature;

    @FXML
    private Button btnback;

    @FXML
    private Button btnconfirm;

    @FXML
    private ComboBox<String> liste_patient;

    @FXML
    private TextField txtfrequencecardiaque;

    @FXML
    private TextField txtfrequencerespiration;

    @FXML
    private TextField txtpoids;

    @FXML
    private TextField txtpressionarterielle;

    @FXML
    private TextField txtsaturationoxygene1;

    @FXML
    private TextField txtsaturationoxygene11;

    @FXML
    private TextField txttaille;

    @FXML
    private TextField txttemperature;

    PayeeDetails pd = new PayeeDetails();
    DatabaseList dl = new DatabaseList();
    //Tilo
    private DatabaseManager dbManager;
    private Map<String, Integer> patientMap = new HashMap<>();
    public constanteController() {
        dbManager = DatabaseManager.getInstance();
    }

    private ObservableList<String> allPatients = FXCollections.observableArrayList();

    void loadFonctionData() {
        String query = "SELECT id, prenom FROM patient";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String prenom = rs.getString("prenom");
                if (!patientMap.containsKey(prenom)) {
                    allPatients.add(prenom);
                    patientMap.put(prenom, id);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


// ajotuer les constante et siege
@FXML
private void insertConstanteData() {
    String insertQuery = "INSERT INTO constante (taille, poids, frequence_cardiaque, frequence_respiratoire, "
            + "pression_arterielle, temperature_corporelle, saturation_oxygene, id_patient) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    String updateQuery = "UPDATE siege SET id_patient = ?, id_constant = ? WHERE occupe = 1 AND id_patient = 0 LIMIT 1";



    try (Connection conn = dbManager.getConnection();
         PreparedStatement pstInsert = conn.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS);
         PreparedStatement pstUpdate = conn.prepareStatement(updateQuery)) {


        String selectedPrenom = liste_patient.getValue();
        int selectedPatientId = -1;
        if (selectedPrenom != null) {
            selectedPatientId = patientMap.get(selectedPrenom);
            //System.out.println("Selected Patient ID: " + selectedPatientId);
            // Utilisez l'ID du patient selon vos besoins, par exemple, pour une requête de base de données
        } else {
            System.out.println("Aucun patient sélectionné");
        }

        // Récupérer les valeurs des TextField
        double taille = Double.parseDouble(txttaille.getText());
        double poids = Double.parseDouble(txtpoids.getText());
        int frequenceCardiaque = Integer.parseInt(txtfrequencecardiaque.getText());
        int frequenceRespiration = Integer.parseInt(txtfrequencerespiration.getText());
        String pressionArterielle = txtpressionarterielle.getText();
        double temperatureCorporelle = Double.parseDouble(txttemperature.getText());
        double saturationOxygene = Double.parseDouble(txtsaturationoxygene11.getText());
        //int idPatient = Integer.parseInt(txtsaturationoxygene11.getText()); // Utilisez le champ approprié pour id_patient

        // Définir les valeurs pour la requête
        pstInsert.setDouble(1, taille);
        pstInsert.setDouble(2, poids);
        pstInsert.setInt(3, frequenceCardiaque);
        pstInsert.setInt(4, frequenceRespiration);
        pstInsert.setString(5, pressionArterielle);
        pstInsert.setDouble(6, temperatureCorporelle);
        pstInsert.setDouble(7, saturationOxygene);
        pstInsert.setInt(8, selectedPatientId);

        pstInsert.executeUpdate();
        System.out.println("Data inserted successfully.");

        ResultSet generatedKeys = pstInsert.getGeneratedKeys();
        if (generatedKeys.next()) {
            int generatedConstantId = generatedKeys.getInt(1);

            // Définir les valeurs pour la requête de mise à jour
            pstUpdate.setInt(1, selectedPatientId);
            pstUpdate.setInt(2, generatedConstantId);

            // Exécuter la requête de mise à jour
            pstUpdate.executeUpdate();
            System.out.println("Data inserted and siege updated successfully. Constant ID: " + generatedConstantId);
        }



    } catch (SQLException ex) {
        Logger.getLogger(SalleAttenteController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NumberFormatException ex) {
        System.err.println("Invalid number format: " + ex.getMessage());
    }
}


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadFonctionData();

        FilteredList<String> filteredPatients = new FilteredList<>(allPatients, p -> true);

        txtsaturationoxygene1.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPatients.setPredicate(prenom -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return prenom.toLowerCase().contains(lowerCaseFilter);
            });
        });

        liste_patient.setItems(filteredPatients);
        // Ajoutez(Radji) ici le contenu de la méthode initialize annotée avec @FXML
        btnconfirm.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int newWindow = onConfirmClicked();
                if (newWindow == 1) {
                    try {
                        insertConstanteData();

                        // Charger le fichier FXML
                        Parent ticket_window = FXMLLoader.load(getClass().getResource("contactView.fxml"));

                        // Créer une nouvelle scène
                        Scene payee_details = new Scene(ticket_window);

                        // Créer une nouvelle fenêtre
                        Stage newStage = new Stage();
                        newStage.setScene(payee_details);
                        newStage.setTitle("Contact View");

                        // Obtenir la fenêtre actuelle
                        Stage currentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                        // Masquer la fenêtre actuelle
                        currentStage.hide();

                        // Afficher la nouvelle fenêtre
                        newStage.show();

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
    //Fin Tilo

    //Radji
    private int onConfirmClicked(){

        int temp =0;

        //Température corporelle
        try{
            pd.setTemperatureCorporelle(txttemperature.getText());

        }

        catch (NullPointerException e) {
            temp = 1;
            alerttemperature.setText("Vous devez entrer votre temperature corporelle.");
        }

        //Fréquence cardiaque
        try{
            pd.setFrequenceCardiaque(txtfrequencecardiaque.getText());

        }

        catch (NullPointerException e) {
            temp = 1;
            alertfrequencecardiaque.setText("!!!");
        }
        //pression artérielle
        try{
            pd.setPressionArterielle(txtpressionarterielle.getText());

        }

        catch (NullPointerException e) {
            temp = 1;
            alertpressionarterielle.setText("Vous devez entrer votre pression arterielle.");
        }
        //Frequence Respiratoire
        try{
            pd.setFrequenceRespiratoire(txtfrequencerespiration.getText());

        }

        catch (NullPointerException e) {
            temp = 1;
            alertfrequencerespiration.setText("!!!");
        }
        //saturation en oxygène
        try{
            pd.setSaturationOxygene(txtsaturationoxygene11.getText());

        }

        catch (NullPointerException e) {
            temp = 1;
            alertsaturationoxygene11.setText("Vous devez entrer votre saturation en oxygène.");
        }
        //taille
        try{
            pd.setTaille(Integer.parseInt(txttaille.getText()));

        }

        catch (Exception e) {
            temp = 1;
            alerttaille.setText("Entrer une valeure !");
        }
        //Poids
        try{
            pd.setPoids(Integer.parseInt(txtpoids.getText()));

        }

        catch (Exception e) {
            temp = 1;
            alertpoids.setText("Entrer une valeure !");
        }
        //Mobile
        // try{
        //     pd.setMob(txtmob.getText());

        // }

        // catch (NullPointerException e) {
        //     temp = 1;
        //     alertmob.setText("Vous devez entrer votre Numéro de Téléphone.");
        // }
        // catch (IllegalArgumentException e) {
        //     temp = 1;
        //     alertmob.setText("Le numéro de téléphone doit être composé de 10 chiffres.");
        // }

        //Email
        // try{
        //     pd.setEmail(txtemail.getText());

        // }

        // catch (NullPointerException e) {
        //     temp = 1;
        //     alertemail.setText("Vous devez entrer votre email.");
        // }

        // try{
        //     pd.setCard(txtcard.getText());

        // }

        // catch (NullPointerException e) {
        //     temp = 1;
        //     alertcard.setText("Vous devez entrer votre card details");
        // }

        // catch (IllegalArgumentException e) {
        //     temp = 1;
        //     alertcard.setText("Card number must be of 16 digits.");
        // }

        // int temp2 =0;
        // if(credit.isSelected()){
        //     pd.setPayment("Paid by Credit/Debit Card");
        //     temp2 =1;

        // }
        // if(scene.isSelected()){
        //     pd.setPayment("Paid by Scene Card");
        //     temp2 =1;

        // }
        // if (temp2 ==0){
        //     alertpayment.setText("Vous devez entrer votre card details to proceed.");
        //     temp = 1;
        // }

        //txt

        if(temp == 0){

            try {
                FileWriter out = new FileWriter("ConstanteX.txt",true);


                // out.write("\r\nNom : "+ pd.getName());
                // out.write("\r\n");
                // out.write("\r\nPrénom : "+ pd.getPrenom());
                // out.write("\r\n");
                // out.write("Age: "+ pd.getAge());
                // out.write("\r\n");
                // out.write("Numéro de Téléphone : "+ pd.getMob());
                // out.write("\r\n");
                out.write("Taille : "+ pd.getTaille());
                out.write("\r\n");
                out.write("Poids : "+ pd.getPoids());
                out.write("\r\n");
                out.write("Temperature Corporelle : "+ pd.getTemperatureCorporelle());
                out.write("\r\n");
                out.write("Frequence Cardiaque : "+ pd.getFrequenceCardiaque());
                out.write("\r\n");
                out.write("Pression Artérielle : "+ pd.getPressionArterielle());
                out.write("\r\n");
                out.write("Fréquence Respiratoire : "+ pd.getFrequenceRespiratoire());
                out.write("\r\n");
                out.write("Saturation en Oxygène : "+ pd.getSaturationOxygene());
                out.write("\r\n");
                // out.write("Email: "+ pd.getEmail() );
                // out.write("\r\n");
                // out.write("Payment Method : "+ pd.getPayment());
                // out.write("\r\n");
                // out.write("Card Number :"+ "xxxx-xxxx-xxxx-"+ pd.getCard().substring(12,pd.getCard().length()));
                // out.write("\r\n");
                out.write("\r\n");
                out.write("------------------------------------------------");

                out.close();

            }
            catch (Exception e) {

                e.printStackTrace();
            }
            return 1;
        }//if ends
        else{
            return 0;
        }
    }//confirm method ends
    //Fin Radji
}
