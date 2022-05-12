package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Represent the deck of the assistant cards in the game
 */
public class Deck {
    private int id;
    private boolean hasChosen;
    private final ArrayList<AssistantCard> cardsList = new ArrayList<AssistantCard>();


    public Deck(int id){
        this.id = id;
        this.hasChosen = false;
        cardsList.add(new AssistantCard(1,1));
        cardsList.add(new AssistantCard(2,1));
        cardsList.add(new AssistantCard(3,2));
        cardsList.add(new AssistantCard(4,2));
        cardsList.add(new AssistantCard(5,3));
        cardsList.add(new AssistantCard(6,3));
        cardsList.add(new AssistantCard(7,4));
        cardsList.add(new AssistantCard(8,4));
        cardsList.add(new AssistantCard(9,5));
        cardsList.add(new AssistantCard(10,5));
    }

    public int getId() {
        return id;
    }

    public boolean getChosen() {
        return hasChosen;
    }

    /**
     *
     * @param hasChosen indicate if the deck it's been chosen by a player
     */
    public void setChosen(boolean hasChosen) {
        this.hasChosen = hasChosen;
    }

    public ArrayList<AssistantCard> getCardsList() {
        return cardsList;
    }


}
