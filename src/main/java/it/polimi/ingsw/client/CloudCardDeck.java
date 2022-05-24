package it.polimi.ingsw.client;

import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.Game;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CloudCardDeck {

    private ArrayList<Image> imgCloudcard2 = new ArrayList<>(2);
    private ArrayList<Image> imgCloudcard3 = new ArrayList<>(3);
    private Map<Integer,Image> deckCloudCard = new HashMap<>();
    private ArrayList<Image> cloudCardsInGame = new ArrayList<>();
    Image cc1 = new Image("@../../../../images/cloudcard/cc1.png", false);
    Image cc2 = new Image("@../../../../images/cloudcard/cc2.png", false);
    Image cc3 = new Image("@../../../../images/cloudcard/cc3.png", false);
    Image cc4= new Image("@../../../../images/cloudcard/cc4.png", false);


    public CloudCardDeck(){
        deckCloudCard.put(0,cc1);
        deckCloudCard.put(1,cc2);
        deckCloudCard.put(2,cc3);
        deckCloudCard.put(3,cc4);
    }

    public ArrayList<Image> getCloudCardsInGame() {
        return cloudCardsInGame;
    }

    public Image getCloudCard(int index) {
        return deckCloudCard.get(index);
    }

    public void extractRandomCloudCard() {
        ArrayList<Integer> cloudCard = getRandomNonRepeatingCC(3, 0, 3);
        for (Integer integer : cloudCard) {
            cloudCardsInGame.add(getCloudCard(integer));
        }
    }
    public static ArrayList<Integer> getRandomNonRepeatingCC(int size, int min, int max) {
        ArrayList<Integer> cloudcards = new ArrayList<>();
        Random random = new Random();
        while (cloudcards.size() < size) {
            int randomNumber = random.nextInt((max - min) + 1) + min;
            if (!cloudcards.contains(randomNumber)) {
                cloudcards.add(randomNumber);
            }
        }
        return cloudcards;
    }
}
