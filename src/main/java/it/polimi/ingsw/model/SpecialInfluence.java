package it.polimi.ingsw.model;

public class SpecialInfluence implements SpecialCard{
    private final int id = 7;
    private int cost = 1;

    @Override
    public void effect() {
        SpecialCard.super.effect();


    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }



    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
