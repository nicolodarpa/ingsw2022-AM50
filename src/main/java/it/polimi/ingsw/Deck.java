package it.polimi.ingsw;

import java.util.ArrayList;

public class Deck {
    private int id;
    private boolean hasChoosen;
    private ArrayList<AssistantCard> cardsList = new ArrayList<AssistantCard>();


    public Deck(int id, boolean hasChoosen){
        this.id = id;
        this.hasChoosen = hasChoosen;
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

    public void AssistantCardList(Deck d){
        AssistantCard ac1 = new AssistantCard(1,1);
        AssistantCard ac2 = new AssistantCard(2,1);
        AssistantCard ac3 = new AssistantCard(3,2);
        AssistantCard ac4 = new AssistantCard(4,2);
        AssistantCard ac5 = new AssistantCard(5,3);
        AssistantCard ac6 = new AssistantCard(6,3);
        AssistantCard ac7 = new AssistantCard(7,4);
        AssistantCard ac8 = new AssistantCard(8,4);
        AssistantCard ac9 = new AssistantCard(9,5);
        AssistantCard ac10 = new AssistantCard(10,5);

        d.cardsList.add(ac1);
        d.cardsList.add(ac2);
        d.cardsList.add(ac3);
        d.cardsList.add(ac4);
        d.cardsList.add(ac5);
        d.cardsList.add(ac5);
        d.cardsList.add(ac6);
        d.cardsList.add(ac7);
        d.cardsList.add(ac8);
        d.cardsList.add(ac9);
        d.cardsList.add(ac10);

    }

}
