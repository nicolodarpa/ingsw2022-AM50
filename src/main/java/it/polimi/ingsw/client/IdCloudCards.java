package it.polimi.ingsw.client;

import java.util.ArrayList;
import java.util.Random;

public class IdCloudCards {
    private final int numberOfTotalCloudCards = 4;
    private final ArrayList<Integer> idOfCloudCards = new ArrayList<>(numberOfTotalCloudCards);
    /**
     * ArrayList that contains only the three id extracted in game.
     */
    private final ArrayList<Integer> idOfCloudCardsInGame = new ArrayList<>();

    public ArrayList<Integer> getIdOfCloudCardsInGame() {
        return idOfCloudCardsInGame;
    }

    public Integer getIdCloudCard(int index){
        return idOfCloudCards.get(index);
    }
    public IdCloudCards() {
        for (int id = 0; id < numberOfTotalCloudCards; id++ ){
            idOfCloudCards.add(id);
        }
    }

    /**
     * extracts randomly a number (=numberOfCloudCardInTheGame) of id between 0 and 3.
     * @param numberOfCloudCardsInTheGame is the number of the CloudCards that are on the game.
     */
    public void extractRandomCard(int numberOfCloudCardsInTheGame) {
        ArrayList<Integer> cloudCardPlayed = getRandomNonRepeatingIntegers(numberOfCloudCardsInTheGame, 0, 3);
        for (Integer integer : cloudCardPlayed) {
            idOfCloudCardsInGame.add(getIdCloudCard(integer));
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
