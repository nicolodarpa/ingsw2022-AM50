package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;


public class LoginController {

    @FXML
    private TextField name_input;

    @FXML
    private Button loginButton;

    @FXML Button lobbyButton;



    @FXML
    protected void submitLogin(ActionEvent actionEvent) throws IOException {
        String name = name_input.getText();
        ClientInput.getInstance().sendString("login", name);
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        TextMessage message = ClientInput.getInstance().readLine();
        if (!Objects.equals(message.type, "msg")) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Login", message.message);
        } else{
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Login", message.message);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deck.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void setSelectionFormController(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("selection_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}

