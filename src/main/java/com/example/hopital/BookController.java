package com.example.hopital;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import model.Book;
public class BookController {
    @FXML
    private Label authorName;
    @FXML
    private ImageView bookImage;
    @FXML
    private Label bookName;
    @FXML
    private HBox box;
    public void setData(Book book) {
        Image image = new Image(getClass().getResourceAsStream(book.getImageSrc()));
        bookImage.setImage(image);

        bookName.setText(book.getName());
        authorName.setText(book.getAuthor());
        box.setStyle("-fx-background-radius: 15;" +
                "-fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 10);");

        //+"-fx-background-color: #FFFFFF;"
    }
}
