package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void alertChosenDeck(ActionEvent actionEvent) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        TextMessage message = ClientInput.getInstance().readLine();
        if (!Objects.equals(message.type, "msg")) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Deck", message.message);
        } else {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Deck", message.message);
            setPhasePage(actionEvent);
        }
    }

    public void chooseDeck1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(1));
        alertChosenDeck(actionEvent);
    }

    public void chooseDeck2(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(2));
        alertChosenDeck(actionEvent);
    }

    public void chooseDeck3 (ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(3));
        alertChosenDeck(actionEvent);
    }


    public void chooseDeck4 (ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(4));
        alertChosenDeck(actionEvent);
    }
}

