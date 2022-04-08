package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Player contains all method that allows player to play a match
 */

public class Player {
    private  String name;
    private int order;
    private int movesOfMN;
    private boolean statusPlayer;
    private int numberOfTowers;
    private String towerColor;
    private Wallet wallet = new Wallet();
    private Dashboard dashboard = new Dashboard();
    private Deck deck;

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Player(String name){
        this.name = name;
        wallet.setCoins(1);
    }

    public void moveStudentsToHall(StudentsBag bag){
        for (int i = 0; i<7;i++){
            Student student = bag.casualExtraction();
            dashboard.addStudentToHall(student);
        }
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getMovesOfMN() {
        return movesOfMN;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
        deck.setHasChosen(true);
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * The player choose based on the number of the card of its deck which one to play
     * @param numberOfCard
     */
    public void playAssistantCard(int numberOfCard){
        order = deck.getCardsList().get(numberOfCard).getOrder();
        movesOfMN = deck.getCardsList().get(numberOfCard).getMoveOfMN();
        deck.getCardsList().remove(deck.getCardsList().get(numberOfCard));
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the selected Island
     * @param island
     * @param position
     */
    public void moveStudentToIsland(Island island, int position){
        island.addStudent(dashboard.getStudentFromHall(position));
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the classroom
     * @param position
     */
    public void moveStudentToClassroom(int position){
        dashboard.addStudentToClassroom(dashboard.getStudentFromHall(position));
    }

    public int[] countStudentRow() {
        int[] colorList = new int[5];
        int red = 0, cyan = 0, magenta = 0, yellow = 0, green = 0;
        Student[] list = new Student[10];
        list = dashboard.getHall();
        colorList[0] = red;
        colorList[1] = cyan;
        colorList[2] = magenta;
        colorList[3] = yellow;
        colorList[4] = green;

        for (int i = 0; i < 10; i++) {
            if (list[i].getColor() == "RED")
                colorList[0]++;
            else if (list[i].getColor() == "CYAN")
                colorList[1]++;
            else if (list[i].getColor() == "MAGENTA")
                colorList[2]++;
            else if (list[i].getColor() == "YELLOW")
                colorList[3]++;
            else if (list[i].getColor() == "GREEN")
                colorList[4]++;
        }
        return colorList;
    }
}

