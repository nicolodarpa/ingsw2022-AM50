package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;

public class AddInfluence implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you have 2 more points in the calculation of the influence");
    private int cost = 2;
    private Island islandWithMN;
    private PlayersList players;
    private Player actualPlayer;

    public AddInfluence(Island islandWithMn, PlayersList players, Player actualPlayer){
        this.islandWithMN = islandWithMn;
        this.players = players;
        this.actualPlayer = actualPlayer;
    }




    public int getCost() {
        return cost;
    }

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void effect() {
        actualPlayer.setInfluencePoint(2);
        islandWithMN.calcInfluence(players);
        Dashboard dashboardTemp = actualPlayer.getDashboard();
    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
