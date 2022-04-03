package it.polimi.ingsw;

import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * Game contains all the metods that implements the match
 *
 * Implemented methods allows to do the following operation:
 *<ul>
 *     <li>addPlayer add a new player to the match</li>
 *     <li>moveStudentsToHall move the students from the Entrance to the hall of the dashboard</li>
 *     <li>setupGame start a new game</li>
 *     <li>checkPlayer check that there is the correct number of players to start a new match or print that the game is waiting new players</li>
 *</ul>
 * @author Nicolò D'Arpa, Zarlene Justrem De Mesa, Alessandro Costantini
 * @since 1.0
 */

public class Game{
    private int id;
    private boolean gameStatus;
    private int round;
    private int numberOfPlayers = 3;
    private int orderOfGame;
    private int numberOfIslands;
    private static PrintWriter out;
    private final PlayersList plist = new PlayersList();
    private StudentsBag studentsBag = new StudentsBag();
    private ArrayList<CloudCard> cloudCards = new ArrayList<>();


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getCurrentNumberOfPlayers() {
        return plist.getCurrentNumberOfPlayers();
    }


    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void startGame() {
        System.out.println("Game starting");
        EchoServerClientHandler.printToAllClients("The game is starting...");

    }

    public void addPlayer(String name) {
        plist.addPlayer(name);
        if (getNumberOfPlayers() == getCurrentNumberOfPlayers()) {
            startGame();
        }
    }

    public void moveStudentsToHall(){
        plist.moveStudentsToHall(studentsBag);

    }

    public void setupGame() {
        fillStudentsBag();
        cloudCardCreation();
        cloudCardFill();

    }

    public void checkPlayers() {

        while (plist.getCurrentNumberOfPlayers() != numberOfPlayers) {
            EchoServerClientHandler.printToAllClients("waiting for players");

        }
    }

    public void fillStudentsBag() {
        studentsBag.fillBag(120);
    }

    public int getStudentsInBag() {
        return studentsBag.getNum();
    }

    /**
     * Create 4 CloudCard in cloudCards and fill them with the correct amount of students
     */
    private void cloudCardCreation() {
        if (numberOfPlayers == 2) {
            for (int i = 0; i < 2; i++) {
                cloudCards.add(new CloudCard(numberOfPlayers));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                cloudCards.add(new CloudCard(numberOfPlayers));
            }
        }


    }

    /**
     * Fill up cloudCard with 3 or 4 students each depending on the number of players
     */
    public void cloudCardFill() {

        for (CloudCard cl : cloudCards) {
            if (numberOfPlayers == 2) {
                for (int i = 0; i < 3; i++) {
                    cl.addStudent(studentsBag.casualEstraction());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    cl.addStudent(studentsBag.casualEstraction());
                }
            }
        }


    }


    public void removePlayer(String name) {
        plist.removePlayer(name);
    }
}
