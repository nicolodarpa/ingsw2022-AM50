package it.polimi.ingsw.model;

public class ChangeStudent implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you can choose to swap places up to 2 students from the Hall and the Classroom. ");
    private int cost = 1;

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
