package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;


public class Login {

    @FXML
    private TextField name_input;

    @FXML
    private Button loginButton;

    @FXML
    protected void submitLogin(ActionEvent actionEvent) {
        String name = name_input.getText();
        ClientInput.getInstance().sendString("login",name);
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Login","Welcome " + name);

    }
}
