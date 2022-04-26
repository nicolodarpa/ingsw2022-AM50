package it.polimi.ingsw.model;


import com.google.gson.Gson;
import it.polimi.ingsw.comunication.TextMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Player contains all method that allows player to play a match
 */

public class Player {
    private String name;
    private Socket socket;
    private PrintWriter out;
    private int order = 10;
    private int movesOfMN = 0;
    private int numberOfTowersOnIsland = 0;
    private static Wallet wallet = new Wallet();
    private Dashboard dashboard = new Dashboard();
    private Deck deck;
    private int influencePoint = 0;

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Wallet getWallet() {
        return wallet;
    }


    public Player(String name){
        this.name = name;
        wallet.setCoins(1);
    }

    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
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

    public Socket getSocket() {
        if (socket == null) {
            try {
                socket = new Socket("127.0.0.1", 1337);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return socket;
    }

    public PrintWriter getOut() {
        if (out == null) {
            try {
                out = new PrintWriter(getSocket().getOutputStream(), true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return out;
    }

    public int getOrder() {
        return order;
    }


    public int getMovesOfMN() {
        return movesOfMN;
    }

    public void setDeck(Deck deck){
        this.deck = deck;
        deck.setChosen(true);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;

    }

    public void sendToClient(String type, Object message) {
        TextMessage text = new TextMessage(type,(String) message);
        Gson gson = new Gson();
        String json = gson.toJson(text, TextMessage.class);
        getOut().println(json);


    }


    public Deck getDeck() {
        return deck;
    }

    public int getInfluencePoint() {
        return influencePoint;
    }

    public void setMovesOfMN(int movesOfMN) {
        this.movesOfMN = movesOfMN;
    }

    public void setInfluencePoint(int influencePoint) {
        this.influencePoint = influencePoint;
    }

    /**
     * The player choose based on the number of the card of its deck which one to play
     * @param numberOfCard indicate the order of the card
     */
    public void playAssistantCard(int numberOfCard){
        ArrayList<AssistantCard> assistantCardsPlayed = new ArrayList<>();
        try{
            assistantCardsPlayed.add(deck.getCardsList().get(numberOfCard));
            if(checkCard(assistantCardsPlayed)){
                this.order = deck.getCardsList().get(numberOfCard).getOrder();
                this.movesOfMN = deck.getCardsList().get(numberOfCard).getMoveOfMN();
                deck.getCardsList().remove(deck.getCardsList().get(numberOfCard));
            }else{
                System.out.println(" card not available");
            }
        }catch (Exception e){
            System.out.println("This card is not available, please select a valid card between 0 and 9");
        }
    }

    public boolean checkCard(ArrayList<AssistantCard> assistantCardsPlayed){
        AssistantCard card_one = assistantCardsPlayed.get(0);
        for(int i = 1 ; i < assistantCardsPlayed.size(); i++){
            if(card_one == assistantCardsPlayed.get(i)){
                if(deck.getCardsList().size() == 1)
                    return true;
                return false;
            }
        }
        return true;
    }

    public void playSpecialCard(int numberOfCard){
        final SpecialCard cardToPlay;
        try{
            cardToPlay = Game.getSpecialCardsInGame().get(numberOfCard);
            if(wallet.getCoins() >= cardToPlay.getCost()){
                cardToPlay.effect();
                cardToPlay.addCost();
            }else{
                System.out.println(" Not enough coins to play this card ");
            }
        }catch (Exception e){
            System.out.println("Invalid input");
        }
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the selected Island
     * @param island indicate the island where we want to move the student
     * @param position indicate the position of the student in the DashboardHall
     */
    public void moveStudentToIsland(Island island, int position){
        try{
            island.addStudent(dashboard.getStudentFromHall(position));
        }catch (Exception e){
            System.out.println(" Invalid input ");
        }

    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the classroom
     * @param position indicate the position of the student in the DashboardHall
     */
    public void moveStudentToClassroom(int position){
        try{
            dashboard.addStudentToClassroom(dashboard.getStudentFromHall(position));
            dashboard.addCoin(wallet);
        }catch (Exception e){
            System.out.println(" Invalid position, please insert a valid number between 0 and 6 ");
        }
    }
}

