package it.polimi.ingsw.model;

public class MotherNatureInfluence implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you can chose an island and calculate the influence like when Mother Nature is on that island ");
    private int cost = 3;

    @Override
    public void effect() {


    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }



    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
