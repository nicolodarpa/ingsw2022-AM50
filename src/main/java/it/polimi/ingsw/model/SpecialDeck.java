package it.polimi.ingsw.model;


import it.polimi.ingsw.model.CharacterCards.BlockCardStrategy;
import it.polimi.ingsw.model.CharacterCards.*;

import java.util.*;

/**
 * This class implemented 8 of the character cards in the game.
 */
public class SpecialDeck {
    private final Map<Integer, SpecialCardStrategy> deckMap = new HashMap<>();
    private final ArrayList<SpecialCardStrategy> specialCardsInGameStrategy = new ArrayList<>();



    public ArrayList<SpecialCardStrategy> getSpecialCardsInGame() {
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
        deckMap.put(6, new SpecialInfluenceStrategy());
        deckMap.put(7, new TeacherAssignmentStrategy());
    }


    public SpecialCardStrategy getSpecialCard(int index){
        return deckMap.get(index);
    }


    /**
     * extracts randomly a number between 0 and 7, then the extracted number is the correspondence key of the deckMap
     * in this way there are 3 random key and 3 random cards were added in the special deck.
     */
    public void extractRandomCard() {
        ArrayList<Integer> specialCardToPlay = getRandomNonRepeatingIntegers(3, 0, 7);
        for (Integer integer : specialCardToPlay) {
            specialCardsInGameStrategy.add(getSpecialCard(integer));
        }
    }

    /**
     * extract randomly 3 different numbers
     */
     private static ArrayList<Integer> getRandomNonRepeatingIntegers(int size, int min, int max) {
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

