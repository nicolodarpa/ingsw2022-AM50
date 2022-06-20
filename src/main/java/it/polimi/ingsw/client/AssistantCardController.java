package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.PlayersStatus;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.fxml.Initializable;
import it.polimi.ingsw.comunication.TextMessage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.Window;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller of AssistantCardStatus.fxml
 */
public class AssistantCardController implements Initializable {


    @FXML
    public ImageView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;
    public ArrayList<ImageView> assistantCards= new ArrayList<>();
    private final Gson gson = new Gson();
    private TextMessage message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientInput.getInstance().sendString("player", "");
        setAssistantCardsImageView();
        setAssistantCardsImages();
    }



    /**
     * sets the Imageview of the assistant card in the array "assistantCard".
     */
    public void setAssistantCardsImageView(){
        assistantCards.addAll(Arrays.asList(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10));
    }

    /**
     * sets the images of the assistant card in the array "assistantCard" or the image of the deck chosen in case the card has already been used.
     */
    public void setAssistantCardsImages(){
        ClientInput.getInstance().sendString("player", "");
        message = ClientInput.getInstance().readLine();
        PlayersStatus playersStatus = gson.fromJson(message.message,PlayersStatus[].class )[0];
        ArrayList<Integer> cardsPlayed= playersStatus.cardsPlayed;

        int j;
        for (int i=0; i < assistantCards.size(); i++){
            j=i+1;
            if (cardsPlayed.size() != 0){
                for (int k=0; k <cardsPlayed.size(); k++){
                    if (cardsPlayed.get(k) == j){
                        setRetro(assistantCards.get(i), playersStatus.deckId);
                    }else {
                        assistantCards.get(i).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("assets/assistantCard/Assistente ("+j+").png"))));
                    }
                }
            }
            else {
                assistantCards.get(i).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("assets/assistantCard/Assistente ("+j+").png"))));
            }

        }
    }

    /**
     * is the function that set the image of the deck.
     * @param cardConsiderated
     * @param idDeck
     */
    public void setRetro(ImageView cardConsiderated, int idDeck){
        Image retroImg = new Image(String.valueOf(getClass().getClassLoader().getResource("assets/CarteTOT_back_"+idDeck+"@3x.png")));
        cardConsiderated.setImage(retroImg);
    }




    /**
     * Close the window of assistant cards.
     */
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
