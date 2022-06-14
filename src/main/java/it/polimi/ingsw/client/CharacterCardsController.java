package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.CharacterCard;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

public class CharacterCardsController implements Initializable {


    @FXML
    private Button close;
    @FXML
    private ImageView card1, card2, card3;
    @FXML
    private Text label1, label2, label3;

    @FXML
    private ChoiceBox<String> colorsBox;
    @FXML
    private ChoiceBox<String> islandsBox;

    @FXML
    private Button playButton;
    @FXML
    private RadioButton radio1, radio2, radio3;
    private CharacterCard[] characterCards = new CharacterCard[3];

    private ToggleGroup toggleGroup = new ToggleGroup();

    private String specialCardIndex = "";

    private String index2 = "";

    public CharacterCardsController() {

        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        radio3.setToggleGroup(toggleGroup);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colorsBox.setItems(FXCollections.observableArrayList("", "GREEN", "RED", "YELLOW", "MAGENTA", "CYAN"));
        islandsBox.setItems(FXCollections.observableArrayList("", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        playButton.setOnAction((EventHandler<ActionEvent>) playButton);

        card1.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("assets/characterCards/" + characterCards[0].name + ".jpg"))));
        label1.setText("Cost: " + characterCards[0].cost + "\n" +
                "Effect: " + characterCards[0].effect);
        card2.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("assets/characterCards/" + characterCards[1].name + ".jpg"))));
        label2.setText("Cost: " + characterCards[1].cost + "\n" +
                "Effect: " + characterCards[1].effect);
        card3.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("assets/characterCards/" + characterCards[2].name + ".jpg"))));
        label3.setText("Cost: " + characterCards[2].cost + "\n" +
                "Effect: " + characterCards[2].effect);
    }


    private void playCard(ActionEvent event) {
        System.out.println("play special card");
        //ClientInput.getInstance().sendString("playCharacterCard", specialCardIndex, index2);
    }

    public void setCards(CharacterCard[] cards) {
        this.characterCards = cards;
    }
}
