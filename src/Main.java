/**
 * Name:    Alon Vita
 * ID:      311233431
 * Github:  https://github.com/Alonvita
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Main Class -- Main.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start(Stage primaryStage).
     *
     * @param primaryStage Stage -- the primary stage of the program.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            HBox root = FXMLLoader.load(getClass().getResource("Board.fxml"));
            Scene scene = new Scene(root, 520, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setTitle("Reversi Game");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
