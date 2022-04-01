package it.polimi.ingsw;

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


    public Player(String name){
        this.name = name;
        wallet.setCoins(1);
    }

    public void moveStudentsToHall(StudentsBag bag){
        for (int i = 0; i<7;i++){
            Student student = bag.casualEstraction();
            dashboard.addStudentToHall(student);
        }


    }
    public void chooseDeck(Deck d){

    }

    public String getName() {
        return name;
    }
}
