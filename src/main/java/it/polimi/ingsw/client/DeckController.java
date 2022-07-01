package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.DeckStatus;
import it.polimi.ingsw.comunication.TextMessage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;


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
     *
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
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            exit= true;
                        } else if (Objects.equals(message.type, "error")  && Objects.equals(message.context, "chooseDeck")) {
                            anchor.setDisable(false);
                            AlertHelper.showAlert(Alert.AlertType.WARNING, anchor.getScene().getWindow(), "Invalid Deck", message.message);
                            exit = true;
                        } else if (Objects.equals(message.type, "quit")) {
                            quit();
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, anchor.getScene().getWindow(), message.type, message.message);
                        }
                    });
                }else Platform.runLater(this::quit);
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
        //stage.setMaximized(true);
        stage.show();


    }


    /**
     * Reads the response from the server after choosing a deck.
     * If the response is positive the dashboard view is shown else an error alert pops up
     *
     */
    @FXML
    private void alertChosenDeck(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();


    }


    private void quit(){
        exit = true;
        anchor.setDisable(true);
        AlertHelper.showAlert(Alert.AlertType.ERROR, anchor.getScene().getWindow(), "Connection error", "Error connecting to the server, please close the application");
    }


    /**
     * Selects deck 1
     *
     * @throws IOException If loading the scene an exception occurred
     */
    @FXML
    private void chooseDeck1(ActionEvent actionEvent) throws IOException {
        chooseDeck(actionEvent, 1);
    }

    /**
     * Selects deck 2
     *
     * @throws IOException If loading the scene an exception occurred
     */
    @FXML
    private void chooseDeck2(ActionEvent actionEvent) throws IOException {
        chooseDeck(actionEvent, 2);
    }

    /**
     * Selects deck 3
     *
     * @throws IOException If loading the scene an exception occurred
     */
    @FXML
    private void chooseDeck3(ActionEvent actionEvent) throws IOException {
        chooseDeck(actionEvent, 3);
    }

    /**
     * Selects deck 4
     *
     * @throws IOException If loading the scene an exception occurred
     */
    @FXML
    private void chooseDeck4(ActionEvent actionEvent) throws IOException {
        chooseDeck(actionEvent, 4);
    }

    private void chooseDeck(ActionEvent actionEvent, int i) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(i));
        alertChosenDeck(actionEvent);
    }
}

