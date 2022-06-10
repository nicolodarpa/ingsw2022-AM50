package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.PlayersStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.Player;
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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AssistantCardController {





    public void alertChosenCard(ActionEvent actionEvent) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        window.hide();
    }

    public void playAssistantCard1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(1));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard2(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(2));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard3(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(3));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard4(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(4));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard5(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(5));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard6(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(6));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard7(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(7));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard8(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(8));
        alertChosenCard(actionEvent);
    }



    public void playAssistantCard9(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(9));
        alertChosenCard(actionEvent);
    }


    public void playAssistantCard10(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(10));
        alertChosenCard(actionEvent);
    }

}
