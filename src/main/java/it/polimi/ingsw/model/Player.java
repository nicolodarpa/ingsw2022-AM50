package it.polimi.ingsw.model;

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
        deck.setHasChoosen(true);
    }

    public Deck getDeck() {
        return deck;
    }

    public void playAssistantCard(int n){
        order = deck.getCardsList().get(n).getOrder();
        movesOfMN = deck.getCardsList().get(n).getMovesOfMN();
        deck.getCardsList().get(n).setHasPlayed(true);
    }

}
