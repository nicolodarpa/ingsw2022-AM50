package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;

public class NoTowerInfluence implements SpecialCard{
    private String effectOfTheCard = new String(" With this card you 2 available move more for Mother Nature ");
    private int cost = 3;
    private Island islandWithMn;
    private PlayersList players;

    public NoTowerInfluence(Island islandWithMn, PlayersList players) {
        this.islandWithMn = islandWithMn;
        this.players = players;
    }


    @Override
    public void effect() {
        SpecialCard.super.effect();
        islandWithMn.calcInfluence(players);
    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }



    public int getCost() {
        return cost;
    }

}
