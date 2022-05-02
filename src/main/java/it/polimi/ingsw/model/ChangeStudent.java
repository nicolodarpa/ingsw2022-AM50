package it.polimi.ingsw.model;

import java.io.BufferedReader;
import java.util.Scanner;

public class ChangeStudent implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you can choose to swap places up to 2 students from the Hall and the Classroom. ");
    private int cost = 1;
    private final Player actualPlayer;
    Scanner scanner = new Scanner(System.in);

    public ChangeStudent(Player actualPlayer){
        this.actualPlayer = actualPlayer;
    }

    public String getEffectOfTheCard(){
        return effectOfTheCard;
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
        PawnColor studentColor = PawnColor.valueOf(scanner.nextLine());
        int position = scanner.nextInt();
        actualPlayer.changeStudent(studentColor, position);
    }



    @Override
    public void addCost() {
        SpecialCard.super.addCost();
        cost++;
    }
}
