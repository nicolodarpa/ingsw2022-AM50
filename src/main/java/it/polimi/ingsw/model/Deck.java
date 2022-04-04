package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Deck {
    private int id;
    private boolean hasChoosen;
    private ArrayList<AssistantCard> cardsList = new ArrayList<AssistantCard>();


    public Deck(int id){
        this.id = id;
        this.hasChoosen = false;
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

    public boolean isHasChoosen() {
        return hasChoosen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHasChoosen(boolean hasChoosen) {
        this.hasChoosen = hasChoosen;
    }

    public ArrayList<AssistantCard> getCardsList() {
        return cardsList;
    }


}
