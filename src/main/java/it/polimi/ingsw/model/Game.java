package it.polimi.ingsw.model;

import com.google.gson.Gson;
import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.model.CharacterCards.SpecialCard;

import java.io.IOException;
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
    private String gameStatus = "Waiting for players";
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
    private SpecialDeck specialDeck = new SpecialDeck();
    private static ArrayList<SpecialCard> cardsInGame = new ArrayList<>();


    public Game() {
    }

    public Game(int numberOfPlayers) {
        setNumberOfPlayers(numberOfPlayers);
        createDecks();
    }

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

    public String getGameStatus() {
        return gameStatus;
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
        plist.notifyAllClients("notify", "Game started");
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
        if (getCurrentNumberOfPlayers()== getNumberOfPlayers()) {
            startGame();
        }

    }

    public void removePlayer(Player player) {
        plist.removePlayer(player);
        if (numberOfPlayers == 2 && plist.getCurrentNumberOfPlayers() == 1) {
            for (Player player1 : plist.getPlayers()) {
                player1.sendToClient("quit", "Victory");
                try {
                    player1.getSocket().close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void moveStudentsToHall() {
        plist.moveStudentsToHall(studentsBag);
    }

    /**
     * sets all the initial setup of the game:
     * <p>
     *     <ul>
     *         <li> Creates 12 islands </li>
     *         <li> adds randomly mother nature on the island </li>
     *         <li> adds two students of each color on the islands except the island with mother nature and its opposite</li>
     *         <li> fills the students bag with 120 students </li>
     *         <li> creates and fills with 3 or 4 students the cloud cards </li>
     *         <li> assigns the tower to the players </li>
     *         <li> moves the students from the student's bag to the hall </li>
     *         <li> extracts randomly 3 special cards </li>
     *     </ul>
     * </p>
     */
    public void setupGame() {
        gameStatus = "active";
        fillStudentsBag();
        createIslands();
        addMotherNatureToIsland();
        addStudentToIsland();

        for (Player player : plist.getPlayers()) {
            player.getDashboard().setupHall(numberOfPlayers);
        }
        cloudCardCreation();
        for (CloudCard cloudCard : cloudCards) {
            cloudCardFill(cloudCard);
        }
        assignTower();

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


    public String sendCharacterCardsDeck() {
        ArrayList<CharacterCard> cardList = new ArrayList<>();
        for (SpecialCard specialCard : cardsInGame) {
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
     * adds Mother Nature to a random Island, at the beginning of a new match
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
     * adds two students of each color on every island(except the island where there is Mother Nature and its opposite)
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
        /* if moves is a negative value it is rescaled to a positive value by added of the islands size */
        if (moves < 0) {
            moves = islands.size() + moves;
        }
        if (moves > playerMovesOfMN || moves < 1 ) {
            return false;
        } else {
            Island destination = islands.get(destinationIslandIndex);
            islandWithMN.setPresenceMN(false);
            destination.setPresenceMN(true);
            islandWithMN = destination;
            if (destination.getBlock()) {
                destination.setBlock(false);
                System.out.println("island blocked");
                return true;
            } else {
                islandWithMN.calcInfluence(plist);
                connectIsland();
            }
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
     * Creates 3 or 4 CloudCard in cloudCards and fills them with the correct amount of students
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


    /**
     * checks if two or more island have the same owner and in that case it unifies the islands into only one
     */
    public void connectIsland() {
        for (int i = 1; i < islands.size(); i++) {
            Island curr = islands.get(i);
            Island next;
            if (i == islands.size() - 1) {
                next = islands.get(0);
            } else next = islands.get(i + 1);
            if (Objects.equals(next.getOwner(), curr.getOwner()) && !Objects.equals(curr.getOwner(), "free") &&
                    !Objects.equals(next.getIdGroup(), curr.getIdGroup())) {
                next.setIdGroup(curr.getIdGroup());
                for (Student student : next.getStudents()) {
                    islands.get(i).addStudent(student);

                }
                for (Tower tower : next.getTowerArrayList()) {
                    islands.get(i).addTower(tower);
                }
                if (curr.getPresenceMN() || next.getPresenceMN()) {
                    islands.get(i).setPresenceMN(true);
                }
                islands.get(i).increaseDimension();
                islands.remove(next);
                i--;
            }
        }
        int i = 1;
        for (Island island : islands) {
            island.setId(i);
            i++;
        }
        /* if there are only 3 groups of islands the game has to finish */
        if (islands.size() == 3) {
            plist.notifyAllClients("notify", "the game has finished");
            calculateWinner();
        }


    }

    /**
     * assigns the teacher to a teacher table of the player who has more students of a color in his classroom
     */
    public void assignTeacher() {
        for (int i = 0; i < PawnColor.numberOfColors; i++) {
            for (int j = 0; j < numberOfPlayers - 1; j++) {
                Player current = plist.getPlayers().get(j);
                Player next = plist.getPlayers().get(j + 1);
                Dashboard currentDashboard = current.getDashboard();
                Dashboard nextDashboard = next.getDashboard();
                if (currentDashboard.countStudentByColor(teachers[i].getColor()) > nextDashboard.countStudentByColor(teachers[i].getColor())) {
                    switchTeacher(nextDashboard, currentDashboard, i);
                } else if (nextDashboard.countStudentByColor(teachers[i].getColor()) > currentDashboard.countStudentByColor(teachers[i].getColor())) {
                    switchTeacher(currentDashboard, nextDashboard, i);
                } else if (currentDashboard.countStudentByColor(teachers[i].getColor()) != 0) {
                    if (current.isTeacherAssignerModifier()) {
                        switchTeacher(nextDashboard, currentDashboard, i);

                    } else if (next.isTeacherAssignerModifier()) {
                        switchTeacher(currentDashboard, nextDashboard, i);
                    }
                }
            }
        }

    }

    /**
     * switches the teacher of the actual player when another player has more students of the teacher's color than the actual player
     */
    private void switchTeacher(Dashboard previousOwner, Dashboard newOwner, int i) {
        newOwner.addTeacherToTable(teachers[i]);
        if (previousOwner.getTeacherTable()[i] != null) {
            previousOwner.removeTeacherFromTable(teachers[i]);
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

    /**
     * calculate the player who have to play, based on his order of game. Then it notifies to the actual player that his turn is started
     */
    public void setActualPlayer() {
        for (Island island : islands) {
            island.setTowerMultiplier(1);
        }
        for (int i = 0;
             i < PawnColor.numberOfColors; i++) {
            PawnColor.values()[i].setInfluenceMultiplier(1);
        }

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
                if (phase == 1 && p.getHasPlayed()) {
                    p.setTeacherAssignerModifier(false);
                }
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
                p.setTeacherAssignerModifier(false);
            }
            plist.notifyAllClients("notify", "Planning phase");
            for (CloudCard c : cloudCards )
                cloudCardFill(c);
            phase = 0;
            round++;
            for (CloudCard cloudCard : cloudCards) {
                cloudCardFill(cloudCard);
            }
            if(studentsBag.endOfStudents()){
                calculateWinner();
            }
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


    public boolean chooseCloudCard(int numberOfCloudCard, Player player) {
        try{
            ArrayList<Student> students;
            students = cloudCards.get(numberOfCloudCard).getAllStudents();
            if (students.size() == 0){
                return true;
            }else {
                Dashboard actualDashboard = player.getDashboard();
                for (Student s : students)
                    actualDashboard.addStudentToHall(s);
                player.setHasPlayed(true);
                return false;
            }
        }catch (Exception IO){
            return true;
        }

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

    public void playAssistantCard(Player player, int cardNumber) {
        if (player.checkCardAvailability(cardNumber)) {
            player.sendToClient("error", "card already played");
            return;
        }
        boolean check = false;
        if (checkLastPlayedAssistant(cardNumber)) {
            player.sendToClient("error", "Assistant card already played by another player");
            for (AssistantCard assistantCard : player.getDeck().getCardsList()) {
                if (!checkLastPlayedAssistant(assistantCard.order())) {
                    player.sendToClient("warning", assistantCard.order()+ ") order: " + assistantCard.order() + " and #" + assistantCard.movesOfMN() + " moves of MN available");
                    check = true;
                }

            }
            if (!check) {
                player.playAssistantCard(cardNumber);
                setActualPlayer();
            }
            return;
        }
        player.playAssistantCard(cardNumber);
        setActualPlayer();
        if (player.deckSize()) {
            plist.notifyAllClients("notify", "the game has finished");
            calculateWinner();
        }


    }

    public boolean checkLastPlayedAssistant(int order) {

        for (Player player : plist.getPlayers()) {
            if (order == player.getLastPlayedAC()) {
                return true;
            }
        }

        return false;

    }


    public void playCharacterCard(int specialCardIndex, int index, PawnColor color) {
        SpecialCard specialCard = cardsInGame.get(specialCardIndex);
        specialCard.update(plist, actualPlayer, islands, color, index, studentsBag);
        specialCard.effect();
        actualPlayer.spendCoins(specialCard.getCost());
    }

    public void calculateWinner(){
        int towerNumber = 8;
        Player winner = null;
        for(Player p : plist.getPlayers()){
            if(p.getDashboard().getTowers().size() < towerNumber){
                towerNumber = p.getDashboard().getTowers().size();
                winner = p;
            }
            else if(p.getDashboard().getTowers().size() == towerNumber){
                winner = null;
            }
        }
        if(winner != null)
            winner.sendToClient("msg", "You are the winner!");
        else
            plist.notifyAllClients("msg", "It's a draw!");

    }


}
