package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;

public abstract class SpecialCard {

    int usages = 0;

    PlayersList playersList;
    ArrayList<Island> islands;

    int islandIndex;
    PawnColor pawnColor;

    Player actualPlayer;

    int cost;
    String effectOfTheCard;


    public abstract void effect();


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

    public void setIslands(ArrayList<Island> islands) {
        this.islands = islands;
    }

    public void setPlayersList(PlayersList playersList) {
        this.playersList = playersList;
    }

    public void setPawnColor(PawnColor pawnColor) {
        this.pawnColor = pawnColor;
    }

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public void setIslandIndex(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    public void update(PlayersList playersList, Player actualPlayer , ArrayList<Island> islands, PawnColor pawnColor, int islandIndex){
        setIslands(islands);
        setPlayersList(playersList);
        setPawnColor(pawnColor);
        setActualPlayer(actualPlayer);
        setIslandIndex(islandIndex);
    }

}
