package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.model.CharacterCards.SpecialCard;

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
        for (Player player:plist.getPlayers()){
            player.getDashboard().setupHall(numberOfPlayers);
        }
        createIslands();
        addMotherNatureToIsland();
        addStudentToIsland();
        fillStudentsBag();
        cloudCardCreation();
        for (CloudCard cloudCard : cloudCards) {
            cloudCardFill(cloudCard);
        }
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

    public String sendHall(Player player) {
        ArrayList<HallStatus> statusList = new ArrayList<>();
        statusList.add(new HallStatus(player.getDashboard()));
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

    public String sendPlayerDashboard(Player player) {
        ArrayList<DashboardStatus> statusList = new ArrayList<>();
        statusList.add(new DashboardStatus(player.getName(), player.getDashboard()));
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


    public String sendCharacterCardsDeck(){
        ArrayList<CharacterCard> cardList = new ArrayList<>();
        for (SpecialCard specialCard: cardsInGame){
            cardList.add(new CharacterCard(specialCard));
        }
        Gson gson = new Gson();
        return gson.toJson(cardList);
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

    public boolean moveMN(Player player, int destinationIslandIndex) {
        int playerMovesOfMN = player.getMovesOfMN();
        int islandWithMNIndex = getIslandWithMNIndex();
        int moves = destinationIslandIndex - islandWithMNIndex;
        if (moves < 0) {
            moves = islands.size() + moves;
        }
        if (moves > playerMovesOfMN) {
            return false;
        } else {
            islandWithMN.setPresenceMN(false);
            islands.get(destinationIslandIndex).setPresenceMN(true);
            islandWithMN = islands.get(destinationIslandIndex);
            islandWithMN.calcInfluence(plist);
            connectIsland();
            return true;
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
        for (int i = 1; i < islands.size(); i++) {

            Island curr = islands.get(i);
            Island next;
            if (i == islands.size() - 1) {
                next = islands.get(0);
            } else next = islands.get(i + 1);


            if (Objects.equals(next.getOwner(), curr.getOwner()) && !Objects.equals(curr.getOwner(), "free") && !Objects.equals(next.getIdGroup(), curr.getIdGroup())) {
                next.setIdGroup(curr.getIdGroup());
                for (Student student : next.getStudents()) {
                    islands.get(i).addStudent(student);

                }
                for (Tower tower : next.getTowerArrayList()) {
                    islands.get(i).addTower(tower);
                }
                islands.get(i).increaseDimension();
                islands.remove(next);
                i--;
            }
        }
        if (islands.size() == 3) {
            plist.notifyAllClients("notify", "the game has finished");
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
        int max_order = 11;
        Player temp = null;
        if (round == 1 && phase == 0) {
            for (Player p : plist.getPlayers()) {
                if (!p.getHasPlayed()) {
                    p.sendToClient("notify", "Your turn started");
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
                temp.sendToClient("notify", "Your turn started");
                System.out.println(actualPlayer.getName() + " turn");
            } else nextPhase();

        }


    }

    public void nextPhase() {
        if (phase == 0) {
            for (Player p : plist.getPlayers()) {
                p.setHasPlayed(false);
            }
            plist.notifyAllClients("notify", "Action phase");
            phase = 1;
        } else if (phase == 1) {
            for (Player p : plist.getPlayers()) {
                p.setHasPlayed(false);
                p.setLastPlayedAC(0);
                p.resetMovesOfStudents();
            }
            plist.notifyAllClients("notify", "Planning phase");
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


    public int getIslandWithMNIndex() {
        int index = 0;
        for (Island i : islands) {
            if (i.getPresenceMN()) {
                this.islandWithMN = i;
                return index;
            }
            index++;
        }
        return index;
    }



    public void chooseCloudCard(int numberOfCloudCard, Player player) {
        ArrayList<Student> students;
        System.out.println(" Choose a cloud card: ");
        students = cloudCards.get(numberOfCloudCard).getAllStudents();
        Dashboard actualDashboard = player.getDashboard();
        for (Student s : students)
            actualDashboard.addStudentToHall(s);
        cloudCardFill(cloudCards.get(numberOfCloudCard));
        player.setHasPlayed(true);
    }

    /**
     * Fill up cloudCard with 3 or 4 students each depending on the number of players
     */
    public void cloudCardFill(CloudCard cloudCard) {

        if (numberOfPlayers == 2) {
            for (int i = 0; i < 3; i++) {
                cloudCard.addStudent(studentsBag.casualExtraction());
            }
        } else {
            for (int i = 0; i < 4; i++) {
                cloudCard.addStudent(studentsBag.casualExtraction());
            }
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

    public boolean playAssistantCard(Player player, int cardNumber) {
        if (player.checkCardAvailability(cardNumber - 1)) {
            player.sendToClient("error", "card already played");
            return true;
        }
        boolean check = false;
        if (checkLastPlayedAssistant(cardNumber)) {
            player.sendToClient("error", "Assistant card already played by another player");
            for (AssistantCard assistantCard : player.getDeck().getCardsList()) {
                if (!checkLastPlayedAssistant(assistantCard.getOrder())) {
                    player.sendToClient("warning", "you can play card# " + assistantCard.getOrder());
                    check = true;
                }

            }
            if (!check) {
                player.playAssistantCard(cardNumber - 1);
                return false;
            } else {
                return true;
            }
        }
        player.playAssistantCard(cardNumber - 1);
        return false;


    }

    public boolean checkLastPlayedAssistant(int order) {

        for (Player player : plist.getPlayers()) {
            if (order == player.getLastPlayedAC()) {
                return true;
            }
        }

        return false;

    }



    public boolean playCharacterCard(int specialCardIndex, int islandIndex, PawnColor color){
        SpecialCard specialCard = cardsInGame.get(specialCardIndex);
        specialCard.update(plist,actualPlayer, islands, color, islandIndex);

        return false;
    }


}
