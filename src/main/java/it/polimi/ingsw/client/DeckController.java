package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.DeckStatus;
import it.polimi.ingsw.comunication.TextMessage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Controller for deck.fxml
 */
public class DeckController implements Initializable, DisplayLabel {


    @FXML
    private Pane anchor;
    @FXML
    private Label deck1Owner;
    @FXML
    private Label deck2Owner;
    @FXML
    private Label deck3Owner;
    @FXML
    private Label deck4Owner;

    /**
     * Flag to stop the thread
     */
    private boolean exit;


    /**
     * Asks the server for the list of assistant card decks and sets the name of the player if a deck has already been chosen
     * Starts a thead listening for incoming messages
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientInput clientInput = ClientInput.getInstance();
        clientInput.sendString("sendAssistantDecks", "");
        TextMessage response = clientInput.readLine();
        Gson gson = new Gson();
        DeckStatus[] deckStatusArrayList = gson.fromJson(response.message, DeckStatus[].class);
        deck1Owner.setText(deckStatusArrayList[0].playerName);
        deck2Owner.setText(deckStatusArrayList[1].playerName);
        deck3Owner.setText(deckStatusArrayList[2].playerName);
        deck4Owner.setText(deckStatusArrayList[3].playerName);
        Thread readThread = new Thread(() -> {
            exit = false;
            while (!exit) {
                TextMessage message = clientInput.readLine();
                if (message != null) {
                    Platform.runLater(() -> {
                        if (Objects.equals(message.type, "confirmation") && Objects.equals(message.context, "chooseDeck")) {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, anchor.getScene().getWindow(), "Deck", message.message + "\nNow wait for your turn to begin!");
                            anchor.setDisable(true);
                        } else if (Objects.equals(message.type, "notify") && !message.message.contains("logged in")) {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, anchor.getScene().getWindow(), "Turn Notification", message.message);
                            try {
                                setTable();
                                exit = true;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (Objects.equals(message.type, "error")) {
                            if (Objects.equals(message.context, "chooseDeck")) {
                                anchor.setDisable(false);
                                AlertHelper.showAlert(Alert.AlertType.WARNING, anchor.getScene().getWindow(), "Invalid Deck", message.message);
                            } else {
                                AlertHelper.showAlert(Alert.AlertType.ERROR, anchor.getScene().getWindow(), "Error", message.message);
                            }
                        } else if (Objects.equals(message.type, "quit")) {
                            quit();
                        } else if (Objects.equals(message.type, "endGame")) {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, anchor.getScene().getWindow(), "Every other player disconnected", message.message + "\nPlease close the application");
                            anchor.setDisable(true);
                            exit = true;
                        }
                    });
                } else Platform.runLater(this::quit);
                try {
                    Thread.sleep(400);

                } catch (InterruptedException e) {
                    System.out.println("Thread stopped");
                }
            }
        });
        readThread.start();

    }

    /**
     * Loads and shows dashboard scene
     *
     * @throws IOException If loading the scene an exception occurred
     */
    private void setTable() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 810.0, 1270.0);
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(scene);
        stage.setHeight(900.);
        stage.setWidth(1400.0);
        stage.show();


    }

    private void quit() {
        exit = true;
        anchor.setDisable(true);
        AlertHelper.showAlert(Alert.AlertType.ERROR, anchor.getScene().getWindow(), "Server connection error", "Server unreachable, please close the application");
    }


    /**
     * Selects deck 1
     *
     */
    @FXML
    private void chooseDeck1(ActionEvent actionEvent) {
        chooseDeck(actionEvent, 1);
    }

    /**
     * Selects deck 2
     *
     */
    @FXML
    private void chooseDeck2(ActionEvent actionEvent) {
        chooseDeck(actionEvent, 2);
    }

    /**
     * Selects deck 3
     *
     */
    @FXML
    private void chooseDeck3(ActionEvent actionEvent) {
        chooseDeck(actionEvent, 3);
    }

    /**
     * Selects deck 4
     *
     */
    @FXML
    private void chooseDeck4(ActionEvent actionEvent) {
        chooseDeck(actionEvent, 4);
    }

    private void chooseDeck(ActionEvent actionEvent, int i) {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(i));
    }
}

