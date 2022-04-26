package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;

public class AddInfluence implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you have 2 more points in the calculation of the influence. ");
    private int cost = 2;
    private final Player actualPlayer;

    public AddInfluence(Player actualPlayer){
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
    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
