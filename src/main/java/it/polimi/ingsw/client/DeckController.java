package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.DeckStatus;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeckController implements Initializable {

    @FXML
    private Label choose;
    @FXML
    protected Label deck1Owner;
    @FXML
    protected Label deck2Owner;
    @FXML
    protected Label deck3Owner;
    @FXML
    protected Label deck4Owner;

    private String username;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientInput.getInstance().sendString("sendAssistantDecks", "");
        TextMessage message = ClientInput.getInstance().readLine();
        Gson gson = new Gson();
        DeckStatus[] deckStatusArrayList = gson.fromJson(message.message, DeckStatus[].class);
        deck1Owner.setText(deckStatusArrayList[0].playerName);
        deck2Owner.setText(deckStatusArrayList[1].playerName);
        deck3Owner.setText(deckStatusArrayList[2].playerName);
        deck4Owner.setText(deckStatusArrayList[3].playerName);
    }


    public void setPhasePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assistantCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setWaitingPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setUsername(String username){
        this.username = username;
    }

    @FXML
    public void alertChosenDeck(ActionEvent actionEvent, int deckNumber) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        TextMessage message = ClientInput.getInstance().readLine();
        if (Objects.equals(message.type, "error")) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Deck", "Deck already chosen by another player");
        } else {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Deck", "Deck #" + deckNumber + " chosen");
            setPhasePage(actionEvent);
        }


    }

    @FXML
    public void chooseDeck1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(1));
        alertChosenDeck(actionEvent, 1);
    }

    @FXML
    public void chooseDeck2(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(2));
        alertChosenDeck(actionEvent, 2);
    }

    @FXML
    public void chooseDeck3(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(3));
        alertChosenDeck(actionEvent, 3);
    }

    @FXML
    public void chooseDeck4(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(4));
        alertChosenDeck(actionEvent, 4);
    }

}

