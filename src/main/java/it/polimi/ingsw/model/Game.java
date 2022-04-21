package it.polimi.ingsw.model;


import it.polimi.ingsw.PlayersList;

import java.net.Socket;
import java.util.*;

/**
 * Game contains all the methods that implements the match
 * <p>
 * Implemented methods allows to do the following operation:
 * <ul>
 *     <li>addPlayer add a new player to the match</li>
 *     <li>moveStudentsToHall move the students from the Entrance to the hall of the dashboard</li>
 *     <li>setupGame start a new game</li>
 *     <li>checkPlayer check that there is the correct number of players to start a new match or print that the game is waiting new players</li>
 * </ul>
 *
 * @author Nicolò D'Arpa, Zarlene Justrem De Mesa, Alessandro Costantini
 * @since 1.0
 */

public class Game {

    private boolean gameStatus;
    private int round = 0;
    private int numberOfPlayers = 3;
    private Player actualPlayer;
    private int numberOfIslands = 12;
    private final PlayersList plist = new PlayersList();
    private StudentsBag studentsBag = new StudentsBag();
    private ArrayList<CloudCard> cloudCards = new ArrayList<>();
    private Map<Integer, Deck> map = new HashMap<>();
    private ArrayList<Island> islands = new ArrayList<>(12);
    private final Teacher[] teachers = {new Teacher(PawnColor.CYAN), new Teacher(PawnColor.MAGENTA), new Teacher(PawnColor.YELLOW), new Teacher(PawnColor.RED), new Teacher(PawnColor.GREEN)};


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public PlayersList getPlist() {
        return plist;
    }


    public int getCurrentNumberOfPlayers() {
        return PlayersList.getCurrentNumberOfPlayers();
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
    }

    public void addPlayer(String name, Socket socket) {
        PlayersList.addPlayer(name, socket);
        if (getNumberOfPlayers() == getCurrentNumberOfPlayers()) {
            startGame();
        }
    }

    public void moveStudentsToHall() {
        plist.moveStudentsToHall(studentsBag);
    }

    public void setupGame() {
        createIslands();
        addMotherNatureToIsland();
        studentsBag.fillBag(10);
        addStudentToIsland();
        fillStudentsBag();
        cloudCardCreation();
        cloudCardFill();
        createDecks();
        assignTower();
        moveStudentsToHall();
    }

    public void createIslands() {
        for (int i = 1; i <= numberOfIslands; i++) {
            Island island = new Island(i);
            islands.add(island);
        }
    }

    /**
     * add Mother Nature to a random Island, at the beginning of a new match
     */
    public void addMotherNatureToIsland() {
        Random random = new Random();
        int upperBound = 12;
        int id_random = random.nextInt(upperBound);
        int id_opposite;
        if (id_random <= 5)
            id_opposite = id_random + 6;
        else {
            id_opposite = id_random - 6;
        }
        islands.get(id_random).addMotherNature();
        islands.get(id_opposite).setOppositeMN(true);
    }

    /**
     * add Two students of each color on every island(except the island where there is Mother Nature and its opposite)
     */
    public void addStudentToIsland() {
        StudentsBag studentsBag_setup = new StudentsBag();
        studentsBag_setup.fillBag(10);
        for (Island i : islands) {
            if (!i.getPresenceMN() && !i.getOppositeMN()) {
                Student student = studentsBag_setup.casualExtraction();
                i.addStudent(student);
            }
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
        PlayersList.removePlayer(name);
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


    public void connectIsland() {
        for (int i = 1; i < 11; i++) {
            Island curr = islands.get(i);
            Island next = islands.get(i+1);
            if(Objects.equals(next.getOwner(), curr.getOwner()) && !Objects.equals(curr.getOwner(), "free")  && !Objects.equals(next.getIdGroup(), curr.getIdGroup())){
                next.setIdGroup(curr.getIdGroup());
                for (int j = i+2; j < 12; j++){
                    islands.get(j).setIdGroup(islands.get(j).getIdGroup()-1);
                }

            }

        }

    }

    public void assignTeacher() {
        if (numberOfPlayers == 2) {
            Dashboard d1 = PlayersList.getPlayers().get(0).getDashboard();
            Dashboard d2 = PlayersList.getPlayers().get(1).getDashboard();
            for (int i = 0; i < 5; i++) {
                if (d1.countStudentByColor(teachers[i].getColor()) > d2.countStudentByColor(teachers[i].getColor())) {
                    d1.addTeacherToTable(teachers[i]);
                    if (d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                } else if (d2.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor())) {
                    d2.addTeacherToTable(teachers[i]);
                    if (d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                }
            }

        }

        else if (numberOfPlayers == 3) {
            Dashboard d1 = PlayersList.getPlayers().get(0).getDashboard();
            Dashboard d2 = PlayersList.getPlayers().get(1).getDashboard();
            Dashboard d3 = PlayersList.getPlayers().get(2).getDashboard();
            for (int i = 0; i < 5; i++) {
                if (d1.countStudentByColor(teachers[i].getColor()) > d2.countStudentByColor(teachers[i].getColor()) && d1.countStudentByColor(teachers[i].getColor()) > d3.countStudentByColor(teachers[i].getColor())) {
                    d1.addTeacherToTable(teachers[i]);
                    if (d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                    else if (d3.getTeacherTable()[i] != null)
                        d3.removeTeacherFromTable(teachers[i]);
                } else if (d2.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor()) && d2.countStudentByColor(teachers[i].getColor()) > d3.countStudentByColor(teachers[i].getColor())) {
                    d2.addTeacherToTable(teachers[i]);
                    if (d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                    else if (d3.getTeacherTable()[i] != null)
                        d3.removeTeacherFromTable(teachers[i]);
                } else if (d3.countStudentByColor(teachers[i].getColor()) > d1.countStudentByColor(teachers[i].getColor()) && d3.countStudentByColor(teachers[i].getColor()) > d2.countStudentByColor(teachers[i].getColor())) {
                    d3.addTeacherToTable(teachers[i]);
                    if (d1.getTeacherTable()[i] != null)
                        d1.removeTeacherFromTable(teachers[i]);
                    else if (d2.getTeacherTable()[i] != null)
                        d2.removeTeacherFromTable(teachers[i]);
                }

            }
        }
    }

    public void assignTower() {
        int i = 0;
        if (numberOfPlayers == 2) {
            for (Player p : PlayersList.getPlayers()) {
                p.getDashboard().addTower(8, TowerColor.values()[i]);
                i++;
            }
        } else if (numberOfPlayers == 3) {
            for (Player p : PlayersList.getPlayers()) {
                p.getDashboard().addTower(6, TowerColor.values()[i]);
                i++;
            }
        }
    }
}
