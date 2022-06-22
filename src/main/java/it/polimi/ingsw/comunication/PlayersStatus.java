package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

/**
 * Template class to encode and decode json with the information of a player

 */
public class PlayersStatus {

    /**
     * PLayer name
     */
    public String name;

    /**
     * Player last assistant card played
     */
    public int order;

    /**
     * Available Mother Nature moves
     */
    public int movesOfMN;

    /**
     * Availabe student moves
     */
    public int movesOfStudents;

    /**
     * Coins in the wallet
     */
    public int wallet;

    /**
     * List of assistant cards played
     */
    public ArrayList<Integer> cardsPlayed;

    /**
     * Assistant cards deck id
     */
    public int deckId;

    /**
     * Indicates if the player has played during the current round
     */
    public boolean hasPlayed;

    public PlayersStatus(Player player) {
        this.name = player.getName();
        this.order = player.getOrder();
        this.movesOfMN = player.getMovesOfMN();
        this.movesOfStudents = player.getMovesOfStudents();
        this.hasPlayed = player.getHasPlayed();
        this.wallet = player.getWallet().getCoins();
        this.cardsPlayed = player.getAssistantCardsPlayed();
        this.deckId = player.getDeck().getId();
    }


}
