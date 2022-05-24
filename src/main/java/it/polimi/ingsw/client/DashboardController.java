package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    private Counter movesAvailable;
    private OrderOfPlayer orderOfGame;


    @FXML
    private Label movesAvailableCounter;

    @FXML
    private Label roundCounter;

    @FXML
    private Label order;


    /**
     * Open a window and show the message : "You run out of available moves"
     * @param actionEvent
     */
    public void alertRunOut(ActionEvent actionEvent){
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Finish moves", "You run out of available moves");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.movesAvailable = new Counter();
        roundCounter.setText(String.valueOf(1)); //set the round at the beginning of a new match
        this.orderOfGame = new OrderOfPlayer();
    }

    public void moveStudentToClassroom(ActionEvent actionEvent) {
        if(movesAvailable.getCounter() > 0){
            this.movesAvailable.decrement();
            movesAvailableCounter.setText(movesAvailable.toString());
        }
        else{
            alertRunOut(actionEvent); //show an alert when you finish the moves
        }

    }

    public void moveStudentToIsland(ActionEvent actionEvent) {
        if(movesAvailable.getCounter() > 0){
            this.movesAvailable.decrement();
            movesAvailableCounter.setText(movesAvailable.toString());
        }
        else{
            alertRunOut(actionEvent); //show an alert when you finish the moves
        }
    }


    public void playCharacterCard(ActionEvent actionEvent) {
    }


}
