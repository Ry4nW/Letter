package textEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("editorGUI.fxml"));
        primaryStage.getIcons().add(new Image("https://image.flaticon.com/icons/png/512/196/196308.png"));
        primaryStage.setTitle("Letter");

        // primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root, 700, 700);
        scene.getStylesheets().add("css/stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
