package it.polimi.ingsw.model;


import it.polimi.ingsw.model.CharacterCards.BlockCard;
import it.polimi.ingsw.model.CharacterCards.*;

import java.util.*;

/**
 * This class implemented 8 of the character cards in the game.
 */
public class SpecialDeck {
    private final SpecialCard characterCard1 = new AddInfluence();
    private final SpecialCard characterCard2 = new AddMoveMN();
    private final SpecialCard characterCard3 = new BlockCard();
    private final SpecialCard characterCard4 = new MotherNatureInfluence();
    private final SpecialCard characterCard5 = new NoTowerInfluence();
    private final SpecialCard characterCard6 = new RemoveStudent();
    private final SpecialCard characterCard7 = new SpecialInfluence();
    private final SpecialCard characterCard8 = new TeacherAssignment();

    private final Map<Integer, SpecialCard> deckMap = new HashMap<>();
    private final ArrayList<SpecialCard> specialCardsInGame = new ArrayList<>();



    public ArrayList<SpecialCard> getSpecialCardsInGame() {
            return specialCardsInGame;
        }

    /**
     * creates a deck with all the special cards
     * @return
     */
    public SpecialDeck() {
        deckMap.put(0, characterCard1);
        deckMap.put(1, characterCard2);
        deckMap.put(2, characterCard3);
        deckMap.put(3, characterCard4);
        deckMap.put(4, characterCard5);
        deckMap.put(5, characterCard6);
        deckMap.put(6, characterCard7);
        deckMap.put(7, characterCard8);
    }


    public SpecialCard getSpecialCard(int index){
        return deckMap.get(index);
    }


    /**
     * extracts randomly a number between 0 and 7, then the extracted number is the correspondence key of the deckMap
     * in this way there are 3 random key and 3 random cards were added in the special deck.
     */
    public void extractRandomCard() {
        ArrayList<Integer> specialCardToPlay = getRandomNonRepeatingIntegers(3, 0, 7);
        for (Integer integer : specialCardToPlay) {
            specialCardsInGame.add(getSpecialCard(integer));
        }
    }

    /**
     * extract randomly 3 different numbers
     */
     public static ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min, int max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        while (numbers.size() < size) {
            int randomNumber = random.nextInt((max - min) + 1) + min;
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

}

