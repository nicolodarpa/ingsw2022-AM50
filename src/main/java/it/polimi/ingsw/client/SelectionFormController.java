package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.GameStatus;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
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
        ClientInput.getInstance().sendString("joinGame", selectedGame);
        setLoginPage(actionEvent);

    }

    @FXML
    private void refreshGames(){
        ClientInput.getInstance().sendString("avlGames", "");
        BufferedReader socketIn = ClientInput.getInstance().getSocketIn();
        String msg;
        try {
            msg = socketIn.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        TextMessage message = gson.fromJson(msg, TextMessage.class);
        if (!Objects.equals(message.type, "error")) {
            gamesList.getItems().clear();
            GameStatus[] gameStatuses = gson.fromJson(message.message, GameStatus[].class);
            int i = 0;
            for (GameStatus gameStatus : gameStatuses) {
                gamesList.getItems().add("game # " + i + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players");
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
