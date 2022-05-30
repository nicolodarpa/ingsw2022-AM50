package it.polimi.ingsw.model;


import com.google.gson.Gson;
import it.polimi.ingsw.comunication.TextMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static it.polimi.ingsw.model.Game.MOVES;

/**
 * Player contains all method that allows player to play a match
 */

public class Player {


    private final String name;
    private Socket socket;
    private PrintWriter out;
    private int order = 10;
    private int movesOfMN = 0;

    private int movesOfStudents = MOVES;
    private static final Wallet wallet = new Wallet();
    private Dashboard dashboard = new Dashboard();
    private Deck deck;
    private int influencePoint = 0;

    private boolean hasPlayed = false;

    private int lastPlayedAC = 0;

    private boolean teacherAssignerModifier = false;


    private final ArrayList<AssistantCard> assistantCardsPlayed = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        wallet.setCoins(1);
    }

    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        wallet.setCoins(1);
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public int getCoins() {
        return wallet.getCoins();
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void addCoin(int coin) {
        wallet.addCoins(coin);
    }

    public void spendCoins(int coin) {
        wallet.removeCoins(coin);
    }

    public void resetMovesOfStudents() {
        movesOfStudents = MOVES;
    }

    public int getMovesOfStudents() {
        return movesOfStudents;
    }

    public int getLastPlayedAC() {
        return lastPlayedAC;
    }

    public void setLastPlayedAC(int lastPlayedAC) {
        this.lastPlayedAC = lastPlayedAC;
    }

    public void setTeacherAssignerModifier(boolean modifier) {
        this.teacherAssignerModifier = modifier;
    }

    public boolean isTeacherAssignerModifier() {
        return teacherAssignerModifier;
    }


    public void moveStudentsToHall(StudentsBag bag) {
        for (int i = 0; i < getDashboard().getHall().length; i++) {
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

    public void setOut(PrintWriter out) {
        this.out = out;
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

    public void setDeck(Deck deck) {
        this.deck = deck;
        deck.setChosen(true);
    }

    public void setSocket(Socket socket) {
        this.socket = socket;

    }

    public boolean getHasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(boolean h) {
        this.hasPlayed = h;
    }

    public void sendToClient(String type, Object message) {
        try {
            TextMessage text = new TextMessage(type, (String) message);
            Gson gson = new Gson();
            String json = gson.toJson(text, TextMessage.class);
            getOut().println(json);
        } catch (Exception e) {
            System.out.println("No connection to client");
        }
    }

    public void sendToClient(String type,String context, Object message) {
        try {
            TextMessage text = new TextMessage(type, context,(String) message);
            Gson gson = new Gson();
            String json = gson.toJson(text, TextMessage.class);
            getOut().println(json);
        } catch (Exception e) {
            System.out.println("No connection to client");
        }
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
     *
     * @param cardOrder indicate the order of the card
     */
    public void playAssistantCard(int cardOrder) {
        try {
            if (!checkCardAvailability(cardOrder)) {
                AssistantCard cardToPlay = deck.getCardOrder(cardOrder);
                assistantCardsPlayed.add(cardToPlay);
                order = cardToPlay.order();
                movesOfMN = cardToPlay.movesOfMN();
                deck.getCardsList().remove(cardToPlay);
                sendToClient("msg", "played card " + cardOrder + "\nvalue: " + order + "\nmoves of MN available: " + movesOfMN);
                this.hasPlayed = true;
                lastPlayedAC = order;
            } else {
                sendToClient("error", "card not available");
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * checks if the card it's been already played and if it is the last card of the deck
     *
     * @param cardOrder indicate the order of the card
     * @return true if the card is playable or false if is not playable
     */
    public boolean checkCardAvailability(int cardOrder) {
        AssistantCard assistantCard = deck.getCardOrder(cardOrder);
        for (int i = 1; i < assistantCardsPlayed.size(); i++) {
            if (assistantCard == assistantCardsPlayed.get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true if there aren't cards in the deck
     */
    public boolean deckSize() {
        return deck.getCardsList().size() == 0;
    }


    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the selected Island
     *
     * @param island   indicate the island where we want to move the student
     * @param position indicate the position of the student in the DashboardHall
     */
    public void moveStudentToIsland(Island island, int position) {
        Student student = dashboard.getStudentFromHall(position);
        if (student != null) {
            island.addStudent(student);
            movesOfStudents--;
        } else {
            System.out.println(" selected null student ");
        }
    }


    public boolean moveStudentToIsland(int position, int index, Game game) {
        Student student = dashboard.getStudentFromHall(position);
        try {
            if (student != null && movesOfStudents>0) {
                game.getIslands().get(index).addStudent(student);
                movesOfStudents--;
                return true;
            } else {
                System.out.println("selected null student ");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the classroom
     *
     * @param position indicate the position of the student in the DashboardHall
     */
    public boolean moveStudentToClassroom(int position, Game game) {
        Student student = dashboard.getStudentFromHall(position);
        if (student != null && movesOfStudents>0 ) {
            dashboard.addStudentToClassroom(student);
            dashboard.addCoin(wallet);
            movesOfStudents--;
            game.assignTeacher();
            return true;
        } else {
            System.out.println("selected null student ");
            return false;
        }
    }

    /**
     * switch the position of a student in the hall and a student in the classroom
     *
     * @param studentColor is the color of the student in the classroom
     * @param pos_hall     is the position of the student in the hall
     */
    public void changeStudent(PawnColor studentColor, int pos_hall) {
        Student studentFromClassroom = dashboard.getStudentFromClassroom(studentColor);
        Student studentFromHall = dashboard.getStudentFromHall(pos_hall);
        try {
            if (studentFromClassroom != null) {
                dashboard.addStudentToClassroom(studentFromHall);
                dashboard.addStudentToHall(studentFromClassroom);
            }
        } catch (Exception ignored) {

        }

    }


}

