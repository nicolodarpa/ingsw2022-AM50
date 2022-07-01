package it.polimi.ingsw.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

/**
 * Start the client GUI.
 */

public class ClientGUI extends Application {


    Stage mainStage = null;


    /**
     * Start the main stage for the GUI.
     * Load the first scene, sets the icon and the title of the application window.
     *
     * @param stage main stag for the graphic interface
     * @throws IOException In an exception with the graphic loading occurred
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.mainStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("input_ip_form.fxml")));

        Scene scene = new Scene(root);
        Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource("images/LOGO CRANIO CREATIONS_bianco.png")));

        stage.getIcons().add(icon);
        stage.setTitle("Eriantys GUI");
        stage.setScene(scene);
        stage.show();

        mainStage.setOnCloseRequest(evt -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to leave the game ? ", ButtonType.YES, ButtonType.NO);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            if (ButtonType.NO.equals(result)) {
                evt.consume();
            } else
                try {
                    ClientInput.getInstance().sendString("quit", "");

                } catch (Exception e) {
                    stage.close();
                }
            System.exit(0);
        });

    }

    public static void main() {
        launch();
    }
}


