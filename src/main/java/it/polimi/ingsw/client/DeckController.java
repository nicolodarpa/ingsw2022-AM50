package it.polimi.ingsw.client;

import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.Notifications;
import com.github.plushaze.traynotification.notification.TrayNotification;
import com.google.gson.Gson;
import it.polimi.ingsw.comunication.DeckStatus;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class DeckController implements Initializable, DisplayLabel {

    @FXML
    private Label usernameLabel;
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

    @Override
    public void displayLabel(@NotNull String text, Label label, String textLabel) {
        DisplayLabel.super.displayLabel(text, label, textLabel);
        this.username = textLabel;
    }

    public Label getUsernameLabel(){
        return usernameLabel;
    }

    public void setPhasePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("assistantCard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AssistantCardController assistantCardController = fxmlLoader.getController();
        assistantCardController.displayLabel("Username", assistantCardController.getUsernameLabel(), username);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void alertChosenDeck(ActionEvent actionEvent) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        while(true){
            TextMessage message = ClientInput.getInstance().readLine();
            if(!Objects.equals(message, null))
                if (Objects.equals(message.type, "notify")) {
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Turn Notification" , message.message);
                    setPhasePage(actionEvent);
                    break;
                } else if(Objects.equals(message.type, "error")){
                    AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Invalid Deck", message.message);
                    break;
                }
        }
    }



    @FXML
    public void chooseDeck1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(1));
        alertChosenDeck(actionEvent);
    }

    @FXML
    public void chooseDeck2(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(2));
        alertChosenDeck(actionEvent);
    }

    @FXML
    public void chooseDeck3(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(3));
        alertChosenDeck(actionEvent);
    }

    @FXML
    public void chooseDeck4(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("chooseDeck", String.valueOf(4));
        alertChosenDeck(actionEvent);
    }

    public void setWaitingPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("waiting.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}

