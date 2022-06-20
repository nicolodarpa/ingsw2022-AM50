package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Represent the deck of the assistant cards in the game
 */
public class Deck {
    private final int id;

    private final String color;


    private Player player = null;
    private boolean hasChosen;
    private final ArrayList<AssistantCard> cardsList = new ArrayList<>();


    public Deck(int id, String color){
        this.id = id;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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

    public AssistantCard getCardOrder(int order){
        for(AssistantCard c : cardsList)
            if(c.getOrder() == order)
                return c;
        return null;
    }


}
