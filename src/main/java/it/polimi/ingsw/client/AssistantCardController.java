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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AssistantCardController implements Initializable, DisplayLabel {

    @FXML
    private Label usernameLabel;

    private String username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void displayLabel(@NotNull String text, Label label, String textLabel) {
        DisplayLabel.super.displayLabel(text, label, textLabel);
        this.username = textLabel;
    }

    public Label getUsernameLabel(){
        return usernameLabel;
    }

    public void setActionPage(ActionEvent actionEvent, int order, int movesOfMN) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        DashboardController dashboardController = fxmlLoader.getController();
        dashboardController.displayLabel("Username", dashboardController.getUsernameLabel(), username);
        dashboardController.displayLabel("Order", dashboardController.getOrderLabel(), String.valueOf(order));
        dashboardController.displayLabel("Moves of MN", dashboardController.getMovesOfMnLabel(), String.valueOf(movesOfMN));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void alertChosenCard(ActionEvent actionEvent, int order, int movesOfMN) throws IOException {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        while (true) {
            TextMessage message = ClientInput.getInstance().readLine();
            if (!Objects.equals(message, null))
                if(Objects.equals(message.type, "notify")){
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Turn Notification", message.message);
                    setActionPage(actionEvent, order, movesOfMN);
                    break;
                /*if (Objects.equals(message.message, "Your turn started")) {
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, window, "Turn Notification", message.message);
                    setActionPage(actionEvent, order, movesOfMN);
                    break;*/
                } else if (Objects.equals(message.type, "error")) {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Invalid card", message.message);
                    break;
                }
        }
    }

    public void playAssistantCard1(ActionEvent actionEvent) throws IOException {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(1));
        alertChosenCard(actionEvent, 1, 1);
    }


    public void playAssistantCard2(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(2));
        alertChosenCard(actionEvent,2,1);
    }


    public void playAssistantCard3(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(3));
        alertChosenCard(actionEvent, 3, 2);
    }


    public void playAssistantCard4(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(4));
        alertChosenCard(actionEvent, 4, 2);
    }


    public void playAssistantCard5(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(5));
        alertChosenCard(actionEvent, 5, 3);
    }


    public void playAssistantCard6(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(6));
        alertChosenCard(actionEvent, 6, 3);
    }


    public void playAssistantCard7(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(7));
        alertChosenCard(actionEvent,7,4);
    }


    public void playAssistantCard8(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(8));
        alertChosenCard(actionEvent,8,4);
    }



    public void playAssistantCard9(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(9));
        alertChosenCard(actionEvent,9,5);
    }


    public void playAssistantCard10(ActionEvent actionEvent) throws IOException{
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(10));
        alertChosenCard(actionEvent,10,5);
    }

}
