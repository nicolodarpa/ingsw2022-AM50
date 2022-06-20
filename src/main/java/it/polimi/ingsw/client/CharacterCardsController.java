package it.polimi.ingsw.client;

import it.polimi.ingsw.comunication.CharacterCard;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller of characterCards.fxml
 * The three character cards available in game are displayed.
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

    private final ToggleGroup toggleGroup = new ToggleGroup();

    /**
     * Index of the character card chosen via the radio button.
     * Gets sent to the server via a command message in the field value1.
     */
    private String characterCardIndex = "";

    /**
     * Contains the optional value for the effect of some character cards.
     * It may store the index of an island or of a pawn color.
     * Gets sent to the server via a command message in the field value2.
     */
    private String index2 = "";

    /**
     * Initialize the controller.
     * Sets the ToggleGroup of the three radioButtons, populates the choiceBoxes, set the images for the character cards with the relative cost and effect
     * @param url default parameter of initialize
     * @param resourceBundle default parameter of initialize
     */
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


    /**
     *Plays the selected character card sending a message to the server.
     * Gets the selected card via the radio buttons and if necessary by the effect of the card
     * it gets the island index or the students color via one of the two ChoiceBox
     * @param actionEvent required by the button
     */
    private void playCard(ActionEvent actionEvent) {

        if (radio1.isSelected()) {
            characterCardIndex = "1";
        } else if (radio2.isSelected()) {
            characterCardIndex = "2";
        } else if (radio3.isSelected()) {
            characterCardIndex = "3";
        }
        String name = characterCards[Integer.parseInt(characterCardIndex) - 1].name;
        if (Objects.equals(name, "princess") || Objects.equals(name, "ambassador") || Objects.equals(name, "warrior")) {
            index2 = islandsBox.getValue();
        } else if (Objects.equals(name, "thief") || Objects.equals(name, "wizard")) {
            index2 = String.valueOf(colorsBox.getSelectionModel().getSelectedIndex());
        }
        System.out.println("play special card: " + characterCardIndex + "value: " + index2);
        ClientInput.getInstance().sendString("playCharacterCard", characterCardIndex, index2);
    }


    /**
     * Sets the List characterCards
     * @param cards list of available character cards in game
     */
    public void setCards(CharacterCard[] cards) {
        this.characterCards = cards;
    }
}
