package it.polimi.ingsw.model;

public class ChangeStudent implements SpecialCard{
    private String effectOfTheCard = new String(" With this card you 2 available move more for Mother Nature ");
    private final int id = 3;
    private int cost = 1;

    public int getId() {
        return id;
    }



    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void effect() {
        SpecialCard.super.effect();
    }

    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
