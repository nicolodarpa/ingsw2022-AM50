package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

public class RemoveStudent implements SpecialCard{

    private final String effectOfTheCard = new String(" With this card you 2 available move more for Mother Nature ");
    private int cost = 3;


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void effect() {


    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }

    public void calcInfluence(PlayersList playersList){

    }
}
