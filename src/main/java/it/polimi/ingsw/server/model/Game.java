package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import it.polimi.ingsw.client.IdCloudCards;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.server.model.CharacterCards.CharacterCardStrategy;

import java.util.*;

/**
 * Game contains all the methods that implements the match and all objects that models the game.
 *
 * @author Nicolò D'Arpa, Zarlene Justrem De Mesa, Alessandro Costantini
 * @since 1.0
 */
public class Game {

    /**
     * MOVES indicates how many students, player can move in action phase, is 3 or 4 based on the number of the players in the match.
     */
    public static int MOVES;

    private static final int disconnectionTimeout = 10000;
    private String gameStatus = "Waiting for players";

    /**
     * It is the round of the match.
     */
    private int round = 0;

    /**
     * It is the gaming phase, there are two distinct phase:
     * Planning phase where the player play an assistant card (represented by 0)
     * Action phase where the player can move student from the hall to the classroom or to the islands (represented by 1).
     */
    private int phase = 0;
    private int numberOfPlayers = 3;

    /**
     * The players in a match
     * {@link PlayersList}
     */
    private final PlayersList plist = new PlayersList();


    /**
     * The bag where there are the student's pawn
     * {@link StudentsBag}
     */
    private final StudentsBag studentsBag = new StudentsBag();

    /**
     * The cloud card in a match
     * {@link CloudCard}
     */
    private final ArrayList<CloudCard> cloudCards = new ArrayList<>();

    /**
     * They are 3 id for the CloudCards.
     * {@link IdCloudCards}
     */
    private final IdCloudCards idCloudCards = new IdCloudCards();

    /**
     * A hash map that contains all the four decks.
     * {@link Deck}
     */
    private final Map<Integer, Deck> deckMap = new HashMap<>();

    /**
     * All the 12 islands in game.
     * {@link Island}
     */
    private final ArrayList<Island> islands = new ArrayList<>(12);

    /**
     * An array of 5 elements that contains all the teacher of every Pawn Color
     * {@link Teacher}
     * {@link PawnColor}
     */
    private final Teacher[] teachers = {new Teacher(PawnColor.GREEN), new Teacher(PawnColor.RED), new Teacher(PawnColor.YELLOW), new Teacher(PawnColor.MAGENTA), new Teacher(PawnColor.CYAN)};

    /**
     * Is the current island with mature nature on it.
     */
    private Island islandWithMN;

    /**
     * It is the player who is playing at the moment
     */
    private Player currentPlayer;

    /**
     * It is the deck that contains 3 special cards.
     * {@link SpecialDeck}
     */
    private final SpecialDeck specialDeck = new SpecialDeck();

    /**
     * An array that contains the 3 extracted special cards.
     * {@link CharacterCardStrategy}
     */
    private static ArrayList<CharacterCardStrategy> cardsInGame = new ArrayList<>();

    /**
     * It is the winner player.
     */
    private Player winner = null;

    /**
     * A flag that says if the current round is the last.
     */
    private boolean lastRound = false;


    public Game() {
    }

    /**
     * assigns the moves of the students in the action phase via moves according to how many players there are and creates the 4 decks
     *
     * @param numberOfPlayers is the number of players in the match
     */
    public Game(int numberOfPlayers) {
        if (numberOfPlayers == 2) {
            MOVES = 3;
        } else MOVES = 4;
        setNumberOfPlayers(numberOfPlayers);
        createDecks();
    }

    public int getPhase() {
        return phase;
    }

