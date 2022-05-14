package it.polimi.ingsw.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;


import java.io.IOException;

public class NewGameForm {

    @FXML
    private RadioButton threePlayersRadio, twoPlayersRadio;
    @FXML
    private Button startGame;

    @FXML
    private void startGame(javafx.event.ActionEvent actionEvent) throws IOException {
        int numberOfPlayers = 0;
        if (threePlayersRadio.isSelected()) {
            numberOfPlayers = Integer.parseInt(threePlayersRadio.getText());
        } else if (twoPlayersRadio.isSelected()) {
            numberOfPlayers = Integer.parseInt(twoPlayersRadio.getText());
        }
        System.out.println("start new game, #p :" + numberOfPlayers);
        ClientInput.getInstance().sendString("newgame", String.valueOf(numberOfPlayers));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
}
