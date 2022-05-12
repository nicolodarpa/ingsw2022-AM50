package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.stage.Window;


public class SelectionFormController {

    @FXML
    private Button joinGameButton;
    @FXML
    private Button newGameButton;


    @FXML
    protected void newGameButton(ActionEvent actionEvent) {
        Window owner = newGameButton.getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "New Game Title", "Start a new game");

    }

    @FXML
    protected void joinGameButton(ActionEvent actionEvent){
        Window window = joinGameButton.getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Join Game Title","Join game");
    }
}
