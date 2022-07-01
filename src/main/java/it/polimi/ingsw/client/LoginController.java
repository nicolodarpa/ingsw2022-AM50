package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;


/**
 * Controller for login.fxml
 */
public class LoginController {


    /**
     * Text field to input the username
     */
    @FXML
    private TextField name_input;


    /**
     * Load and shows the scene for deck selection
     *
     * @param actionEvent action event of the button
     * @throws IOException If loading the scene an exception occurred
     */
    private void setChoosingDeckPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("deck.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Load and shows the dashboard scene
     *
     * @param actionEvent action event of the button
     * @throws IOException If loading the scene an exception occurred
     */
    private void setDashboardPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Send a message to the server to log in with the username typed by the user.
     * Shows an error alert if the response from the server is negative
     */
    @FXML
    private void submitLogin(ActionEvent actionEvent) throws IOException {
        String name = name_input.getText();
        ClientInput.getInstance().sendString("login", name);
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        TextMessage message = ClientInput.getInstance().readLine();
        if (Objects.equals(message.type, "quit")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Connection error", "Error connecting to the server, please close the application");
        } else if (!Objects.equals(message.type, "confirmation")) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Login", message.message);
        } else {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Login", message.message);
            if (Objects.equals(message.context, "login")) {
                setChoosingDeckPage(actionEvent);
            } else setDashboardPage(actionEvent);
        }
    }


    /**
     * Load the selection form scene
     *
     * @throws IOException If loading the scene an exception occurred
     */
    @FXML
    public void setSelectionFormController(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("selection_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}

