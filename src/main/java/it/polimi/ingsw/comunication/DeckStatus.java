package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;

public class DeckStatus {

    public int id;
    public String color;
    public String playerName = "";

    public DeckStatus(Deck deck) {
        this.id = deck.getId();
        if (deck.getPlayer() != null) {
            this.playerName = deck.getPlayer().getName();
        }
        this.color = deck.getColor();


    }
}
