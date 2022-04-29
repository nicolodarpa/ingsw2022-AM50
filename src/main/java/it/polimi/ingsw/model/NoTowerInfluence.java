package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;

public class NoTowerInfluence implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card the towers have no influence on the island considered");
    private int cost = 3;
    private final Island islandWithMn;
    private final PlayersList players;

    public NoTowerInfluence(Island islandWithMn, PlayersList players) {
        this.islandWithMn = islandWithMn;
        this.players = players;
    }


    @Override
    public void effect() {
        SpecialCard.super.effect();
        islandWithMn.calcInfluenceNoTower(players);
    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }



    public int getCost() {
        return cost;
    }

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }

}
