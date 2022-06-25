package it.polimi.ingsw.server.model;


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


    /**
     * Indicates the player's username
     */
    private final String name;


    /**
     * Indicates if the player is active
     */
    private boolean active;

    /**
     * Is the socket of the client
     */
    private Socket socket;
    private PrintWriter out;

    /**
     * Indicates the order of the player in the game, it's set when a player play an assistant card
     */
    private int order = 10;
    /**
     * Indicates the player's available moves of Mother Nature, it's set when a player play an assistant card
     */
    private int movesOfMN = 0;

    /**
     * Indicates the player's available moves of student in the action phase, it's set based of the number of players in the game
     */
    private int movesOfStudents = Game.MOVES;

    /**
     * Is the player's wallet that contains all his coins. {@link Wallet}
     */
    private final Wallet wallet = new Wallet();

    /**
     * The player's dashboard.{@link Dashboard}
     */
    private Dashboard dashboard = new Dashboard();

    /**
     * The player's deck.{@link Deck}
     */
    private Deck deck;

    /**
     * The player's influence point on a selected island, it set to 0 by default.
     */
    private int influencePoint = 0;

    /**
     * Boolean flag that says if the player has played in planning phase or in action phase.
     */
    private boolean hasPlayed = false;

    /**
     * It is the last assistant card that players played.
     */
    private int lastPlayedAC = 0;

    /**
     * Boolean flag that it uses when the player play the special card that change the assignment of the teacher.
     * It is set to false by default, and it is changed when the player play that special card.
     */
    private boolean teacherAssignerModifier = false;


    /**
     * Contains all the assistant card order tha player played.
     */
    private final ArrayList<Integer> assistantCardsPlayed = new ArrayList<>();

    /**
     * It sets the name and also the initial value of coins in wallet.
     * @param name is the player's name
     */
    public Player(String name) {
        this.name = name;
        wallet.setCoins(1);
        setActive(true);
    }


    /**
     * It sets the name, the client socket and also the initial value of coins in wallet.
     * @param name is the player's name
     * @param socket is the client's socket
     */
    public Player(String name, Socket socket) {
        this.name = name;
        this.socket = socket;
        wallet.setCoins(1);
        setActive(true);
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public ArrayList<Integer> getAssistantCardsPlayed() {
        return assistantCardsPlayed;
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

    /**
     * Adds coins to player's wallet.
     * @param coin is the number of coins to add.
     */
    public void addCoin(int coin) {
        wallet.addCoins(coin);
    }

    /**
     * Removes coins when player spend it.
     * @param coin is the number of coins to remove.
     */
    public void spendCoins(int coin) {
        wallet.removeCoins(coin);
    }

    /**
     * Resets the moves of student when player has finished his turn in action phase.
     */
    public void resetMovesOfStudents() {
        movesOfStudents = Game.MOVES;
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


    /**
     * Moves 7 or 9 students from bag to player's hall.
     * @param bag is the StudentBag where it takes the students.
     */
    public void fillStudentsHall(StudentsBag bag) {
        for (int i = 0; i < getDashboard().getHall().length; i++) {
            Student student = bag.casualExtraction();
            dashboard.addStudentToHall(student);
        }
    }

    public String getName() {
        return name;
    }

    /**
     * @return the default socket.
     */
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


    /**
     * Send to client a message parametrized by the type and the object.
     * @param type is the message's type (<code>msg</code>, <code>error</code>, <code>notify</code> ...
     * @param message is a string that contains the message.
     */
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
                assistantCardsPlayed.add(cardOrder);
                order = cardToPlay.order();
                movesOfMN += cardToPlay.movesOfMN();
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
        return assistantCardsPlayed.contains(cardOrder);
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


    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the selected Island
     *
     * @param index   indicate the index of island where we want to move the student
     * @param position indicate the position of the student in the DashboardHall
     * @param game is the game the player is playing.
     *
     *catch a new Exception when the player select a null position or an invalid island's index
     *
     */
    public boolean moveStudentToIsland(int position, int index, Game game) {
        try {
            if (dashboard.getHall()[position] != null && movesOfStudents > 0) {
                Student student = dashboard.getStudentFromHall(position);
                game.getIslands().get(index).addStudent(student);
                movesOfStudents--;
                return true;
            } else {
                System.out.println("selected null student ");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Invalid input");
            return false;
        }
    }

    /**
     * The player choose based on the position of the student from the DashboardHall which one to move to the classroom
     *
     * @param position indicate the position of the student in the DashboardHall
     */
    public boolean moveStudentToClassroom(int position, Game game) {
        try{
            if (dashboard.getHall()[position] != null && movesOfStudents>0 ) {
                Student student = dashboard.getStudentFromHall(position);
                dashboard.addStudentToClassroom(student);
                dashboard.addCoin(wallet);
                movesOfStudents--;
                game.assignTeacher();
                return true;
            } else {
                System.out.println("selected null student ");
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}

