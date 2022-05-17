package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.util.Objects;


public class LoginController {

    @FXML
    private TextField name_input;

    @FXML
    private Button loginButton;

    @FXML
    protected void submitLogin(ActionEvent actionEvent) {
        String name = name_input.getText();
        ClientInput.getInstance().sendString("login", name);
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        TextMessage message = ClientInput.getInstance().readLine();
        if (!Objects.equals(message.type, "msg")) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Login", message.message);
        } else AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Login", message.message);


    }
}
