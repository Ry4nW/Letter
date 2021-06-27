package textEditor;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

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
    public TextArea textEditor; // NOTE: To be replaced with InlineCSSTextArea.

    public Label fileMessage;

    public Button underlineButton;
    public Button italicsButton;
    public Button boldButton;


    // Requires: Nothing
    // Modifies:
    // Effects: Sets wrap text for our editor's text area, aka creates a newline for the user to type in
    // once the end of the area's width is reached.
    public void initialize() {
        textEditor.setWrapText(true);
    }

    // Requires: Nothing.
    // Modifies: Empty's the textArea.
    // Effects: Saves all text into a file through save() in the {IO} class.
    public void onSave() throws IOException {

        FileWriter fw = new FileWriter("textEditor/editorContent.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;

    }

    // Requires: Nothing.
    // Modifies: Fills the textArea.
    // Effects: Loads all text from a selected file through load() in the {IO} class.
    public void onLoad() {
        FileChooser fileChooser = new FileChooser();
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Terminates the process.
    public void onClose() {
        System.exit(0);
    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Opens a link on the client's default browser to the Github page of the application.
    public void onInfoGithub() {

        try {
            Desktop.getDesktop().browse(new URL("https://github.com/Ry4nW/Text-Editor").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Opens a link on the client's default browser to the application's Icon provider.
    public void onInfoIcon() {

        try {
            Desktop.getDesktop().browse(new URL("https://www.flaticon.com/free-icon/text-editor_196308").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    // Requires: Nothing.
    // Modifies: Client's view.
    // Effects: Inserts a popup serving as an "About Page" when pressed.
    public void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("Letter. \nSave, load and write your compositions with ease.");
        alert.show();
    }


    // Requires: Highlighted area the client wants to bold.
    // Modifies: textArea.
    // Effects: Bolds the highlighted text when pressed.
    public void boldText() {

        IndexRange selection = textEditor.getSelection();

    }

    // Requires: Highlighted area the client wants to italicize.
    // Modifies: textArea.
    // Effects: italicizes the highlighted text when pressed.
    public void italicizeText() {
    }

    // Requires: Highlighted area the client wants to underline.
    // Modifies: textArea.
    // Effects: Underlines the highlighted text when pressed.
    public void underlineText() {
    }
}
