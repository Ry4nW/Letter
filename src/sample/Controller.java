package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller {

    public MenuItem githubMI;
    public MenuItem iconMI;

    public TextField documentNameField;
    public TextArea textEditor;

    public Label fileMessage;

    public Button underlineButton;
    public Button italicsButton;
    public Button boldButton;


    public void initialize() {
        textEditor.setWrapText(true);

        javafx.scene.image.Image boldedIcon = new javafx.scene.image.Image("https://uxwing.com/wp-content/themes/uxwing/download/03-text-editing/bold-text.png");
        javafx.scene.image.Image italicizedIcon = new javafx.scene.image.Image("https://cdn2.iconfinder.com/data/icons/font-awesome/1792/italic-512.png");
        javafx.scene.image.Image underlinedIcon = new Image("https://image.flaticon.com/icons/png/512/56/56833.png");

        ImageView boldView = new ImageView(boldedIcon);
        ImageView italicsView = new ImageView(italicizedIcon);
        ImageView underlinedView = new ImageView(underlinedIcon);

        underlinedView.setFitHeight(15);
        italicsView.setFitHeight(15);
        boldView.setFitHeight(15);

        underlinedView.setPreserveRatio(true);
        italicsView.setPreserveRatio(true);
        boldView.setPreserveRatio(true);

        boldButton.setGraphic(boldView);
        italicsButton.setGraphic(italicsView);
        underlineButton.setGraphic(underlinedView);

    }

    public void onSave() throws IOException {

        FileWriter fw = new FileWriter("editorContent.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

    }

    public void onLoad() {

    }

    public void onClose() {
        System.exit(0);
    }


    public void onInfoGithub(ActionEvent actionEvent) {

        try {
            Desktop.getDesktop().browse(new URL("https://github.com/Ry4nW/Text-Editor").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void onInfoIcon(ActionEvent actionEvent) {

        try {
            Desktop.getDesktop().browse(new URL("https://www.flaticon.com/free-icon/text-editor_196308").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void boldText(ActionEvent actionEvent) {

        IndexRange selection = textEditor.getSelection();

    }

    public void italicizeText(ActionEvent actionEvent) {
    }

    public void underlineText(ActionEvent actionEvent) {
    }
}
