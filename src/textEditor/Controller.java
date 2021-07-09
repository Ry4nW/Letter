package textEditor;

import IO.IO;
import IO.IOResult;

import com.teamdev.jxbrowser.navigation.internal.rpc.Index;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.fxmisc.richtext.InlineCssTextArea;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Controller {

    public MenuItem githubMI;
    public MenuItem iconMI;
    public MenuItem aboutMI;

    private final InlineCssTextArea textArea = new InlineCssTextArea();

    public Label fileMessage;

    public Button underlineButton;
    public Button italicsButton;
    public Button boldButton;
    public Button removeStylingBtn;
    public Button clearAllBtn;
    public Button undoBtn;

    public IO model = new IO(); // Class for our IO system.
    public BorderPane borderPane;
    public MenuButton fontSizeMenu;
    public MenuButton setFontMenu;
    public TextField fileNameField;
    private TextFile currentTextFile;
    private ArrayList<String> autoSaveText = new ArrayList<>(); // will auto save text from {textArea} when {clearTextArea} is executed.
    private ArrayList<String> onCloseSaveCheck = new ArrayList<>(); // will auto save text when client saves and checks when closing the application.
    // when the client decides to execute {clearTextArea}.

    // Requires: Nothing.
    // Modifies: Letter.
    // Effects: Sets wrap text for our editor's text area, aka creates a newline for the user to type in
    // once the end of the textEditor's width is reached, border, border style/width, button disabling to
    // prevent errors and other features on initialization.
    public void initialize() {

        removeStylingBtn.setDisable(true);
        undoBtn.setDisable(true);
        fileMessage.setText("");

        borderPane.centerProperty().setValue(textArea); // Adds our text area to the center of the borderPane.

        textArea.setWrapText(true);
        textArea.setStyle("-fx-border-style: solid; -fx-border-width: 1px; -fx-padding: 15px;  -fx-border-color: grey; " +
                "-fx-border-radius: 1px; -fx-fill: transparent; -fx-font-family: Arial; -fx-font-size: 11px; -fx-outline: thick solid #00ff00;");

    }

    // Requires: Nothing.
    // Modifies: ArrayList<String> onCloseSaveCheck.
    // Effects: Clears the ArrayList and updates it with textArea's current text whenever the client decides to save.
    // This will allow us to check whether the latest version of the client's work is saved.
    public void setOnCloseSaveCheck() {
        onCloseSaveCheck.clear();
        onCloseSaveCheck.add(textArea.getText());
    }

    // Requires: Nothing.
    // Modifies: textArea.
    // Effects: Saves all text from {textEditor} into a file through save() in the {IO} class. If a NullPointerException
    // occurs (aka the user did not load an existing .txt file to edit), it will allow the user to create a new one.
    // Prints StackTrace if all else fails.
    public void onSave() {

        try {

            TextFile textFile = new TextFile(currentTextFile.getFile(), Arrays.asList(textArea.getText().split("\n")));
            model.save(textFile);
            fileMessage.setText(fileNameField.getText() + " was successfully saved.");

        } catch (NullPointerException bruh /* NullPointerException occurs when we try to fetch {currentTextFile.getFile()} and there is no current file loaded. */) {

            try {

                final String content = textArea.getText();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Text as .txt");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Document (*.txt)", ".txt");
                fileChooser.getExtensionFilters().add(extFilter);
                fileChooser.setInitialFileName(fileNameField.getText());

                File file = fileChooser.showSaveDialog(null);


                // This will write the text in {textArea} into the newly created file.
                try (final BufferedWriter writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                    writer.write(content);
                    writer.flush();
                    fileMessage.setText("Text successfully saved as " + file.getName() + ". Please open that file to continue working on it.");
                } catch (NullPointerException e) {
                    fileMessage.setText("You did not save your text as a new file.");
                }

            } catch (Exception bruh2) {
                fileMessage.setText("File save failed.");
                bruh2.printStackTrace();
            }

        }

        setOnCloseSaveCheck();

    }


    // Requires: Nothing.
    // Modifies: textArea.
    // Effects: Loads all text from an existing .txt file through load() in the {IO} class. Appends all text from
    // the file to {textArea}, allowing the user to edit and update the same file.
    public void onLoad() throws MalformedInputException {

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
                fileNameField.setText(file.getName());

            } else {
                fileMessage.setText("File load failed. Please make sure that you're loading from a .txt or source code file.");
            }
        }
    }


    // Requires: Nothing.
    // Modifies: Nothing.
    // Effects: Terminates the process if latest save is consistent with text in {textArea}, else asks user for
    // confirmation.
    public void onClose() {

        try {

            if (!textArea.getText().equals("") && onCloseSaveCheck.size() == 0 && textArea.getText().equals(autoSaveText.get(0))) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Letter");
                alert.setHeaderText("Exit");
                alert.setContentText("Save " + fileNameField.getText() + " before exiting?");

                if (alert.showAndWait().get() == ButtonType.OK) {
                    onSave();
                    return;
                }

            }

        } catch (IndexOutOfBoundsException e) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Letter");
            alert.setHeaderText("Exit");
            alert.setContentText("Save " + fileNameField.getText() + " before exiting?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                onSave();
                return;
            }

        }

        System.exit(0);

    }



    // Requires: Nothing.
    // Modifies: Clients browser.
    // Effects: Opens a link on the client's default browser to the Github page of the application.
    public void onInfoGithub() {

        try {
            Desktop.getDesktop().browse(new URL("https://github.com/Ry4nW/Text-Editor").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }


    // Requires: Nothing.
    // Modifies: Client's browser.
    // Effects: Opens a link on the client's default browser to the application's Icon "provider".
    public void onInfoIcon() {

        try {
            Desktop.getDesktop().browse(new URL("https://www.flaticon.com/free-icon/text-editor_196308").toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }


    // Requires: Nothing.
    // Modifies: Client's browser.
    // Effects: Inserts a popup serving as an "About Page" when clicked.
    public void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("Letter. \nSave, load and write your compositions with ease.");
        alert.show();
    }


    // Requires: Highlighted text the client wants to bold.
    // Modifies: Highlighted text in textArea.
    // Effects: Bolds any highlighted text when executed.
    public void boldText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-weight: bold; " + setCurrentStyling());
        removeStylingBtn.setDisable(false);
    }


    // Requires: Highlighted text client wants to italicize.
    // Modifies: Highlighted text in textArea.
    // Effects: Bolds any highlighted text when executed.
    public void italicizeText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-style: italic; " + setCurrentStyling());
        removeStylingBtn.setDisable(false);
    }


    // Requires: Highlighted text client wants to underline.
    // Modifies: Highlights text in textArea.
    // Effects: Bolds any highlighted text when executed.
    public void underlineText() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-underline: true; " + setCurrentStyling());
        removeStylingBtn.setDisable(false);
    }

    // Requires: Highlighted text client wants to remove all styling.
    // Modifies: Highlighted text in textArea.
    // Effects: Bolds any highlighted text when executed.
    public void removeStyling() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-weight: initial; " + setCurrentStyling());
        removeStylingBtn.setDisable(false);
    }


    // Requires: Nothing.
    // Modifies: textArea.
    // Effects: Completely empties the textArea.
    public void clearTextArea() {

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
    public void onUndo() {

        fileMessage.setText("");

        StringBuilder stringBuilder = new StringBuilder();

        for (String str : autoSaveText) {
            stringBuilder.append(str).append("\n");
        }

        textArea.appendText(stringBuilder.toString());
        undoBtn.setDisable(true);
        fileMessage.setText("Text successfully restored.");

    }

    // Requires: Nothing.
    // Modifies: Highlighted text client wants to change font.
    // Effects: Changes the font for highlighted text.
    public void setArialFont() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-family: Arial, sans-serif; -fx-font-size: " + fontSizeMenu.getText() + ";");
        setFontMenu.setText("Arial");
    }

    public void setHelveticaFont() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-family: Helvetica, sans-serif; -fx-font-size: " + fontSizeMenu.getText() + ";");
        setFontMenu.setText("Helvetica");
    }

    public void setTimesNewRomanFont() {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-family: \"Times New Roman\", serif; -fx-font-size: " + fontSizeMenu.getText() + ";");
        setFontMenu.setText("\"Times New Roman\"");
    }

    // Requires: Nothing.
    // Modifies: Highlighted text client wants to change font size.
    // Effects: Changes the selected font size for highlighted text.
    public void setFontSize10(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 10px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("10");
    }

    public void setFontSize11(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 11px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("11");
    }

    public void setFontSize12(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 12px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("12");
    }

    public void setFontSize13(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 14px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("14");
    }

    public void setFontSize14(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 16px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("16");
    }

    public void setFontSize15(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 24px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("24");
    }

    public void setFontSize16(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 48px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("48");
    }

    public void setFontSize17(ActionEvent actionEvent) {
        IndexRange selection = textArea.getSelection();
        textArea.setStyle(selection.getStart(), selection.getEnd(), "-fx-font-size: 72px; -fx-font-family: " + setFontMenu.getText() + ";");
        fontSizeMenu.setText("72");
    }

    // Requires: Nothing.
    // Modifies: Whatever is being styled.
    // Effects: Returns current styling for font-family and font-size.
    public String setCurrentStyling() {
        return "-fx-font-size: " + fontSizeMenu.getText() +"px; -fx-font-family: " + setFontMenu.getText() + ";";
    }
}