    public int getRound() {
        return round;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public PlayersList getPlist() {
        return plist;
    }

    /**
     * @param name is the name of the player
     * @return true if the plist contains player's name, false if is not.
     */
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

    public ArrayList<CharacterCardStrategy> getCardsInGame() {
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

    /**
     * Start a new game, notify all the clients connected and sets the actual player.
     */
    public void startGame() {
        gameStatus = "STARTED";
        System.out.println("Game starting");
        plist.notifyAllClients("startGame", "Game started");
        calculateCurrentPlayer();
    }

    /**
     * Adds new player to plist.
     *
     * @param name is player's name.
     */
    public void addPlayer(String name) {
        plist.addPlayer(new Player(name));
        if (getCurrentNumberOfPlayers() == numberOfPlayers) {
            setupGame();
        }
    }


    /**
     * Removes player from plist.
     *
     * @param player is the player to remove
     */
    public void removePlayer(Player player) {
        plist.removePlayer(player);
         if (!Objects.equals(gameStatus, "STARTED")) {
            notifyAllClients("endGame",player.getName()+ " left during game setup.\nGame ended");
            endOfGame();
        } else if (plist.getActivePlayers() == 1) {
            try {
                plist.notifyAllClients("error", "waiting for reconnection");
                Thread.sleep(disconnectionTimeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (plist.getActivePlayers() == 1) {
                calculateWinner();
            }
        } else if (plist.getActivePlayers() == 0) {
            System.out.println("Game end, all players disconnected");
            endOfGame();
        }  else if (player == currentPlayer) calculateCurrentPlayer();
    }

    /**
     * Moves students from studentsBag to players' hall.
     */
    public void moveStudentsToHall() {
        plist.moveStudentsToHall(studentsBag);
    }

    /**
     * sets all the initial setup of the game:
     *
     * <ul>
     *     <li> Creates 12 islands </li>
     *     <li> adds randomly mother nature on the island </li>
     *     <li> adds two students of each color on the islands except the island with mother nature and its opposite</li>
     *     <li> fills the students bag with 120 students </li>
     *     <li> creates and fills with 3 or 4 students the cloud cards </li>
     *     <li> assigns the tower to the players </li>
     *     <li> moves the students from the student's bag to the hall </li>
     *     <li> extracts randomly 3 special cards </li>
     *     <li> assigns the tower to the players</li>
     * </ul>
     */
    public void setupGame() {
        gameStatus = "active";
        fillStudentsBag();
        createIslands();
        addMotherNatureToIsland();
        addStudentToIsland();
        setUpPlayerDashboard();
        cloudCardCreation();
        setUpCloudCards();
        assignTower();
        moveStudentsToHall();
        extractSpecialCard();
        setUpOrderOfPlayers();
        this.round = 1;
        System.out.println("Setup complete");
    }

    /**
     * Set at the beginning of the game the order of the players to 0.
     */
    private void setUpOrderOfPlayers() {
        for (Player player : plist.getPlayers()) {
            player.setOrder(0);
        }
    }

    /**
     * For each player in the game, it sets their dashboard's hall.
     */
    private void setUpPlayerDashboard() {
        for (Player player : plist.getPlayers()) {
            player.getDashboard().setupHall(numberOfPlayers);
        }
    }

    /**
     * For each cloud card in the game, it fills them with the students
     */
    private void setUpCloudCards() {
        for (CloudCard cloudCard : cloudCards) {
            cloudCardFill(cloudCard);
        }
    }

    /**
     * Sends to client a JSON formatted string with the status of the island in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendIslands() {
        ArrayList<IslandStatus> statusList = new ArrayList<>();
        for (Island island : islands) {
            statusList.add(new IslandStatus(island));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);
    }

    /**
     * Sends to client a JSON formatted string with the status of one island in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendSingleIsland(Island island) {
        ArrayList<IslandStatus> islandStatuses = new ArrayList<>();
        islandStatuses.add(new IslandStatus(island));
        Gson gson = new Gson();
        return gson.toJson(islandStatuses);
    }

    /**
     * Sends to client a JSON formatted string with the status of the player's dashboard's hall in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendHall(Player player) {
        ArrayList<HallStatus> statusList = new ArrayList<>();
        statusList.add(new HallStatus(player.getDashboard()));
        Gson gson = new Gson();
        return gson.toJson(statusList);

    }

    /**
     * Sends to client a JSON formatted string with the status of the players' dashboards in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendDashboard() {
        ArrayList<DashboardStatus> statusList = new ArrayList<>();
        for (Player player : plist.getPlayers()) {
            statusList.add(new DashboardStatus(player.getName(), player.getDashboard()));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);
    }


    /**
     * Sends to client a JSON formatted string with the status of the enemy's dashboard in game.
     *
     * @param player is the player's client who received the formatted string.
     * @return a JSON formatted as a string.
     */
    public String sendEnemyDashboard(Player player) {
        ArrayList<EnemyDashboardStatus> statusList = new ArrayList<>();
        for (Player p : plist.getPlayers()) {
            if (!Objects.equals(p.getName(), player.getName()))
                statusList.add(new EnemyDashboardStatus(p.getName(), p.getDashboard()));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);
    }

    /**
     * Sends to client a JSON formatted string with the status of the player's dashboard in game.
     *
     * @param player is the player's client who received the formatted string.
     * @return a JSON formatted as a string.
     */
    public String sendPlayerDashboard(Player player) {
        ArrayList<DashboardStatus> statusList = new ArrayList<>();
        statusList.add(new DashboardStatus(player.getName(), player.getDashboard()));
        Gson gson = new Gson();
        return gson.toJson(statusList);
    }


    /**
     * Sends to client a JSON formatted string with the status of the cloud cards in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendCloudCards() {
        ArrayList<CloudCardStatus> statusList = new ArrayList<>();
        for (CloudCard cloudCard : cloudCards) {
            statusList.add(new CloudCardStatus(cloudCard));
        }
        Gson gson = new Gson();
        return gson.toJson(statusList);
    }

    /**
     * Sends to client a JSON formatted string with the status of the character cards in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendCharacterCardsDeck() {
        ArrayList<CharacterCard> cardList = new ArrayList<>();
        for (CharacterCardStrategy characterCardStrategy : cardsInGame) {
            cardList.add(new CharacterCard(characterCardStrategy));
        }
        Gson gson = new Gson();
        return gson.toJson(cardList);
    }

    /**
     * Sends to client a JSON formatted string with the status of the player in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendPlayer(Player player) {
        ArrayList<PlayersStatus> playersStatuses = new ArrayList<>();
        playersStatuses.add(new PlayersStatus(player));
        Gson gson = new Gson();
        return gson.toJson(playersStatuses);
    }

    /**
     * Sends to client a JSON formatted string with the status of the all players in game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendAllPlayers() {
        ArrayList<PlayersStatus> players = new ArrayList<>();
        for (Player p : plist.getPlayers()) {
            players.add(new PlayersStatus(p));
        }
        Gson gson = new Gson();
        return gson.toJson(players);
    }

    /**
     * Sends to client a JSON formatted string with the status of the game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendGameInfo() {
        ArrayList<GameInfoStatus> gameInfoStatuses = new ArrayList<>();
        gameInfoStatuses.add(new GameInfoStatus(phase, currentPlayer, round, numberOfPlayers));
        Gson gson = new Gson();
        return gson.toJson(gameInfoStatuses);
    }

    /**
     * Sends to client a JSON formatted string with the status of the game.
     *
     * @return a JSON formatted as a string.
     */
    public String sendHelp() {
        ArrayList<GameInfoStatus> gameInfoStatuses = new ArrayList<>();
        gameInfoStatuses.add(new GameInfoStatus(phase, currentPlayer, round, numberOfPlayers));
        Gson gson = new Gson();
        return gson.toJson(gameInfoStatuses);
    }

    /**
     * Sends to client a JSON formatted string with the status of the assistant cards' deck.
     *
     * @return a JSON formatted as a string.
     */
    public String sendDeck() {
        ArrayList<DeckStatus> deckStatusArrayList = new ArrayList<>();
        for (Deck deck : deckMap.values()) {
            deckStatusArrayList.add(new DeckStatus(deck));

        }
        Gson gson = new Gson();
        return gson.toJson(deckStatusArrayList);
    }

    /**
     * Adds to the arrayList islands, all the islands equals to the number of islands (12)
     */
    public void createIslands() {
        int numberOfIslands = 12;
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

    /**
     * Moves Mother Nature from an island to the destination island.
     *
     * @param player                 is who is playing.
     * @param destinationIslandIndex is the index of the motherNature's destination island.
     * @return true or false, respectively if mother nature can be moved to the selected destination island or not.
     */
    public boolean moveMN(Player player, int destinationIslandIndex) {
        int playerMovesOfMN = player.getMovesOfMN();
        int islandWithMNIndex = getIslandWithMNIndex();
        int moves = destinationIslandIndex - islandWithMNIndex;
        /* if moves is a negative value it is rescaled to a positive value by adding the islands array size */
        if (moves < 0) {
            moves = islands.size() + moves;
        }
        if (moves > playerMovesOfMN || moves < 1) {
            return false;
        } else {
            Island destination = islands.get(destinationIslandIndex);
            islandWithMN.setPresenceMN(false);
            destination.setPresenceMN(true);
            islandWithMN = destination;
            if (!islandWithMN.calculateInfluence(plist)) {
                player.sendToClient("Warning", "Island blocked, influence not calculated");
            }
            if (islandWithMN.getTowerFinished()) {
                calculateWinner();
            }
            player.setMovesOfMN(0);

            connectIsland();
            return true;
        }
    }


    /**
     * Fills the students bag with 120 students
     */
    private void fillStudentsBag() {
        studentsBag.fillBag(120);
    }

    public int getStudentsInBag() {
        return studentsBag.getNum();
    }

    /**
     * Creates 3 or 4 CloudCard in cloudCards and fills them with the correct amount of students
     */
    private void cloudCardCreation() {
        idCloudCards.extractRandomCard(numberOfPlayers);
        ArrayList<Integer> idCloudCardsInGame = idCloudCards.getIdOfCloudCardsInGame();
        if (numberOfPlayers == 2) {
            for (int i = 0; i < 2; i++) {
                cloudCards.add(new CloudCard(numberOfPlayers, idCloudCardsInGame.get(i)));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                cloudCards.add(new CloudCard(numberOfPlayers, idCloudCardsInGame.get(i)));
            }
        }


    }


    /**
     * Adds to the deckMap all the 4 decks.
     */
    private void createDecks() {
        deckMap.put(1, new Deck(1, "BLUE"));
        deckMap.put(2, new Deck(2, "PURPLE"));
        deckMap.put(3, new Deck(3, "GREEN"));
        deckMap.put(4, new Deck(4, "PINK"));
    }

    /**
     * extract the special cards
     */
    public void extractSpecialCard() {
        specialDeck.extractRandomCard();
        cardsInGame = specialDeck.getSpecialCardsInGame();
    }


    /**
     * checks if two or more island have the same owner and in that case it unifies the islands into only one
     */
    public void connectIsland() {
        for (int i = 0; i < islands.size(); i++) {
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
                    islands.get(i).addTowerToIsland(tower);
                }
                if (curr.getPresenceMN() || next.getPresenceMN()) {
                    islands.get(i).setPresenceMN(true);
                }
                islands.get(i).increaseDimension(next.getDimension());
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
        if (islands.size() < 4) {
            plist.notifyAllClients("notify", "Only three groups of islands");
            calculateWinner();
        }


    }

    /**
     *  Assigns a teacher to the player with the higher number of students of one color.
     *  Check if the teacher of the selected color has been already assigned,
     *  compare the number of students of every player with each other to pick the player
     *  with the higher number of students of one color
     *
     * @param color color of the students to count for teacher allocation
     */

    public void assignTeacher(PawnColor color) {
        int studentsNumber = 0;
        Player player = null;
        Player previous = null;
        for (Player player1 : plist.getPlayers()) {
            if (player1.getDashboard().getTeacherTable()[color.ordinal()] != null) {
                previous = player1;
                studentsNumber = previous.getDashboard().countStudentByColor(color);
            }
        }

        for (Player p : plist.getPlayers()) {
            if (p.isTeacherAssignerModifier()) {
                if (p.getDashboard().countStudentByColor(color) != 0 && p.getDashboard().countStudentByColor(color) >= studentsNumber) {
                    studentsNumber = p.getDashboard().countStudentByColor(color);
                    player = p;
                }
            } else {
                if (p.getDashboard().countStudentByColor(color) > studentsNumber) {
                    studentsNumber = p.getDashboard().countStudentByColor(color);
                    player = p;
                }
            }


        }
        if (player != null && previous != null) {
            switchTeacher(previous.getDashboard(), player.getDashboard(), color.ordinal());
        } else if (previous == null && player!=null) {
            player.getDashboard().addTeacherToTable(teachers[color.ordinal()]);
        }
    }
    /**
     * switches the teacher of the actual player when another player has more students of the teacher's color than the actual player
     */
    private void switchTeacher(Dashboard previousOwner, Dashboard newOwner, int i) {
        previousOwner.removeTeacherFromTable(teachers[i]);
        newOwner.addTeacherToTable(teachers[i]);

    }


    /**
     * Assigns the towers to each player at the beginning of a match.
     */
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

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * calculate the player who have to play, based on his order of game. Then it notifies to the actual player that his turn is started
     */
    public void calculateCurrentPlayer() {
        for (Island island : islands) {
            island.setTowerMultiplier(1);
        }
        for (int i = 0; i < PawnColor.numberOfColors; i++) {
            PawnColor.values()[i].setInfluenceMultiplier(1);
        }

        int max_order = 11;
        Player temp = null;
        if (round == 1 && phase == 0) {
            for (Player p : plist.getPlayers()) {
                if (!p.getHasPlayed() && p.isActive()) {
                    p.sendToClient("notify", "Your turn started, play an assistant card");
                    setCurrentPlayer(p);
                    System.out.println(currentPlayer.getName() + " turn");
                    plist.notifyAllClients("gameInfo", sendGameInfo());
                    return;
                }
            }
            nextPhase();
        } else {
            for (Player p : plist.getPlayers()) {
                if (phase == 1 && p.getHasPlayed() && p.isActive()) {
                    p.setTeacherAssignerModifier(false);
                }
                if (p.getOrder() < max_order && !p.getHasPlayed() && p.isActive()) {
                    temp = p;
                    max_order = p.getOrder();
                }
            }
            if (temp != null) {
                setCurrentPlayer(temp);
                temp.sendToClient("notify", "Your turn started");
                plist.notifyAllClients("gameInfo", sendGameInfo());
                System.out.println(currentPlayer.getName() + " turn");
            } else nextPhase();

        }


    }

    /**
     * Set up a new phase, check if all players have played and move from planning to action phase.
     * <p>
     * Whenever a phase changes, set the current player and check if there are no more students in the student's bag.
     * Checks if it is the lastRound and calculates the winner if it is.
     */
    public void nextPhase() {
        if (phase == 0) {
            for (Player p : plist.getPlayers()) {
                p.setHasPlayed(false);
            }
            //plist.notifyAllClients("notify", "Action phase");
            phase = 1;
        } else if (phase == 1) {
            for (Player p : plist.getPlayers()) {
                p.setHasPlayed(false);
                p.setLastPlayedAC(0);
                p.resetMovesOfStudents();
                p.setTeacherAssignerModifier(false);
            }
            if (lastRound) {
                calculateWinner();
                return;
            }
            //plist.notifyAllClients("notify", "Planning phase");
            for (CloudCard c : cloudCards)
                cloudCardFill(c);
            phase = 0;
            round++;
            if (studentsBag.endOfStudents()) {

                calculateWinner();

            }
        }
        calculateCurrentPlayer();

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * @return the Island with mn.
     */
    public Island getIslandWithMN() {
        for (Island i : islands) {
            if (i.getPresenceMN()) {
                this.islandWithMN = i;
            }
        }
        return islandWithMN;
    }

    /**
     * @return the index of the island with mn.
     */
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


    /**
     * player chooses a cloud card, if the choice is successful the students are transferred from the cloud card to the player's hall.
     *
     * @param indexCloudCard is the cloud card chosen
     * @param player         is who is playing
     */
    public void chooseCloudCard(int indexCloudCard, Player player) {
        ArrayList<Student> students;
        try {
            if (!studentsBag.endOfStudents()) {
                if (cloudCards.get(indexCloudCard - 1).getStudents().size() != 0) {
                    students = cloudCards.get(indexCloudCard - 1).getAllStudents();
                    Dashboard actualDashboard = player.getDashboard();
                    for (Student s : students)
                        actualDashboard.addStudentToHall(s);
                    notifyAllClients("cloudCard", sendCloudCards());
                    player.setHasPlayed(true);
                    player.sendToClient("dashboard", sendPlayerDashboard(player));
                    calculateCurrentPlayer();
                } else
                    player.sendToClient("error", "Cloud card already chosen by another player");
            } else {
                player.setHasPlayed(true);
                player.sendToClient("notify", "Student bag is empty, no more students");
                calculateCurrentPlayer();
            }

        } catch (Exception ignored) {
        }


    }

    /**
     * Fill up cloudCard with 3 or 4 students each depending on the number of players
     */
    private void cloudCardFill(CloudCard cloudCard) {
        if (!studentsBag.endOfStudents()) {
            if (cloudCard.getStudents().isEmpty()) {
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
        }


    }

    /**
     * set the deck to the player according to his choice
     *
     * @param deckNumber is the identifier of the deck
     * @param player     is who are choosing the deck
     */
    public void chooseDeck(int deckNumber, Player player) {
        Deck deck = deckMap.get(deckNumber);
        for (Player ignored : plist.getPlayers()) {
            if (deck.getChosen()) {
                player.sendToClient("error", "chooseDeck", "Deck already chosen by another player");
                return;
            }

        }
        player.setDeck(deck);
        player.sendToClient("confirmation", "chooseDeck", "Deck " + deckNumber + " chosen");
        deck.setPlayer(player);
        if (getCurrentNumberOfPlayers() == getNumberOfPlayers()) {
            for (Player player1 : plist.getPlayers()) {
                if (player1.getDeck() == null) {
                    return;
                }
            }
            startGame();
        }
    }


    /**
     * Plays the selected assistant card.
     * Check if the selected card is valid, send an error message to the client if an error occur.
     * If the last card in the deck has been played flags true the lastRound variable.
     *
     * @param player     the player who is playing the assistant card
     * @param cardNumber the index of the assistant card in the deck
     */
    public boolean playAssistantCard(Player player, int cardNumber) {
        if (player.checkCardAvailability(cardNumber)) {
            player.sendToClient("error", "You already played this card");
            return false;
        }

        boolean check = false;
        if (checkLastPlayedAssistant(cardNumber)) {
            for (AssistantCard assistantCard : player.getDeck().getCardsList()) {
                if (!checkLastPlayedAssistant(assistantCard.order()))
                    check = true;
            }
            if (check) {
                player.sendToClient("error", "Assistant card already played by another player");
                return false;
            }
        }
        player.playAssistantCard(cardNumber);
        calculateCurrentPlayer();
        //player.sendToClient("notify", "Now it's " + currentPlayer.getName() + " turn");

        if (player.deckSize()) { //return 1 if the player has finished his cards
            plist.notifyAllClients("notify", "This is the last turn");
            lastRound = true;
        }
        return true;

    }

    /**
     * Check which is the last cards played by the other player during the current round
     *
     * @param order is the order of the card.
     * @return true if the card is the last card played.
     */
    public boolean checkLastPlayedAssistant(int order) {
        for (Player player : plist.getPlayers()) {
            if (order == player.getLastPlayedAC()) {
                return true;
            }
        }
        return false;

    }

    /**
     * Checks that the current player has enough coins to play the selected character card,
     *
     * @param characterCard character card to play
     * @param index         island index to activate the effect on
     * @param color         color selected for the card's effect
     */

    public boolean playCharacterCard(CharacterCardStrategy characterCard, int index, PawnColor color) {
        if (characterCard.getCost() > currentPlayer.getCoins()) {
            currentPlayer.sendToClient("error", "You don't have enough coins to play this card");
            return false;
        } else {
            currentPlayer.spendCoins(characterCard.getCost());
            characterCard.update(plist, currentPlayer, islands, color, index, studentsBag);
            characterCard.effect();
            notifyAllClients("msg", currentPlayer.getName() + " has played the card " + characterCard.getName());
            return true;
        }


    }

    /**
     * Calculates the winner of the match.
     * If only one player is active, he is the winner without considering any other parameter.
     * Counts which player has the most towers on the islands and sends a message to the winner.
     */
    private void calculateWinner() {
        int towerNumber = 8;
        if (plist.getActivePlayers() == 1) {

            for (Player player : plist.getPlayers()) {
                if (player.isActive()) winner = player;
            }
        } else {
            for (Player player : plist.getPlayers()) {
                if (player.getDashboard().getTowers().size() < towerNumber) {
                    towerNumber = player.getDashboard().getTowers().size();
                    winner = player;
                } else if (player.getDashboard().getTowers().size() == towerNumber) {
                    winner = null;
                }
            }
        }

        if (winner != null) {
            System.out.println("Winner: " + winner.getName());
            winner.sendToClient("endGame", "You are the winner!");
            for (Player player : plist.getPlayers()) {
                if (player != winner) player.sendToClient("endGame", winner.getName() + " is the winner");
            }
        } else {
            System.out.println("Draw");
            plist.notifyAllClients("endGame", "It's a draw!");
        }
        endOfGame();

    }


    private void endOfGame() {
        gameStatus = "ENDED";
    }

    public void notifyAllClients(String type, String message) {
        plist.notifyAllClients(type, message);
    }


}
