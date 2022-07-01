package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.GameStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.server.model.Game;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Controller for selectionForm.fxml
 */
public class SelectionFormController implements Initializable {

    @FXML
    private ToggleGroup numberOfPlayers;
    @FXML
    private ListView<String> gamesList;
    @FXML
    private Button joinGameButton;
    @FXML
    private String selectedGame;

    @FXML
    private RadioButton threePlayersRadio, twoPlayersRadio;
    @FXML
    private Button startGame;

    private boolean exit;

    private TextMessage message;

    private final ClientInput clientInput = ClientInput.getInstance();

    @FXML
    private GridPane anchor;


    /**
     * Gets the list of available games from the server, sets a listens to the gamesList ListView
     * Starts a thread that reads incoming messages from the server
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Gson gson = new Gson();

        gamesList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> selectedGame = String.valueOf(gamesList.getSelectionModel().getSelectedIndex()));
        Thread readThread = new Thread(() -> {
            exit = false;
            while (!exit) {
                message = clientInput.readLine();
                if (message != null) {
                    Platform.runLater(() -> {
                        switch (message.type) {
                            case "avlGames" -> {
                                gamesList.getItems().clear();
                                GameStatus[] gameStatuses = gson.fromJson(message.message, GameStatus[].class);
                                for (GameStatus gameStatus : gameStatuses) {
                                    gamesList.getItems().add("-" + gameStatus.gameId + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players:" + gameStatus.playersName);
                                }
                            }
                            case "confirmation" -> {
                                AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, anchor.getScene().getWindow(), "Confirmation", message.message);
                                exit = true;
                            }
                            case "quit" ->
                              closeWindow();

                        }
                    });
                }else Platform.runLater(this::closeWindow);
                try {
                    Thread.sleep(400);

                } catch (InterruptedException e) {
                    System.out.println("Thread stopped");
                }
            }
        });
        readThread.start();
        refreshGames();

    }


    /**
     * Start a game for the selected number of players
     */
    @FXML
    private void startGame(ActionEvent actionEvent) throws IOException {
        int numberOfPlayers = 0;
        if (threePlayersRadio.isSelected()) {
            numberOfPlayers = Integer.parseInt(threePlayersRadio.getText());
        } else if (twoPlayersRadio.isSelected()) {
            numberOfPlayers = Integer.parseInt(twoPlayersRadio.getText());
        }
        System.out.println("start new game, #p :" + numberOfPlayers);
        ClientInput.getInstance().sendString("newGame", String.valueOf(numberOfPlayers));
        setLoginPage(actionEvent);
    }


    /**
     * Sends a message to the server to join the selected game
     */
    @FXML
    protected void joinGameButton(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("joinGame", selectedGame);
        setLoginPage(actionEvent);

    }


    /**
     * Send a message to the server asking for the list of available games.
     * If the response is positive populates the gameList ListView with the available games
     */
    @FXML
    private void refreshGames() {
        clientInput.sendString("avlGames", "");
    }


    /**
     * Gets a message from the server via {@link ClientInput}.
     * If the message received indicates a connection error calls closeWindow
     *
     * @return the message from the server as a {@link TextMessage}
     */
    private TextMessage getServerMessage() {
        TextMessage response = ClientInput.getInstance().readLine();
        if (Objects.equals(response.type, "quit")) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, anchor.getScene().getWindow(), "Connection error", "Error connecting to the server, please close the application");
            anchor.setDisable(true);
        }
        return response;
    }

    /**
     * Shows an alert and close the window
     */
    private void closeWindow() {
        exit = true;
        AlertHelper.showAlert(Alert.AlertType.ERROR, anchor.getScene().getWindow(), "Connection error", "Error connecting to the server, please close the application");
        anchor.setDisable(true);
    }

    /**
     * Sets the login page scene
     *
     * @throws IOException If loading the scene an exception occurred
     */
    private void setLoginPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
