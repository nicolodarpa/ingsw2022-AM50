package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.util.Objects;

public class TurnController extends Thread{

    public void displayAlert(ActionEvent actionEvent, TextMessage message){
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.INFORMATION,window,"Turn notification", message.message);

    }

    public void run(){
        TextMessage message = ClientInput.getInstance().readLine();
        if(Objects.equals(message, "Your turn started")){
        }
    }

}
