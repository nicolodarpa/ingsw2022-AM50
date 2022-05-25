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

import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


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


    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshGames();
        gamesList.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> selectedGame = String.valueOf(gamesList.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    private void startGame(javafx.event.ActionEvent actionEvent) throws IOException {
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


    @FXML
    protected void joinGameButton(ActionEvent actionEvent) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        ClientInput.getInstance().sendString("joinGame", selectedGame);
        TextMessage message = ClientInput.getInstance().readLine();
        AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Game", message.message);
        setLoginPage(actionEvent);

    }

    @FXML
    private void refreshGames() {
        ClientInput.getInstance().sendString("avlGames", "");
        TextMessage message = ClientInput.getInstance().readLine();
        Gson gson = new Gson();
        if (!Objects.equals(message.type, "error")) {
            gamesList.getItems().clear();
            GameStatus[] gameStatuses = gson.fromJson(message.message, GameStatus[].class);
            int i = 0;
            for (GameStatus gameStatus : gameStatuses) {
                gamesList.getItems().add("-" + i + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players:" + gameStatus.playersName);
                i++;
            }
        }

    }

    private void setLoginPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
