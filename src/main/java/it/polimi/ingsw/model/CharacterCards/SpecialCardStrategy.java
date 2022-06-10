package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentsBag;

import java.util.ArrayList;

public abstract class SpecialCardStrategy {

    int usages = 0;

    PlayersList playersList;
    ArrayList<Island> islands;

    int index;
    PawnColor pawnColor;

    Player actualPlayer;

    StudentsBag bag;
    int cost;
    String effectOfTheCard;

    String name;


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

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
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

    public void update(PlayersList playersList, Player actualPlayer , ArrayList<Island> islands, PawnColor pawnColor, int index, StudentsBag bag){
        setIslands(islands);
        setPlayersList(playersList);
        setPawnColor(pawnColor);
        setActualPlayer(actualPlayer);
        setIndex(index);
        setBag(bag);
    }

}
