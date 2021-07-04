package textEditor;

import IO.IO;
import IO.IOResult;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.fxmisc.richtext.InlineCssTextArea;

public class Controller {

    public MenuItem githubMI;
    public MenuItem iconMI;

    private final InlineCssTextArea textArea = new InlineCssTextArea();

    public Label fileMessage;

    public Button underlineButton;
    public Button italicsButton;
    public Button boldButton;
    public Button removeStylingBtn;

    public IO model = new IO(); // Class for our IO system.
    public BorderPane borderPane;
    private TextFile currentTextFile;

    // Requires: Nothing.
    // Modifies: textArea
    // Effects: Sets wrap text for our editor's text area, aka creates a newline for the user to type in
    // once the end of the textEditor's width is reached, border, border style/width and other styling.
    public void initialize() {

        borderPane.centerProperty().setValue(textArea); // Adds our text area to the center of the borderPane.

        textArea.setWrapText(true);
        textArea.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px");

    }


    // Requires: Nothing.
    // Modifies: Empty's the textArea.
    // Effects: Saves all text from {textEditor} into a file through save() in the {IO} class.
    public void onSave() throws IOException {

        TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(textArea.getText().split("\n")));
        model.save(textFile);

    }


    // Requires: Nothing.
    // Modifies: Fills the textArea.
    // Effects: Loads all text from a selected file through load() in the {IO} class.
    public void onLoad() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {

            Path path = file.toPath();
            IOResult<TextFile> io = model.load(Paths.get(path.toString()));

            if (io.checked() && io.hasData()) {

                currentTextFile = io.getData();
                textArea.clear();

                StringBuilder text = new StringBuilder();

                for (int i = 0; i < currentTextFile.getContent().size(); i++) {
                    text.append(currentTextFile.getContent().get(i)).append("\n");
                }

                textArea.appendText(text.toString());

            } else {
                fileMessage.setText("File load failed.");
            }
        }
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
    // Effects: Inserts a popup serving as an "About Page" when clicked.
    public void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("Letter. \nSave, load and write your compositions with ease.");
        alert.show();
    }

    private boolean isBold = false;

    // Requires: Highlighted area the client wants to bold.
    // Modifies: text in textArea.
    // Effects: Bolds the highlighted text when pressed.
    public void boldText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-weight: bold");
    }


    // Requires: Highlighted area client wants to italicize.
    // Modifies: text in textArea.
    // Effects: italicizes the highlighted text when pressed.
    public void italicizeText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-style: italic");
    }


    // Requires: Highlighted area client wants to underline.
    // Modifies: text in textArea.
    // Effects: Underlines the highlighted text when pressed.
    public void underlineText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-underline: true");

    }

    // Requires: Highlighted area client wants to remove all styling.
    // Modifies: text
    // Effects:
    public void removeStyling() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-weight: initial");
    }

}
