package it.polimi.ingsw.client.view;


import com.google.gson.Gson;
import it.polimi.ingsw.client.Command;
import it.polimi.ingsw.client.LineClient;
import it.polimi.ingsw.comunication.*;
import it.polimi.ingsw.server.model.PawnColor;


import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * ClientOut is a thread class that manages the visual aspect of the CLI.
 */

public class ClientOut extends Thread {
    /**
     * ANSI CODE to reset to default the color of print
     */
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * ANSI CODE to print yellow
     */
    private static final String YELLOW = "\u001B[33m";
    /**
     * ANSI CODE to print red
     */
    private static final String RED = "\u001B[31m";
    /**
     * ANSI CODE to print green
     */
    private static final String GREEN = "\u001B[32m";
    /**
     * Number of characters occupied by a single island in the islands' table when printed on console.
     */
    private static final String SPACE = "12";

    private final Gson gson = new Gson();
    /**
     * Contains the message sent from the server.
     */
    private TextMessage message;

    /**
     * Receives incoming messages from the server.
     */
    private final BufferedReader socketIn;

    /**
     * Connection socket to the server.
     */
    private final Socket socket;

    /**
     * Hash Map of all available methods associated with the corresponding command sent by the server
     */
    private final HashMap<String, Command> commandHashMap = new HashMap<>();


    /**
     * Constructor method for ClientOut, it maps every method with the string corresponding
     * the message type received by the server
     *
     * @param socketIn buffered reader, it reads incoming messages from the server connection
     * @param socket   connection socket with the server
     */
    public ClientOut(BufferedReader socketIn, Socket socket) {
        this.socketIn = socketIn;
        this.socket = socket;
        commandHashMap.put("startGame", this::startGame);
        commandHashMap.put("avlGames", this::printGames);
        commandHashMap.put("assistantDecks", this::printAssistantDecks);
        commandHashMap.put("msg", this::printMsg);
        commandHashMap.put("error", this::manageError);
        commandHashMap.put("confirmation", this::manageConfirmation);
        commandHashMap.put("warning", this::printWarning);
        commandHashMap.put("notify", this::printNotify);
        commandHashMap.put("endGame", this::endGame);
        commandHashMap.put("characterCards", this::printCharacterCards);
        commandHashMap.put("islands", this::printIslands);
        commandHashMap.put("dashboard", this::printDashboard);
        commandHashMap.put("cloudCard", this::printCloudCard);
        commandHashMap.put("hall", this::printHall);
        commandHashMap.put("player", this::printPlayer);
        commandHashMap.put("quit", this::quit);
        commandHashMap.put("gameInfo", this::printGameInfo);
        commandHashMap.put("cardsPlayed", this::printCardsPlayed);
        commandHashMap.put("enemyDashboard", this::printDashboard);
        commandHashMap.put("help", this::printHelp);
    }


    /**
     * Runs until the connection socket is open,
     * when the socket gets closed it stops and prints an error message.
     */

    @Override
    public void run() {
        while (!socket.isClosed()) {
            String socketLine;
            try {
                socketLine = socketIn.readLine();
                if (socketLine != null) {
                    Gson gson = new Gson();
                    message = gson.fromJson(socketLine, TextMessage.class);
                    try {
                        commandHashMap.get(message.type).runCommand();
                    } catch (Exception e) {
                        System.out.println("Received " + message.type + "\n" + message.message);
                    }

                } else
                    break;
            } catch (IOException e) {
                break;
            }


        }
        System.out.println("No connection to the server");
        System.out.println("Exit....");
        System.exit(0);

    }

