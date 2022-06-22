package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Deck;
import it.polimi.ingsw.model.Player;


/**
 * Template class to encode and decode json with the info of a deck
 */
public class DeckStatus {

    /**
     * Deck id
     */
    public int id;

    /**
     * Deck color
     */
    public String color;
    /**
     * Player name who chose the deck
     */
    public String playerName = "";

    /**
     * Gets the id, color and owner name of a deck
     * @param deck assistant card deck to get info
     */
    public DeckStatus(Deck deck) {
        this.id = deck.getId();
        if (deck.getPlayer() != null) {
            this.playerName = deck.getPlayer().getName();
        }
        this.color = deck.getColor();


    }
}
