package it.polimi.ingsw.model;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Player contains all method that allows player to play a match
 */

public class Player {
    private String name;
    private Socket socket;

    private PrintWriter out;

    private int order;
    private int movesOfMN;
    private int numberOfTowers;
    private Wallet wallet = new Wallet();
    private Dashboard dashboard = new Dashboard();
    private Deck deck;


    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Player(String name) {
        this.name = name;
        wallet.setCoins(1);
    }

    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        wallet.setCoins(1);
    }


    public void moveStudentsToHall(StudentsBag bag) {
        for (int i = 0; i < 7; i++) {
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

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
        deck.setChosen(true);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;

    }

    public void printToCLI(String message) {
        getOut().println(message);
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * The player choose based on the number of the card of its deck which one to play
     *
     * @param numberOfCard indicate the order of the card
     */
    public void playAssistantCard(int numberOfCard) {
        order = deck.getCardsList().get(numberOfCard).getOrder();
        movesOfMN = deck.getCardsList().get(numberOfCard).getMoveOfMN();
        deck.getCardsList().remove(deck.getCardsList().get(numberOfCard));
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the selected Island
     *
     * @param island   indicate the island where we want to move the student
     * @param position indicate the position of the student in the DashboardHall
     */
    public void moveStudentToIsland(Island island, int position) {
        island.addStudent(dashboard.getStudentFromHall(position));
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the classroom
     *
     * @param position indicate the position of the student in the DashboardHall
     */
    public void moveStudentToClassroom(int position) {
        dashboard.addStudentToClassroom(dashboard.getStudentFromHall(position));
    }
}

