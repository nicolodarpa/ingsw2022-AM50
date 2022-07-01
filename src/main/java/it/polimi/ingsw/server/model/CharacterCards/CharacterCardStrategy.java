package it.polimi.ingsw.server.model.CharacterCards;

import it.polimi.ingsw.server.model.PlayersList;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.StudentsBag;

import java.util.ArrayList;

/**
 * It's an abstract class that implement the strategy of the Character Cards.
 */
public abstract class CharacterCardStrategy {

    /**
     * It is the number of usages of the card.
     */
    protected int usages = 0;

    /**
     * The list of players in game.
     */
    protected PlayersList playersList;

    /**
     * The list of islands in game.
     */
    protected ArrayList<Island> islands;

    /**
     * It is the index of a selected island in some special cards.
     */
    protected int index;

    /**
     * It is a selected color in some special cards.
     */
    protected PawnColor pawnColor;

    /**
     * It is the current player in game.
     */
    protected Player currentPlayer;

    /**
     * The students bag of the game.
     */
    protected StudentsBag bag;

    /**
     * The cost of the special card.
     */
    protected int cost;

    /**
     * The effect of the card.
     */
    protected String effectOfTheCard;

    /**
     * The name of the special card.
     */
    protected String name;


    /**
     * Activates the effect of the card
     */
    public abstract void effect();

    /**
     * It increases the cost by 1 when the card is played.
     */
    public void addCost() {
        if (usages == 0) {
            cost++;
        }
        usages++;
    }

    public int getCost() {
        return cost;
    }

    public int getIndex(){
        return index;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public void setEffectOfTheCard(String effectOfTheCard) {
        this.effectOfTheCard = effectOfTheCard;
    }

    public String getEffectOfTheCard() {
        return effectOfTheCard;
    }

    public String getName() {
        return name;
    }

    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }

    public void setPlayersList(PlayersList playersList) {
        this.playersList = playersList;
    }

    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBag(StudentsBag bag) {
        this.bag = bag;
    }

    /**
     * Sets the current state of the game.
     * @param playersList are the players in game.
     * @param currentPlayer are the current player in game.
     * @param islands are the islands in game.
     * @param pawnColor is the color considered.
     * @param index is the index of island considered.
     * @param bag is the studentsBag in game.
     */
    public void update(PlayersList playersList, Player currentPlayer , ArrayList<Island> islands, PawnColor pawnColor, int index, StudentsBag bag){
        setIslands(islands);
        setPlayersList(playersList);
        setPawnColor(pawnColor);
        setCurrentPlayer(currentPlayer);
        setIndex(index);
        setBag(bag);
    }

}
