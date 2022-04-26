package it.polimi.ingsw.model;

public class RemoveStudent implements SpecialCard{

    private final String effectOfTheCard = new String(" With this card you choose a color of the students and every player (even you) has to put in the BagStudents 3 students of that color from each Hall. ");
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

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }
}
