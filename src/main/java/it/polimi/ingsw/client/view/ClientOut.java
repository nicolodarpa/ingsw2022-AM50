package it.polimi.ingsw.client.view;


import com.google.gson.Gson;
import it.polimi.ingsw.client.ClientInput;
import it.polimi.ingsw.client.LineClient;
import it.polimi.ingsw.comunication.*;


import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class ClientOut extends Thread {
    private static final String ANSI_RESET = "\u001B[0m";

    private static final String CYAN = "\u001B[34m";

    private static final String MAGENTA = "\u001B[35m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String SPACE = "10";

    private final Gson gson = new Gson();
    private TextMessage message;

    private final BufferedReader socketIn;

    private final Socket socket;

    private final HashMap<String, Commd> commandHashMap = new HashMap<>();

    private ClientInput clientInput;

    public interface Commd {
        void runCommand() throws IOException;

    }

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
        commandHashMap.put("characterCards", this::printCharacterCards);
        commandHashMap.put("islands", this::printIslands);
        commandHashMap.put("dashboard", this::printDashboard);
        commandHashMap.put("cloudCard", this::printCloudCard);
        commandHashMap.put("hall", this::printHall);
        commandHashMap.put("studentsRoom", this::printStudentsRoom);
        commandHashMap.put("player", this::printPlayer);
        commandHashMap.put("quit", this::quit);
        commandHashMap.put("gameInfo", this::printGameInfo);
        commandHashMap.put("singleIsland", this::printSingleIsland);
    }


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

                }
            } catch (IOException e) {
                System.out.println("No connection to the server");
                break;
            }


        }

    }

    private void printGames() {
        GameStatus[] gameStatuses = gson.fromJson(message.message, GameStatus[].class);
        for (GameStatus gameStatus : gameStatuses) {
            System.out.println("-" + gameStatus.gameId + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players: " + gameStatus.playersName);
        }
    }

    private void printAssistantDecks() {
        DeckStatus[] deckStatusArrayList = gson.fromJson(message.message, DeckStatus[].class);
        for (DeckStatus deckStatus : deckStatusArrayList) {
            System.out.println(deckStatus.id + "-" + deckStatus.color + ": " + deckStatus.playerName);
        }
    }

    private void startGame() {
        LineClient.clearConsole();
        printNotify();
    }

    private void quit() throws IOException {
        System.out.println(message.message);
        socket.close();
    }


    private void printMsg() {
        System.out.println(message.message);
    }

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

    private void manageConfirmation() throws IOException {
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
                case "chooseDeck" -> {
                    try {
                        LineClient.stdinScan();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });


    }

    private void printWarning() {
        System.out.println(YELLOW + message.message + ANSI_RESET);
    }

    private void printNotify() {
        System.out.println(GREEN + message.message + ANSI_RESET);
    }


    private void draw(String color) {
        if (color == null) {
            System.out.print("_");
        } else System.out.printf(color + "+");
        System.out.print(ANSI_RESET);

    }

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
            line.append("--------------");
            if (islandStatus.presenceMN) {
                id.append(String.format("%s" + YELLOW + " %-" + SPACE + "s" + ANSI_RESET + " %s", "|", "Id #: " + islandStatus.id, "|"));
            } else id.append(String.format("%s %-" + SPACE + "s %s", "|", "Id #: " + islandStatus.id, "|"));
            dim.append(String.format("%s %-" + SPACE + "s %s", "|", "Dim: " + islandStatus.dimension, "|"));
            owner.append(String.format("%s %-" + SPACE + "s %s", "|", "Own: " + islandStatus.owner, "|"));
            towers.append(String.format("%s %-" + SPACE + "s %s", "|", "Twrs #:" + islandStatus.towerNumber, "|"));
            mn.append(String.format("%s %-" + SPACE + "s %s", "|", "MN: " + islandStatus.presenceMN, "|"));
            int length = students.length();
            students.append("| St:");
            for (String student : islandStatus.students) {
                students.append(student).append("+").append(ANSI_RESET);
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

    private void printSingleIsland(){
        IslandStatus[] statuses = gson.fromJson(message.message, IslandStatus[].class);
        for (IslandStatus islandStatus : statuses) {
            System.out.println("Id: " + islandStatus.id + " -Owner: " + islandStatus.owner + " -Dimension: " + islandStatus.dimension);
        }
    }


    private void printDashboard() {
        DashboardStatus[] dashboardStatuses = gson.fromJson(message.message, DashboardStatus[].class);
        for (DashboardStatus dashboardStatus : dashboardStatuses) {
            System.out.println("============");
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
            System.out.println("Towers available: " + dashboardStatus.towers);


        }
    }

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

    private void printPlayer() {
        PlayersStatus[] playersStatuses = gson.fromJson(message.message, PlayersStatus[].class);
        for (PlayersStatus playersStatus : playersStatuses) {
            System.out.println(YELLOW + "-Username: " + playersStatus.name + "  -Moves MN: " + playersStatus.movesOfMN + ANSI_RESET+ " -Wallet: " +playersStatus.wallet);
        }
    }

    private void printGameInfo(){
        GameInfoStatus[] gameInfoStatuses = gson.fromJson(message.message, GameInfoStatus[].class);
        for(GameInfoStatus gameInfoStatus : gameInfoStatuses){
            System.out.println(YELLOW + "\n-Round: " + gameInfoStatus.round + "\n-Actual Player: " + gameInfoStatus.actualPlayer + "\n-Phase: " + gameInfoStatus.phase +  ANSI_RESET);
        }
    }

    private void printCharacterCards() {
        CharacterCard[] characterCards = gson.fromJson(message.message, CharacterCard[].class);
        for (CharacterCard characterCard : characterCards) {
            System.out.println("====");
            System.out.println("Name: " + characterCard.name);
            System.out.println("Effect: " + characterCard.effect);
            System.out.println("Cost: " + characterCard.cost);
        }
    }

    private void printHall() {
        HallStatus[] hallStatuses = gson.fromJson(message.message, HallStatus[].class);
        for (HallStatus hallStatus : hallStatuses) {
            System.out.println("=====Hall: ");
            for (String student : hallStatus.students) {
                System.out.print("|");
                draw(student);
                System.out.print("| ");
            }
            System.out.println(" ");



        }
    }

    private void printStudentsRoom() {
        StudentRoom studentRoom = gson.fromJson(message.message, StudentRoom.class);
        System.out.println("=====Students Room: ");
        for (String student : studentRoom.students) {
            System.out.print("|");
            draw(student);
            System.out.print("| ");
        }

        System.out.println(" ");
    }


}

