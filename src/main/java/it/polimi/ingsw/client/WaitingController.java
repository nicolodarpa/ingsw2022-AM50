package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WaitingController implements Initializable{

    @FXML
    Label waiting;

    @FXML
    ProgressBar progress;



    public void checkPhasePage() throws IOException{
        TextMessage message = ClientInput.getInstance().readLine();
        while(!Objects.equals(message.type, "notify")){
            message = ClientInput.getInstance().readLine();
        }
        Window owner = Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assistantCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) owner;
        stage.setScene(scene);
        stage.show();
    }





    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
