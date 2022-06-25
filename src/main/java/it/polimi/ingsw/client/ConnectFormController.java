package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Controller of input_ip_form.fxml
 */
public class ConnectFormController {

    /**
     * Text filed to input an ip address
     */
    @FXML

    private TextField ip_input;
    /**
     * Text filed to input a port number
     */
    @FXML
    private TextField port_input;
    @FXML
    private Button connectButton;


    private Parent root;


    /**
     * Read the text present in the text fields and connects to the chosen ip and port.
     * Creates a socket and sets the PrintWrite and BufferedRead in the {@link ClientInput} instance.
     * Loads the next scene
     *
     * @param actionEvent default parameter
     * @throws IOException If creating the socket an exception occurred
     */

    @FXML
    protected void connectButton(ActionEvent actionEvent) throws IOException {

        Window owner = connectButton.getScene().getWindow();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Socket socket = null;
        String ip = ip_input.getText();
        int port = Integer.parseInt(port_input.getText());
        try {
            socket = new Socket(ip, port);
            if (!socket.isClosed()) {
                PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
                BufferedReader socketIn = new BufferedReader(new InputStreamReader((socket.getInputStream())));
                ClientInput.getInstance().setSocketOut(socketOut);
                ClientInput.getInstance().setSocketIn(socketIn);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("selection_form.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();
            }
            //new AlertThread().start();
        } catch (Exception e) {

            AlertHelper.showAlert(Alert.AlertType.WARNING, owner, "Connection", "Connection failed");

        }




    }
}
