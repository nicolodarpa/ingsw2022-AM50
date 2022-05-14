package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class SelectionFormController {

    @FXML
    private Button joinGameButton;
    @FXML
    private Button newGameButton;


    @FXML
    protected void newGameButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newGame_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage  = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    protected void joinGameButton(ActionEvent actionEvent){
        Window window = joinGameButton.getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Join Game Title","Join game");
    }
}
