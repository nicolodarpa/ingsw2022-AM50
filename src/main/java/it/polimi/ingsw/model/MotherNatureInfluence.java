package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;
import java.util.Scanner;

public class MotherNatureInfluence implements SpecialCard{
    private final String effectOfTheCard = new String(" With this card you can chose an island and calculate the influence like when Mother Nature is on that island ");
    private int cost = 3;
    private final ArrayList<Island> islandsInGame;
    private final PlayersList plist;
    Scanner scanner = new Scanner(System.in);

    public MotherNatureInfluence(ArrayList<Island> islands, PlayersList plist){
        this.islandsInGame = islands;
        this.plist = plist;
    }

    @Override
    public void effect() {
        Island islandInfluence;
        System.out.println("Choose an Island : ");
        islandInfluence = islandsInGame.get(scanner.nextInt());
        islandInfluence.calcInfluence(plist);
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

    public String getEffectOfTheCard(){
        return effectOfTheCard;
    }
}
