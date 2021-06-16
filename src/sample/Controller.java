package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
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

    public TextArea textEditor;

    public void initialize() {
        textEditor.setWrapText(true);

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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void onInfoIcon(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.flaticon.com/free-icon/text-editor_196308").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
