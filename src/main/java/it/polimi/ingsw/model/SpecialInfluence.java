package it.polimi.ingsw.model;

import it.polimi.ingsw.PlayersList;

import java.util.Scanner;
import java.util.stream.IntStream;

public class SpecialInfluence implements SpecialCard{
    private final String effectOfTheCard = new String("With this card you have to choose one color of the students which will not be considered for the influence.");
    private int cost = 3;
    private final PlayersList players;
    private final Island islandWithMN;
    private final Scanner scanner = new Scanner(System.in);


    public SpecialInfluence(PlayersList players, Island islandWithMN){
        this.islandWithMN = islandWithMN;
        this.players = players;
    }


    @Override
    public void effect() {
        SpecialCard.super.effect();
        final PawnColor colorToExclude;
        final int numberOfColor = 5;

        System.out.println(" Which color do you want exclude ? : ");
        colorToExclude = PawnColor.valueOf(scanner.nextLine());

        for(Player p : players.getPlayers()){
                int [] colorStudent = new int[numberOfColor];
                Dashboard dashboardTemp = p.getDashboard();
                dashboardTemp.getTeacherTable()[colorToExclude.ordinal()] = null;
                for(int i = 0; i < dashboardTemp.getTeacherTable().length; i++){
                    if(dashboardTemp.getTeacherTable()[i] != null){
                        colorStudent[i] = islandWithMN.countStudentOfAColor(dashboardTemp.getTeacherTable()[i].getColor());
                    }
                }
                p.setInfluencePoint(IntStream.of(colorStudent).sum());
            }

            if (players.getPlayers().size() == 2) {
                Player p1 = players.getPlayers().get(0);
                Player p2 = players.getPlayers().get(1);

                if (p1.getInfluencePoint() > p2.getInfluencePoint()) {
                    islandWithMN.setOwner(p1);
                } else if (p1.getInfluencePoint() < p2.getInfluencePoint()) {
                    islandWithMN.setOwner(p2);
                } else {
                    islandWithMN.setOwner(null);
                }
            } else if (players.getPlayers().size() == 3){

                Player p1 = players.getPlayers().get(0);
                Player p2 = players.getPlayers().get(1);
                Player p3 = players.getPlayers().get(2);



                if (p1.getInfluencePoint() > p2.getInfluencePoint() && p1.getInfluencePoint() > p3.getInfluencePoint()) {
                    islandWithMN.setOwner(p1);
                } else if (p2.getInfluencePoint() > p1.getInfluencePoint() && p2.getInfluencePoint() > p3.getInfluencePoint()) {
                    islandWithMN.setOwner(p2);
                } else if (p3.getInfluencePoint() > p1.getInfluencePoint() && p3.getInfluencePoint() > p2.getInfluencePoint()) {
                    islandWithMN.setOwner(p3);
                }
                islandWithMN.addTower();

                for(Player p : players.getPlayers()){
                    p.setInfluencePoint(0);
                }
            }
    }


    public int getCost() {
        return cost;
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
