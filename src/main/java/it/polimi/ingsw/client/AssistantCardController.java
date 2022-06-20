package it.polimi.ingsw.client;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Window;

/**
 * Controller of AssistantCard.fxml
 */
public class AssistantCardController {





    public void closePage(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        window.hide();
    }

    public void playAssistantCard1(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(1));
        closePage(actionEvent);
    }


    public void playAssistantCard2(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(2));
        closePage(actionEvent);
    }


    public void playAssistantCard3(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(3));
        closePage(actionEvent);
    }


    public void playAssistantCard4(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(4));
        closePage(actionEvent);
    }


    public void playAssistantCard5(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(5));
        closePage(actionEvent);
    }


    public void playAssistantCard6(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(6));
        closePage(actionEvent);
    }


    public void playAssistantCard7(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(7));
        closePage(actionEvent);
    }


    public void playAssistantCard8(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(8));
        closePage(actionEvent);
    }



    public void playAssistantCard9(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(9));
        closePage(actionEvent);
    }


    public void playAssistantCard10(ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(10));
        closePage(actionEvent);
    }

}
