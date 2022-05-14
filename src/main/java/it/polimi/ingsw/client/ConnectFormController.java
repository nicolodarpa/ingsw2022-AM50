package it.polimi.ingsw.client;

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
import java.io.PrintWriter;
import java.net.Socket;


public class ConnectFormController {
    @FXML
    private TextField ip_input;
    @FXML
    private TextField port_input;
    @FXML
    private Button connectButton;


    @FXML
    protected void connectButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("selection_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Window owner = connectButton.getScene().getWindow();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Socket socket;
        String ip = ip_input.getText();
        int port = Integer.parseInt(port_input.getText());
        try {
            socket = new Socket(ip, port);
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            ClientInput.getInstance().setSocketOut(socketOut);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Connection", "Connection failed");

        }


    }
}
