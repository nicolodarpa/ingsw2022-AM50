package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.PlayersStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller of AssistantCardStatus.fxml
 */
public class AssistantCardController implements Initializable, DisplayLabel{


    /**
     * IDs of the views in the fxml file
     */
    @FXML
    public ImageView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10;
    @FXML
    public Label otherPlayer, otherPlayer2;

    public ArrayList<Label> label = new ArrayList<>();


    /**
     * ArrayList of all the ImageViews
     */

    public ArrayList<ImageView> assistantCards = new ArrayList<>();

    /**
     * Decode incoming messages from string to json
     */
    private final Gson gson = new Gson();

    /**
     * Contains the message sent from the server.
     */
    private TextMessage message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientInput.getInstance().sendString("player", "");
        setAssistantCardsImageView();
        setAssistantCardsImages();
        setUpLabelArray();
        printCardChosenByOther();
    }


    /**
     * Adds the Imageview of the assistant card in the array "assistantCards".
     */
    public void setAssistantCardsImageView() {
        assistantCards.addAll(Arrays.asList(card1, card2, card3, card4, card5, card6, card7, card8, card9, card10));
    }

    /**
     * Sets the images of the assistant card in the relative ImageView.
     * The front of the card is displayed if the card is available in the deck, the back of the card if it's already been used.
     * Sends a message to the server asking for the player status that contains the list of assistant cards played.
     */
    public void setAssistantCardsImages() {
        ClientInput.getInstance().sendString("player", "");
        message = ClientInput.getInstance().readLine();
        PlayersStatus playersStatus = gson.fromJson(message.message, PlayersStatus[].class)[0];
        ArrayList<Integer> cardsPlayed = playersStatus.cardsPlayed;
        System.out.println("the cards Played: " + cardsPlayed);
        int j;

        for (int i = 0; i < assistantCards.size(); i++) {
            j = i + 1;
            assistantCards.get(i).setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("assets/assistantCard/Assistente (" + j + ").png"))));
        }

        if (cardsPlayed.size() != 0) {
            for (Integer integer : cardsPlayed) {
                setRetro(assistantCards.get(integer - 1), playersStatus.deckId);
            }
        }
    }


    /**
     * Sets the ImageView the back of the card.
     *
     * @param cardView ImageView to set
     * @param idDeck   id of the deck chosen by the player. Every deck has a different back for the cards
     */
    public void setRetro(ImageView cardView, int idDeck) {
        Image retroImg = new Image(String.valueOf(getClass().getClassLoader().getResource("assets/CarteTOT_back_" + idDeck + "@3x.png")));
        cardView.setImage(retroImg);
    }

    private void setUpLabelArray(){
        label.add(otherPlayer);
        label.add(otherPlayer2);

    }

    public void printCardChosenByOther(){
        ClientInput.getInstance().sendString("player", "");
        PlayersStatus player = gson.fromJson(ClientInput.getInstance().readLine().message, PlayersStatus[].class)[0];
        String name1 = player.name;
        ClientInput.getInstance().sendString("allPlayers", "");
        PlayersStatus[] players = gson.fromJson(ClientInput.getInstance().readLine().message, PlayersStatus[].class);
        int i=0;

        for (PlayersStatus playersStatus : players){
            System.out.println(playersStatus.name);
            if(!Objects.equals(playersStatus.name, name1)){
                String otherPlayerUsername = playersStatus.name;
                String orderCard = String.valueOf(playersStatus.getOrder());
                String movesOfMN= String.valueOf(playersStatus.movesOfMN);
                displayLabel("username: " + otherPlayerUsername, label.get(i)  , "\nhas played the card of order : "+ orderCard+ "\nhas moves of MN: "+movesOfMN);
                i++;
            }
        }
    }


    /**
     * Closes the window of assistant cards.
     */
    public void closePage(ActionEvent actionEvent) {
        Window window = ((Node) actionEvent.getSource()).getScene().getWindow();
        window.hide();
    }

    public void playAssistantCard1(ActionEvent actionEvent) {
        playAssistantCard(1, actionEvent);
    }


    public void playAssistantCard2(ActionEvent actionEvent) {
        playAssistantCard(2, actionEvent);
    }


    public void playAssistantCard3(ActionEvent actionEvent) {
        playAssistantCard(3, actionEvent);
    }


    public void playAssistantCard4(ActionEvent actionEvent) {
        playAssistantCard(4, actionEvent);
    }


    public void playAssistantCard5(ActionEvent actionEvent) {
        playAssistantCard(5, actionEvent);
    }


    public void playAssistantCard6(ActionEvent actionEvent) {
        playAssistantCard(6, actionEvent);
    }


    public void playAssistantCard7(ActionEvent actionEvent) {
        playAssistantCard(7, actionEvent);
    }

    public void playAssistantCard8(ActionEvent actionEvent) {
        playAssistantCard(8, actionEvent);
    }

    public void playAssistantCard9(ActionEvent actionEvent) {
        playAssistantCard(9, actionEvent);
    }


    public void playAssistantCard10(ActionEvent actionEvent) {
        playAssistantCard(10, actionEvent);
    }


    /**
     * Sends a message to the server with the command <code>playAssistantCard</code> and the index of the assistant card chosen.
     * @param i index of the assistant card played.
     * @param actionEvent ActionEvent necessary to close the window
     */
    private void playAssistantCard(int i, ActionEvent actionEvent) {
        ClientInput.getInstance().sendString("playAssistantCard", String.valueOf(i));
        closePage(actionEvent);
    }


}
