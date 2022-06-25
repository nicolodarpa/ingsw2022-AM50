package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.model.PlayersList;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentsBag;

import java.util.ArrayList;

public abstract class SpecialCardStrategy {

    /**
     * It is the number of usages of the card.
     */
    int usages = 0;

    /**
     * The list of players in game.
     */
    PlayersList playersList;

    /**
     * The list of islands in game.
     */
    ArrayList<Island> islands;

    /**
     * It is the index of a selected island in some special cards.
     */
    int index;

    /**
     * It is a selected color in some special cards.
     */
    PawnColor pawnColor;

    /**
     * It is the current player in game.
     */
    Player currentPlayer;

    /**
     * The students bag of the game.
     */
    StudentsBag bag;

    /**
     * The cost of the special card.
     */
    int cost;

    /**
     * The effect of the card.
     */
    String effectOfTheCard;

    /**
     * The name of the special card.
     */
    String name;


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
