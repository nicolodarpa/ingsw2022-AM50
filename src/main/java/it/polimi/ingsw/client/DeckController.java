package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;

public class DeckController {


    public void setPhasePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assistantCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void alertChosenDeck(ActionEvent actionEvent, int deckNumber) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        TextMessage message = ClientInput.getInstance().readLine();
        if (Objects.equals(message.type, "error")) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Deck", "Deck already chosen by another player");
        } else {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Deck", "Deck #" + deckNumber + " chosen" );
            setPhasePage(actionEvent);
        }

    }
    @FXML
    public void chooseDeck1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(1));
        alertChosenDeck(actionEvent,1);
    }
    @FXML
    public void chooseDeck2(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(2));
        alertChosenDeck(actionEvent,2);
    }
    @FXML
    public void chooseDeck3 (ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(3));
        alertChosenDeck(actionEvent,3);
    }

    @FXML
    public void chooseDeck4 (ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(4));
        alertChosenDeck(actionEvent,4);
    }
}

