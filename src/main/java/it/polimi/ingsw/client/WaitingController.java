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
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class WaitingController implements DisplayLabel{

    @FXML
    Label waiting;

    @FXML
    ProgressBar progress;

    @FXML
    private Label usernameLabel;

    private String username;

    /*public void setPhasePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assistantCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AssistantCardController assistantCardController = fxmlLoader.getController();
        assistantCardController.displayLabel("Username", assistantCardController.getUsernameLabel(), username);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }*/

    @Override
    public void displayLabel(@NotNull String text, Label label, String textLabel) {
        DisplayLabel.super.displayLabel(text, label, textLabel);
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public void checkPhasePage(ActionEvent actionEvent) throws IOException{
        TextMessage message = ClientInput.getInstance().readLine();
        while(!Objects.equals(message.type, "notify")){
            message = ClientInput.getInstance().readLine();
        }
        //setPhasePage(actionEvent);

    }





}
