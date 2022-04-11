package it.polimi.ingsw.model;


import it.polimi.ingsw.EchoServerClientHandler;
import it.polimi.ingsw.PlayersList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

public class Game {
    private boolean gameStatus;
    private int round;
    private int numberOfPlayers;
    private int orderOfGame;
    private int numberOfIslands;
    private final PlayersList plist = new PlayersList();
    private StudentsBag studentsBag = new StudentsBag();
    private ArrayList<CloudCard> cloudCards = new ArrayList<>();
    private Map<Integer, Deck> map = new HashMap<Integer, Deck>();
    private ArrayList<Island> islands = new ArrayList<>(12);
    private Teacher[] teachers = {new Teacher(PawnColor.CYAN), new Teacher(PawnColor.MAGENTA), new Teacher(PawnColor.YELLOW), new Teacher(PawnColor.RED), new Teacher(PawnColor.GREEN)};



    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public PlayersList getPlist() {
        return plist;
    }


    public int getCurrentNumberOfPlayers() {
        return plist.getCurrentNumberOfPlayers();
    }

    public StudentsBag getStudentsBag() {
        return studentsBag;
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    public ArrayList<CloudCard> getCloudCards() {
        return cloudCards;
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

    public void moveStudentsToHall() {
        plist.moveStudentsToHall(studentsBag);
    }

    public void setupGame() {
        fillStudentsBag();
        cloudCardCreation();
        cloudCardFill();
        createDecks();
        createIslands();
        moveStudentsToHall();
        this.round = 0;
    }

    public void createIslands() {
        for (int i = 1; i <= 12; i++) {
            Island island = new Island(i);
            islands.add(island);
        }
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
     * Create 3 or 4 CloudCard in cloudCards and fill them with the correct amount of students
     */
    public void cloudCardCreation() {
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
                    cl.addStudent(studentsBag.casualExtraction());
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    cl.addStudent(studentsBag.casualExtraction());
                }
            }
        }


    }


    public void removePlayer(String name) {
        plist.removePlayer(name);
    }

    public void createDecks() {
        /**
         * create the assistant card's deck:
         * <ul>
         *     <li>Wizard 1</li>
         *     <li>Wizard 2</li>
         *     <li>Wizard 3</li>
         *     <li>Wizard 4</li>
         * </ul>
         */

        map.put(1, new Deck(1));
        map.put(2, new Deck(2));
        map.put(3, new Deck(3));
        map.put(4, new Deck(4));
    }


    public void assignTeacher() {
        if (numberOfPlayers == 2) {
            Dashboard d1 = PlayersList.getPlayers().get(0).getDashboard();
            Dashboard d2 = PlayersList.getPlayers().get(1).getDashboard();
            for(int i = 0; i < 5; i++){
                if (d1.countStudentByColor(teachers[i].getColor()) > d2.countStudentByColor(teachers[i].getColor())) {
                    d1.addTeacherToTable(teachers[i]);
                    if(d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                }

                else if(d2.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor())){
                    d2.addTeacherToTable(teachers[i]);
                    if(d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                }
            }

        }

        /**else if (numberOfPlayers == 3) {
            Dashboard d1 = PlayersList.getPlayers().get(0).getDashboard();
            Dashboard d2 = PlayersList.getPlayers().get(1).getDashboard();
            Dashboard d3 = PlayersList.getPlayers().get(2).getDashboard();
            if ((d1.countStudentByColor(teacher.getColor()) > d2.countStudentByColor(teacher.getColor())) && (d1.countStudentByColor(teacher.getColor()) > d3.countStudentByColor(teacher.getColor()))) {
                d1.addTeacherToTable(teacher);
            }

        }
         */
    }
}