    /**
     * Prints the state of the game int the available games list,
     * it shows game id, the current number of player the joined the game and the total number of player the game is set to host
     */
    private void printGames() {
        GameStatus[] gameStatuses = gson.fromJson(message.message, GameStatus[].class);
        for (GameStatus gameStatus : gameStatuses) {
            System.out.println("-" + gameStatus.gameId + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players: " + gameStatus.playersName);
        }
        LineClient.joinGame();
    }

    /**
     * Print the list of assistants card decks with the relative player if the deck is already selected
     */
    private void printAssistantDecks() {
        DeckStatus[] deckStatusArrayList = gson.fromJson(message.message, DeckStatus[].class);
        for (DeckStatus deckStatus : deckStatusArrayList) {
            System.out.println(deckStatus.id + "-" + deckStatus.color + ": " + deckStatus.playerName);
        }
    }

    private void printHelp(){
        int studentsMoves = 0;
        GameInfoStatus gameInfoStatus = gson.fromJson(message.message, GameInfoStatus[].class)[0];

        if(gameInfoStatus.numberOfPlayer == 2)
            studentsMoves = 3;
        else if(gameInfoStatus.numberOfPlayer == 3)
            studentsMoves = 4;

        if(Objects.equals(gameInfoStatus.phase, "Planning phase"))
            System.out.println("Play an assistant card to determine your turn order and available Mother Nature moves");
        else if(Objects.equals(gameInfoStatus.phase, "Action phase")){
            System.out.println("1.Select " + studentsMoves + " student from your to move to your classroom or to islands");
            System.out.println("2.Move Mother Nature on an island of your choice");
            System.out.println("3.Choose a cloud card");
        }

    }


    /**
     * Clear the console on game start and prints the message
     */
    private void startGame() {
        LineClient.clearConsole();
        printNotify();
    }

    /**
     * Prints the incoming message and closes the connection socket
     *
     * @throws IOException If an exception occur with socket connection
     */
    private void quit() throws IOException {
        System.out.println(message.message);
        socket.close();
    }


    /**
     * Prints the regular messages incoming from the server of type:  <code>msg</code>
     */
    private void printMsg() {
        System.out.println(message.message);
    }


    /**
     * Prints in red every message incoming from the server of type: <code>error</code>
     * and calls the correct method if specified in the message
     */
    private void manageError() {
        System.out.println(RED + message.message + ANSI_RESET);
        CompletableFuture.runAsync(() -> {
            switch (message.context) {
                case "chooseDeck" -> LineClient.chooseDeck();
                case "joinGame01" -> LineClient.joinGame();
                case "joinGame02", "login01" -> LineClient.initSetup();
                case "login02" -> {
                    try {
                        LineClient.login();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });


    }

    /**
     * Prints in green every message of type: <code>confirmation</code>
     * and calls the method of LineClient indicated by the message context
     */
    private void manageConfirmation() {
        System.out.println(GREEN + message.message + ANSI_RESET);
        CompletableFuture.runAsync(() -> {
            switch (message.context) {
                case "newGame", "joinGame" -> {
                    try {
                        LineClient.login();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "login" -> LineClient.chooseDeck();
                case "chooseDeck", "logBack" -> LineClient.stdinScan();
            }
        });


    }

    /**
     * Prints in yellow messages of type: <code>warning</code>
     */
    private void printWarning() {
        System.out.println(YELLOW + message.message + ANSI_RESET);
    }

    /**
     * Prints in green messages of type: <code>notify</code>
     */
    private void printNotify() {
        System.out.println(GREEN + message.message + ANSI_RESET);
    }


    /**
     * Prints a symbol + representing a pawn in the relative color or _ if there isn't any pawn
     *
     * @param color The color code of pawn or null if the pawn isn't present in that position
     */
    private void draw(String color) {
        if (color == null) {
            System.out.print("_");
        } else System.out.printf(color + "+");
        System.out.print(ANSI_RESET);

    }

    /**
     * Decode to json the message field of the message received from the server as a list of LineClient and prints all the islands.
     * Every StringBuilder parameter creates a string with the corresponding field of every island, every StringBuilder
     * is added to the Table ArrayList to print  horizontally all the islands in game.     *
     */
    private void printIslands() {
        IslandStatus[] statuses = gson.fromJson(message.message, IslandStatus[].class);
        System.out.println("Islands");
        StringBuilder id = new StringBuilder();
        StringBuilder dim = new StringBuilder();
        StringBuilder owner = new StringBuilder();
        StringBuilder towers = new StringBuilder();
        StringBuilder mn = new StringBuilder();
        StringBuilder line = new StringBuilder();
        StringBuilder students = new StringBuilder();
        for (IslandStatus islandStatus : statuses) {
            int space = Integer.parseInt(SPACE) + 2;
            line.append("----------------");
            if (islandStatus.presenceMN) {
                id.append(String.format("%s" + YELLOW + " %-" + SPACE + "s" + ANSI_RESET + " %s", "|", "Id #: " + islandStatus.id, "|"));
            } else id.append(String.format("%s %-" + SPACE + "s %s", "|", "Id #: " + islandStatus.id, "|"));
            dim.append(String.format("%s %-" + SPACE + "s %s", "|", "Dim: " + islandStatus.dimension, "|"));
            owner.append(String.format("%s %-" + SPACE + "s %s", "|", "Own: " + islandStatus.owner, "|"));
            towers.append(String.format("%s %-" + SPACE + "s %s", "|", "Twrs #:" + islandStatus.towerNumber, "|"));
            mn.append(String.format("%s %-" + SPACE + "s %s", "|", "MN: " + islandStatus.presenceMN, "|"));
            int length = students.length();
            students.append("| St:");
            for (Integer student : islandStatus.studentColorOrdinal) {
                students.append(PawnColor.values()[student].getCode()).append("+").append(ANSI_RESET);
                space = space + 9;
            }
            int i = students.length();
            while (i < length + space) {
                students.append("_");
                i++;
            }
            students.append(" |");

        }
        ArrayList<StringBuilder> table = new ArrayList<>();
        table.add(id);
        table.add(dim);
        table.add(owner);
        table.add(towers);
        table.add(mn);
        table.add(students);
        System.out.println(line);
        for (StringBuilder row : table) {
            System.out.println(row);
        }
        System.out.println(line);
    }


    /**
     * Print the dashboard of a player with the relative students, teachers and towers
     */
    private void printDashboard() {
        DashboardStatus[] dashboardStatuses = gson.fromJson(message.message, DashboardStatus[].class);
        for (DashboardStatus dashboardStatus : dashboardStatuses) {
            System.out.println("""

                    ============
                    """);
            System.out.println("Dashboard of " + dashboardStatus.nameOwner);
            System.out.print("Hall: ");
            for (String student : dashboardStatus.studentsHall) {
                System.out.print("|");
                draw(student);
                System.out.print("| ");
            }
            System.out.println(" ");
            System.out.println("Classrooms");
            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 10; j++) {
                    System.out.print("|");
                    draw(dashboardStatus.studentsClassroom[i][j]);
                    System.out.print("| ");
                }
                System.out.println(" ");
            }
            System.out.print("Teacher Table: ");
            for (String teacher : dashboardStatus.teacherTable) {
                System.out.print("|");
                draw(teacher);
                System.out.print("| ");
            }
            System.out.println(" ");
            System.out.println(dashboardStatus.towerColor + " towers available: " + dashboardStatus.towers);


        }
    }


    /**
     * Prints a cloud card with the students in it
     */
    private void printCloudCard() {
        CloudCardStatus[] cloudCardStatuses = gson.fromJson(message.message, CloudCardStatus[].class);
        for (CloudCardStatus cloudCardStatus : cloudCardStatuses) {
            System.out.println("=====CLoudCard=====");
            for (String student : cloudCardStatus.students) {
                draw(student);
                System.out.print(" ");
            }
            System.out.println(" ");

        }
    }

    /**
     * Prints basic information of the player like the username, available moves of mother nature and the coins available.
     */
    private void printPlayer() {
        PlayersStatus[] playersStatuses = gson.fromJson(message.message, PlayersStatus[].class);
        for (PlayersStatus playersStatus : playersStatuses) {
            System.out.println(YELLOW + "-Username: " + playersStatus.name + "  -Moves MN: " + playersStatus.movesOfMN + " -Wallet: " + playersStatus.wallet + ANSI_RESET);
        }
    }


    /**
     * Prints basic information of the current games such as the round, the current player and the phase in progress
     */
    private void printGameInfo() {
        GameInfoStatus[] gameInfoStatuses = gson.fromJson(message.message, GameInfoStatus[].class);
        for (GameInfoStatus gameInfoStatus : gameInfoStatuses) {
            System.out.println(YELLOW + "-Round: " + gameInfoStatus.round + " -Actual Player: " + gameInfoStatus.actualPlayer + " -Phase: " + gameInfoStatus.phase + ANSI_RESET);
        }
    }

    /**
     * Prints the list of character cards with the relative name, cost and effect
     */
    private void printCharacterCards() {
        CharacterCard[] characterCards = gson.fromJson(message.message, CharacterCard[].class);
        for (CharacterCard characterCard : characterCards) {
            System.out.println("====");
            System.out.println("Name: " + characterCard.name);
            System.out.println("Effect: " + characterCard.effect);
            System.out.println("Cost: " + characterCard.cost);
        }
    }


    /**
     * Prints list of students present in a hall
     */
    private void printHall() {
        HallStatus[] hallStatuses = gson.fromJson(message.message, HallStatus[].class);
        for (HallStatus hallStatus : hallStatuses) {
            System.out.println("=====Hall=====");
            for (String student : hallStatus.students) {
                System.out.print("|");
                draw(student);
                System.out.print("| ");
            }
            System.out.println(" ");


        }
    }


    /**
     * Prints a message with the information about the assistant card played
     */
    private void printCardsPlayed() {
        PlayersStatus[] playersStatuses = gson.fromJson(message.message, PlayersStatus[].class);
        for (PlayersStatus playersStatus : playersStatuses) {
            System.out.println("Your deck id: " + playersStatus.deckId);
            System.out.println("Cards Played: ");
            for (int i = 0; i < playersStatus.cardsPlayed.size(); i++)
                System.out.println("order: " + playersStatus.cardsPlayed.get(i));
        }
    }

    /**
     * @throws IOException If an exception append closing the socket
     */
    private void endGame() throws IOException {
        printNotify();
        socket.close();
    }


}

