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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TooManyListenersException;

/**
 * Controller of characterCards.fxml
 */
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

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
        radio3.setToggleGroup(toggleGroup);
        colorsBox.setItems(FXCollections.observableArrayList("GREEN", "RED", "YELLOW", "MAGENTA", "CYAN"));
        islandsBox.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"));
        playButton.setOnAction(this::playCard);
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

        if (radio1.isSelected()) {
            specialCardIndex = "1";
        } else if (radio2.isSelected()) {
            specialCardIndex = "2";
        } else if (radio3.isSelected()) {
            specialCardIndex = "3";
        }
        String name = characterCards[Integer.parseInt(specialCardIndex) - 1].name;
        if (Objects.equals(name, "princess") || Objects.equals(name, "ambassador") || Objects.equals(name, "warrior")) {
            index2 = islandsBox.getValue();
        } else if (Objects.equals(name, "thief") || Objects.equals(name, "wizard")) {
            index2 = String.valueOf(colorsBox.getSelectionModel().getSelectedIndex());
        }
        System.out.println("play special card: " + specialCardIndex + "value: " + index2);
        ClientInput.getInstance().sendString("playCharacterCard", specialCardIndex, index2);
    }

    public void setCards(CharacterCard[] cards) {
        this.characterCards = cards;
    }
}
