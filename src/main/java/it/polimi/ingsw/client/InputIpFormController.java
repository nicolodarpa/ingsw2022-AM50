package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.net.Socket;


public class InputIpFormController {
    @FXML
    private TextField ip_input;
    @FXML
    private TextField port_input;
    @FXML
    private Button connectButton;



    public void connectButton(ActionEvent actionEvent) {
        Window owner = connectButton.getScene().getWindow();
        Socket socket;
        String ip = ip_input.getText();
        int port = Integer.parseInt(port_input.getText());
        try {
            socket = new Socket(ip, port);
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Connection", "Successfully connected");
        } catch (Exception e){
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Connection", "Connection failed");

        }


    }
}
