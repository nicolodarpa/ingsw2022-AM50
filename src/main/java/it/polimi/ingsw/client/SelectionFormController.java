package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.GameStatus;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    @FXML
    private GridPane anchor;


    /**
     * Gets the list of available games from the server, sets a listens to the gamesList ListView
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshGames();
        gamesList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> selectedGame = String.valueOf(gamesList.getSelectionModel().getSelectedIndex()));
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
        TextMessage response = getServerMessage();
        setLoginPage(actionEvent);
    }


    /**
     * Sends a message to the server to join the selected game
     */
    @FXML
    protected void joinGameButton(ActionEvent actionEvent) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        ClientInput.getInstance().sendString("joinGame", selectedGame);
        TextMessage message = getServerMessage();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Game", message.message);
        setLoginPage(actionEvent);

    }


    /**
     * Send a message to the server asking for the list of available games.
     * If the response is positive populates the gameList ListView with the available games
     */
    @FXML
    private void refreshGames() {
        ClientInput.getInstance().sendString("avlGames", "");
        TextMessage response = getServerMessage();
        Gson gson = new Gson();
        if (Objects.equals(response.type, "avlGames")) {
            gamesList.getItems().clear();
            GameStatus[] gameStatuses = gson.fromJson(response.message, GameStatus[].class);
            for (GameStatus gameStatus : gameStatuses) {
                gamesList.getItems().add("-" + gameStatus.gameId + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players:" + gameStatus.playersName);
            }
        }

    }

    /**
     * Gets a message from the server via {@link ClientInput}.
     * If the message received indicates a connection error calls closeWindow
     * @return the message from the server as a {@link TextMessage}
     */
    private TextMessage getServerMessage() {
        TextMessage response = ClientInput.getInstance().readLine();
        if (Objects.equals(response.type, "quit")) {
            closeWindow();
        }
        return response;
    }

    /**
     * Shows an alert and close the window
     */
    private void closeWindow() {
        Window window = anchor.getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Connection error", "No connection to the server. Closing...");
        System.exit(0);
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
