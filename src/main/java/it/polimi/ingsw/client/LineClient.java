package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientInput;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.ClientOut;
import it.polimi.ingsw.comunication.DeckStatus;
import it.polimi.ingsw.comunication.GameStatus;
import it.polimi.ingsw.comunication.TextMessage;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class LineClient {
    private final static String ANSI_PRIMARY = "\u001B[36m";
    private final static String ANSI_SECONDARY = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private ClientInput clientInput;

    private BufferedReader socketIn;

    private final Gson gson = new Gson();
    private Scanner stdin;
    private final String ip;
    private final int port;

    public LineClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    public void startClient() {
        System.out.println(ANSI_PRIMARY + "====Eriantys CLI Client====" + ANSI_RESET);
        Socket socket;
        try {
            socket = new Socket(ip, port);
            System.out.println(ANSI_SECONDARY + "Connection established" + ANSI_RESET);
            socketIn = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            stdin = new Scanner(System.in);
            ClientOut clientOut = new ClientOut(socketIn, socket);
            clientInput = ClientInput.getInstance();
            clientInput.setSocketOut(socketOut);
            initSetup();
            chooseDeck();
            clientOut.start();
            while (!socket.isClosed()) {
                String inputLine = stdin.nextLine();
                switch (inputLine) {

                    case "clear" -> clearConsole();
                    case "play character card" -> playCharacterCard();
                    case "play assistant card" -> playAssistantCard();
                    case "move student to island" -> moveStudentToIsland();
                    case "move student to classroom" -> moveStudentToClassroom();
                    case "move mn" -> moveMN();
                    case "choose cc" -> chooseCC();
                    case "islands" -> clientInput.sendString("islands", "");
                    case "dashboard" -> clientInput.sendString("dashboard", "");
                    case "cloud cards" -> clientInput.sendString("sendCloudCards", "");
                    default -> printCommands();

                }

            }


        } catch (Exception e) {
            System.out.println("Connection refused, no server online\n" +
                    "Start a server before launching the client");
        }

    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println(ex);
        }
        System.out.println(ANSI_PRIMARY + "====Eriantys CLI Client====" + ANSI_RESET);
    }


    private void printCommands() {
        System.out.println("""
                Available commands:
                -play character card
                -play assistant card
                -move student to island
                -move student to classroom
                -move mn
                -choose cc
                -islands (show islands)
                -dashboard (show dashboards of both players)
                -cloud cards (show cloud cards)
                -clear""");
    }


    private void initSetup() throws IOException {

        System.out.println("0-New Game\n" +
                "1-Join Game");
        while (true) {
            String string = stdin.nextLine();
            if ("0".equals(string)) {
                newGame();
                break;
            } else if ("1".equals(string)) {
                joinGame();
                break;
            }
            System.out.println("Select\n0-New Game\n1-Join Game");
        }

        login();
    }

    private void newGame() {
        System.out.println("Starting new Game\nHow many players?");
        String value = stdin.nextLine();
        if (!Objects.equals(value, "2") && !Objects.equals(value, "3")) {
            value = "2";
        }
        clientInput.sendString("newGame", value);
    }

    private void joinGame() throws IOException {
        clientInput.sendString("avlGames", "");
        String msg = socketIn.readLine();
        TextMessage message = gson.fromJson(msg, TextMessage.class);
        if (Objects.equals(message.type, "error")) {
            System.out.println(message.message);
            newGame();
            return;
        }
        GameStatus[] gameStatuses = gson.fromJson(message.message, GameStatus[].class);
        int i = 0;
        for (GameStatus gameStatus : gameStatuses) {
            System.out.println("-"+i + ": " + gameStatus.currentNumber + "/" + gameStatus.totalPlayers + " players: " + gameStatus.playersName);
            i++;
        }
        clientInput.sendString("joinGame", stdin.nextLine());
        msg = socketIn.readLine();
        message = gson.fromJson(msg, TextMessage.class);
        if (Objects.equals(message.type, "errInvIndex")) {
            System.out.println(message.message);
            joinGame();
        } else System.out.println(message.message);
    }

    private void login() throws IOException {
        System.out.print("Login\nUsername: ");
        String username = stdin.nextLine();
        clientInput.sendString("login", username);
        String response = socketIn.readLine();
        TextMessage message = gson.fromJson(response, TextMessage.class);
        if (Objects.equals(message.type, "error02")) { //name already in use
            System.out.println(message.message);
            login();
            return;
        } else if (Objects.equals(message.type, "error01")) { //max number of players reached
            System.out.println(message.message);
            initSetup();
            return;
        }
        System.out.println(message.message);
    }

    private void chooseDeck() throws IOException {
        clientInput.sendString("sendAssistantDecks", "");
        String response = socketIn.readLine();
        TextMessage message = gson.fromJson(response, TextMessage.class);
        DeckStatus[] deckStatusArrayList = gson.fromJson(message.message, DeckStatus[].class);
        System.out.println("Select your deck:");
        for (DeckStatus deckStatus : deckStatusArrayList) {
            System.out.println(deckStatus.id + "-" + deckStatus.color + ": " + deckStatus.playerName);
        }
        String index = stdin.nextLine();
        while (!Objects.equals(index, "4") && !Objects.equals(index, "1") && !Objects.equals(index, "2") && !Objects.equals(index, "3")) {
            System.out.println("Please input a valid index");
            index = stdin.nextLine();
        }

        clientInput.sendString("chooseDeck", index);
        response = socketIn.readLine();
        message = gson.fromJson(response, TextMessage.class);
        if (Objects.equals(message.type, "error")) {
            System.out.println(message.message);
            chooseDeck();
        } else System.out.println(message.message);


    }

    private void playCharacterCard() {
        System.out.println("Select character card to play");
        clientInput.sendString("characterCards", "");
        String index;

        while (true) {
            index = stdin.nextLine();
            if (Objects.equals(index, "1") || Objects.equals(index, "2") || Objects.equals(index, "3")) {
                break;
            } else {
                System.out.println("Please input a valid index: 1-3");
            }
        }

        System.out.println("Select island or color if necessary");
        System.out.println("Select the color\n0-CYAN\n1-MAGENTA\n2-YELLOW\n3-RED\n4-GREEN");
        String index2;
        index2 = stdin.nextLine();
        if (index2 == null) {
            index2 = "xxxx";
        }


        clientInput.sendString("playCharacterCard", index, index2);
    }

    private void playAssistantCard() {
        System.out.println("Select assistant card to play");
        clientInput.sendString("assistantCardDeck", "");
        clientInput.sendString("playAssistantCard", stdin.nextLine());

    }

    private void moveStudentToIsland() {
        System.out.println("Select student from hall");
        String indexStudent = stdin.nextLine();
        while (Integer.parseInt(indexStudent) > 7 || Integer.parseInt(indexStudent) < 1) {
            System.out.println("Input a valid index");
            indexStudent = stdin.nextLine();
        }

        System.out.println("Select destination island");
        String indexIsland = stdin.nextLine();
        while (Integer.parseInt(indexIsland) > 12 || Integer.parseInt(indexIsland) < 1) {
            System.out.println("Input a valid index");
            indexIsland = stdin.nextLine();
        }

        clientInput.sendString("moveStudentToIsland", indexStudent, indexIsland);
    }

    private void moveStudentToClassroom() {
        System.out.println("Select student from hall");
        String indexStudent = stdin.nextLine();
        while (Integer.parseInt(indexStudent) > 7 || Integer.parseInt(indexStudent) < 1) {
            System.out.println("Input a valid index");
            indexStudent = stdin.nextLine();
        }

        clientInput.sendString("moveStudentToClassroom", indexStudent);
    }

    private void moveMN() {

        System.out.println("Select destination island");
        String indexIsland = stdin.nextLine();
        while (Integer.parseInt(indexIsland) > 12 || Integer.parseInt(indexIsland) < 1) {
            System.out.println("Input a valid index");
            indexIsland = stdin.nextLine();
        }

        clientInput.sendString("moveMN", indexIsland);
    }

    private void chooseCC() {
        clientInput.sendString("sendCloudCards", "");
        System.out.println("Select Cloud Card");
        String indexCC = stdin.nextLine();
        clientInput.sendString("chooseCC", indexCC);
    }
}
