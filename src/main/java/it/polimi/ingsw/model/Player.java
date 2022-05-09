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
    private final String name;
    private Socket socket;
    private PrintWriter out;
    private int order = 10;
    private int movesOfMN = 0;

    private int movesOfStudents = 3;
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

    public int getWallet() {
        return wallet.getCoins();
    }

    public void addCoin(int coin) {
        wallet.addCoins(coin);
    }

    public void spendCoins(int coin) {
        wallet.removeCoins(coin);
    }

    public void resetMovesOfStudents() {
        movesOfStudents = 3;
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
     * @param cardIndex indicate the order of the card
     */
    public void playAssistantCard(int cardIndex) {
        try {
            if (!checkCardAvailability(cardIndex)) {
                assistantCardsPlayed.add(deck.getCardsList().get(cardIndex));
                order = deck.getCardsList().get(cardIndex).getOrder();
                movesOfMN = deck.getCardsList().get(cardIndex).getMoveOfMN();
                deck.getCardsList().remove(deck.getCardsList().get(cardIndex));
                sendToClient("msg", "played card " + (cardIndex + 1) + "\nvalue: " + order + "\nmoves of MN available: " + movesOfMN);
                this.hasPlayed = true;
                lastPlayedAC = order;
            } else {
                sendToClient("error", "card not available");
            }
        } catch (Exception ignored) {
        }
    }

    public boolean checkCardAvailability(int cardIndex) {
        AssistantCard assistantCard = deck.getCardsList().get(cardIndex);
        for (int i = 1; i < assistantCardsPlayed.size(); i++) {
            if (assistantCard == assistantCardsPlayed.get(i)) {
                return true;
            }
        }
        return false;
    }

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
            if (student != null) {
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
        if (student != null) {
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

