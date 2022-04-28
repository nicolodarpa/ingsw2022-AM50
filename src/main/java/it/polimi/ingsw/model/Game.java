package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.comunication.CloudCardStatus;
import it.polimi.ingsw.comunication.DashboardStatus;
import it.polimi.ingsw.comunication.IslandStatus;

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
 * </p>
 *
 * @author Nicolò D'Arpa, Zarlene Justrem De Mesa, Alessandro Costantini
 * @since 1.0
 */

public class Game {
    private boolean gameStatus;
    private int round = 0;
    private int phase = 0;
    private int numberOfPlayers = 3;
    private int numberOfIslands = 12;
    private PlayersList plist = new PlayersList();
    private StudentsBag studentsBag = new StudentsBag();
    private ArrayList<CloudCard> cloudCards = new ArrayList<>();
    private Map<Integer, Deck> deckMap = new HashMap<>();
    private ArrayList<Island> islands = new ArrayList<>(12);
    private final Teacher[] teachers = {new Teacher(PawnColor.CYAN), new Teacher(PawnColor.MAGENTA), new Teacher(PawnColor.YELLOW), new Teacher(PawnColor.RED), new Teacher(PawnColor.GREEN)};
    private Island islandWithMN;
    private Player actualPlayer;
    private SpecialDeck specialDeck = new SpecialDeck(islandWithMN, plist, actualPlayer, islands);
    private static ArrayList<SpecialCard> cardsInGame = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);


    public int getPhase() {
        return phase;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public PlayersList getPlist() {
        return plist;
    }

    public boolean containsPlayerByName(String name) {
        for (Player player : plist.getPlayers()) {
            if (Objects.equals(player.getName(), name)) {
                return true;
            }
        }
        return false;
    }


    public int getCurrentNumberOfPlayers() {
        return plist.getCurrentNumberOfPlayers();
    }

    public ArrayList<SpecialCard> getCardsInGame() {
        return cardsInGame;
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
        setupGame();
        System.out.println("Game starting");
        plist.notifyAllClients("msg", "Game started");
        String islandStatus = sendIslands();
        plist.notifyAllClients("islands", islandStatus);
        String cloudCardsStatus = sendCloudCards();
        plist.notifyAllClients("cloudCard", cloudCardsStatus);
        String dashboardStatus = sendDashboard();
        plist.notifyAllClients("dashboard", dashboardStatus);
        setActualPlayer();
    }

    public void addPlayer(String name) {
        plist.addPlayer(name);

    }

    public void checkLobby() {
        if (waitLobby()) {
            startGame();
        }
    }

    public boolean waitLobby() {
        return getCurrentNumberOfPlayers() == numberOfPlayers;
    }

    public void removePlayer(String name) {
        plist.removePlayer(name);
        System.out.println(name + " logged out");
        plist.notifyAllClients("msg", name + " logged out");
    }

    public void moveStudentsToHall() {
        plist.moveStudentsToHall(studentsBag);
    }

    public void setupGame() {
        createIslands();
        addMotherNatureToIsland();
        addStudentToIsland();
        fillStudentsBag();
        cloudCardCreation();
        cloudCardFill();
        assignTower();
        createDecks();
        moveStudentsToHall();
        extractSpecialCard();
        this.round = 1;
        System.out.println("Setup complete");


    }

    public String sendIslands() {
        ArrayList<IslandStatus> statusList = new ArrayList<>();
        for (Island island : islands) {
            statusList.add(new IslandStatus(island));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);

    }


    public String sendDashboard() {
        ArrayList<DashboardStatus> statusList = new ArrayList<>();
        for (Player player : plist.getPlayers()) {
            statusList.add(new DashboardStatus(player.getName(), player.getDashboard()));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);
    }

    public String sendCloudCards() {
        ArrayList<CloudCardStatus> statusList = new ArrayList<>();
        for (CloudCard cloudCard : cloudCards) {
            statusList.add(new CloudCardStatus(cloudCard));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);
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


    public void createDecks() {
        deckMap.put(1, new Deck(1));
        deckMap.put(2, new Deck(2));
        deckMap.put(3, new Deck(3));
        deckMap.put(4, new Deck(4));
    }


    public void extractSpecialCard() {
        specialDeck.extractRandomCard();
        cardsInGame = specialDeck.getSpecialCardsInGame();
    }

    public static ArrayList<SpecialCard> getSpecialCardsInGame() {
        return cardsInGame;
    }

    public void connectIsland() {
        for (int i = 1; i < 11; i++) {
            Island curr = islands.get(i);
            Island next = islands.get(i + 1);
            if (Objects.equals(next.getOwner(), curr.getOwner()) && !Objects.equals(curr.getOwner(), "free") && !Objects.equals(next.getIdGroup(), curr.getIdGroup())) {
                next.setIdGroup(curr.getIdGroup());
                for (int j = i + 2; j < 12; j++) {
                    islands.get(j).setIdGroup(islands.get(j).getIdGroup() - 1);
                }
            }
        }
    }


    public void assignTeacher() {
        if (numberOfPlayers == 2) {
            Dashboard d1 = plist.getPlayers().get(0).getDashboard();
            Dashboard d2 = plist.getPlayers().get(1).getDashboard();
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

        } else if (numberOfPlayers == 3) {
            Dashboard d1 = plist.getPlayers().get(0).getDashboard();
            Dashboard d2 = plist.getPlayers().get(1).getDashboard();
            Dashboard d3 = plist.getPlayers().get(2).getDashboard();
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
            for (Player p : plist.getPlayers()) {
                p.getDashboard().addTower(8, TowerColor.values()[i]);
                i++;
            }
        } else if (numberOfPlayers == 3) {
            for (Player p : plist.getPlayers()) {
                p.getDashboard().addTower(6, TowerColor.values()[i]);
                i++;
            }
        }
    }

    public void setActualPlayer() {
        int max_order = 10;
        Player temp = null;
        if (round == 1 && phase == 0) {
            for (Player p : plist.getPlayers()) {
                if (!p.getHasPlayed()) {
                    p.sendToClient("msg", "Your turn started");
                    this.actualPlayer = p;
                    System.out.println(actualPlayer.getName() + " turn");
                    return;
                }
            }
            nextPhase();
        } else {
            for (Player p : plist.getPlayers()) {
                if (p.getOrder() < max_order && !p.getHasPlayed()) {
                    temp = p;
                    max_order = p.getOrder();
                }
            }
            if (temp != null) {
                this.actualPlayer = temp;
                temp.sendToClient("msg", "Your turn started");
                System.out.println(actualPlayer.getName() + " turn");
            } else nextPhase();

        }


    }

    public void nextPhase() {
        if (phase == 0) {
            for (Player p : plist.getPlayers()) {
                p.setHasPlayed(false);
            }
            plist.notifyAllClients("msg", "Action phase");
            phase = 1;
        } else if (phase == 1) {
            for (Player p : plist.getPlayers()) {
                p.setHasPlayed(false);
            }
            plist.notifyAllClients("msg", "Planning phase");
            phase = 0;
            round++;
        }
        setActualPlayer();

    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public Island getIslandWithMN() {
        for (Island i : islands) {
            if (i.getPresenceMN()) {
                this.islandWithMN = i;
            }
        }
        return islandWithMN;
    }


    public void playSpecialCard() {
        for (int i = 0; i < cardsInGame.size(); i++)
            System.out.println(" card " + i + " : " + cardsInGame.get(i).getEffectOfTheCard() + " card's cost : " + cardsInGame.get(i).getCost());
        System.out.println("Insert the number of card do you want to play: ");
        //actualPlayer.playSpecialCard(0);
    }

    public void chooseCloudCard() {
        int numberOfCloudCard = 0;
        ArrayList<Student> students = new ArrayList<>();
        System.out.println(" Choose a cloud card: ");
        numberOfCloudCard = scanner.nextInt();
        students = cloudCards.get(numberOfCloudCard).getAllStudents();
        Dashboard actualDashboard = actualPlayer.getDashboard();
        for (Student s : students)
            actualDashboard.addStudentToHall(s);
        fillOneCloudCard(numberOfCloudCard);
    }

    public void fillOneCloudCard(int numberOfCloudCard) {
        final CloudCard cloudCard = cloudCards.get(numberOfCloudCard);
        if (numberOfPlayers == 2) {
            for (int i = 0; i < numberOfPlayers; i++)
                cloudCard.addStudent(studentsBag.casualExtraction());
        } else if (numberOfPlayers == 3) {
            for (int i = 0; i < numberOfPlayers; i++)
                cloudCard.addStudent(studentsBag.casualExtraction());
        }
    }

    public int chooseDeck(int numberOfDeck, Player player) {
        Deck deck = deckMap.get(numberOfDeck);
        for (Player p : plist.getPlayers()) {
            if (deck.getChosen())
                return 0;
        }
        player.setDeck(deck);
        return 1;
    }


}
