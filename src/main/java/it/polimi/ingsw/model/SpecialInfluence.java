package it.polimi.ingsw.model;

public class SpecialInfluence implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you have to choose one color of the students which will not be considered for the influence.");
    private int cost = 3;


    @Override
    public void effect() {
        SpecialCard.super.effect();


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
