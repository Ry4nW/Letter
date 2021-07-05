package textEditor;

import IO.IO;
import IO.IOResult;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

import org.fxmisc.richtext.InlineCssTextArea;


public class Controller {

    public MenuItem githubMI;
    public MenuItem iconMI;
    public MenuItem aboutMI;

    private final InlineCssTextArea textArea = new InlineCssTextArea();

    public Label fileMessage;
    public Label errorMessage;

    public Button underlineButton;
    public Button italicsButton;
    public Button boldButton;
    public Button removeStylingBtn;
    public Button clearAllBtn;
    public Button undoBtn;

    public IO model = new IO(); // Class for our IO system.
    public BorderPane borderPane;
    private TextFile currentTextFile;
    private ArrayList<String> autoSaveText = new ArrayList<>();

    // Requires: Nothing.
    // Modifies: textArea
    // Effects: Sets wrap text for our editor's text area, aka creates a newline for the user to type in
    // once the end of the textEditor's width is reached, border, border style/width and other styling.
    public void initialize() {

        removeStylingBtn.setDisable(true);
        undoBtn.setDisable(true);
        fileMessage.setText("");
        errorMessage.setText("");

        borderPane.centerProperty().setValue(textArea); // Adds our text area to the center of the borderPane.

        textArea.setWrapText(true);
        textArea.setStyle("-fx-border-color: black; -fx-border-style: solid; -fx-border-width: 2px");

    }


    // Requires: Nothing.
    // Modifies: Empty's the textArea.
    // Effects: Saves all text from {textEditor} into a file through save() in the {IO} class.
    public void onSave() throws IOException {

        try {

            TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(textArea.getText().split("\n")));
            model.save(textFile);
            fileMessage.setText(currentTextFile.getFile() + "was successfully saved.");

        } catch (NullPointerException exception) {

            try {

                final String content = textArea.getText();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Text as .txt");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
                File f = new File("Untitled.txt");

                fileChooser.showSaveDialog(null);
                final Path path = Paths.get(f.getPath());

                try (final BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                    writer.write(content);
                    writer.flush();
                }

                fileMessage.setText("Text successfully saved as " + f.getName());

            } catch (Exception e) {
                fileMessage.setText("File save failed.");
                e.printStackTrace();
            }

        }

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
                fileMessage.setText("Text successfully loaded from " + currentTextFile.getFile());

            } else {
                fileMessage.setText("File load failed. Please make sure that you're loading from a .txt file.");
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


    // Requires: Highlighted area the client wants to bold.
    // Modifies: text in textArea.
    // Effects: Bolds the highlighted text when pressed.
    public void boldText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-weight: bold");
        removeStylingBtn.setDisable(false);
    }


    // Requires: Highlighted area client wants to italicize.
    // Modifies: text in textArea.
    // Effects: italicizes the highlighted text when pressed.
    public void italicizeText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-style: italic");
        removeStylingBtn.setDisable(false);
    }


    // Requires: Highlighted area client wants to underline.
    // Modifies: text in textArea.
    // Effects: Underlines the highlighted text when pressed.
    public void underlineText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-underline: true");
        removeStylingBtn.setDisable(false);
    }

    // Requires: Highlighted area client wants to remove all styling.
    // Modifies: text
    // Effects:
    public void removeStyling() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-weight: initial");
        removeStylingBtn.setDisable(false);
    }


    // Requires: Nothing.
    // Modifies: textArea.
    // Effects: Clears the textArea
    public void clearTextArea(ActionEvent actionEvent) {

        fileMessage.setText("");

        if (textArea.getContent().length() != 0) {

            autoSaveText.clear();

            autoSaveText.add(textArea.getText());

            textArea.clear();
            undoBtn.setDisable(false);

        } else {
            fileMessage.setText("Text area is empty.");
        }

    }

    // Requires: A {clearTextArea} execution.
    // Modifies: textArea.
    // Effects: Undos a {clearTextArea} execution.
    public void onUndo(ActionEvent actionEvent) {

        StringBuilder stringBuilder = new StringBuilder();

        for (String str : autoSaveText) {
            stringBuilder.append(str).append("\n");
        }

        textArea.appendText(stringBuilder.toString());
        undoBtn.setDisable(true);
    }


}
