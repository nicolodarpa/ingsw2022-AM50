package it.polimi.ingsw.server.model;


import it.polimi.ingsw.server.model.CharacterCards.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This class implemented 8 of the character cards in the game.
 */
public class SpecialDeck {
    /**
     * HashMap that contains all the eight special card
     */
    private final Map<Integer, CharacterCardStrategy> deckMap = new HashMap<>();

    /**
     * ArrayList that contains only the three cards extracted in game.
     */
    private final ArrayList<CharacterCardStrategy> specialCardsInGameStrategy = new ArrayList<>();



    public ArrayList<CharacterCardStrategy> getSpecialCardsInGame() {
            return specialCardsInGameStrategy;
        }

    /**
     * creates a deck with all the special cards
     */
    public SpecialDeck() {
        deckMap.put(0, new AddInfluenceStrategy());
        deckMap.put(1, new AddMoveMNStrategy());
        deckMap.put(2, new BlockCardStrategy());
        deckMap.put(3, new MotherNatureInfluenceStrategy());
        deckMap.put(4, new NoTowerInfluenceStrategy());
        deckMap.put(5, new RemoveStudentStrategy());
        deckMap.put(6, new CharacterInfluenceStrategy());
        deckMap.put(7, new TeacherAssignmentStrategy());
    }


    public CharacterCardStrategy getSpecialCard(int index){
        return deckMap.get(index);
    }


    /**
     * extracts randomly a number between 0 and 7, then the extracted number is the correspondence key of the deckMap
     * in this way there are 3 random key and 3 random cards were added in the special deck.
     */
    public void extractRandomCard() {
        ArrayList<Integer> specialCardToPlay = getRandomNonRepeatingIntegers();
        for (Integer integer : specialCardToPlay) {
            specialCardsInGameStrategy.add(getSpecialCard(integer));
        }
    }

    /**
     * extract randomly 3 different numbers
     */
     private static ArrayList<Integer> getRandomNonRepeatingIntegers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        while (numbers.size() < 3) {
            int randomNumber = random.nextInt((7) + 1);
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        return numbers;
    }

}

