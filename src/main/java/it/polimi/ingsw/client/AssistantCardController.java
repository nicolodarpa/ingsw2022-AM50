package it.polimi.ingsw.client;

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
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AssistantCardController implements Initializable {

    private String username;

    @FXML
    private Label yourDeck;

    private Parent root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setActionPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setUsername(String username){
        this.username = username;
    }


    public void alertChosenCard(ActionEvent actionEvent, int order) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        ClientInput.getInstance().readLine();
        TextMessage message = ClientInput.getInstance().readLine();
        if(Objects.equals(message.type, "error")){
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Select card", "Select another card to play");
            AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Invalid Card", "Card already played");
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, window, "Card chosen", "Card #" + order + " chosen");
            setActionPage(actionEvent);
        }
    }

    public void playAssistantCard1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(1));
        alertChosenCard(actionEvent, 1);
    }


    public void playAssistantCard2(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(2));
        alertChosenCard(actionEvent, 2);
    }


    public void playAssistantCard3(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(3));
        alertChosenCard(actionEvent, 3);
    }


    public void playAssistantCard4(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(4));
        alertChosenCard(actionEvent, 4);
    }


    public void playAssistantCard5(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(5));
        alertChosenCard(actionEvent, 5);
    }


    public void playAssistantCard6(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(6));
        alertChosenCard(actionEvent, 6);
    }


    public void playAssistantCard7(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(7));
        alertChosenCard(actionEvent, 7);
    }


    public void playAssistantCard8(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(8));
        alertChosenCard(actionEvent, 8);
    }



    public void playAssistantCard9(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(9));
        alertChosenCard(actionEvent, 9);
    }


    public void playAssistantCard10(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(10));
        alertChosenCard(actionEvent, 10);
    }

}
