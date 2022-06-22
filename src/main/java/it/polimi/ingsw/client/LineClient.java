package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ClientOut;


import java.io.*;
import java.net.Socket;

import java.util.Objects;
import java.util.Scanner;


/**
 * Class for the Client CLI.
 */

public class LineClient {
    private final static String ANSI_PRIMARY = "\u001B[36m";
    private final static String ANSI_SECONDARY = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private static ClientInput clientInput;

    private static Scanner stdin;
    private final String ip;
    private final int port;
    private static Socket socket;


    /**
     * Constructor method with ip and port
     *
     * @param ip   ip address to connect
     * @param port port number to connect
     */

    public LineClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }


    /**
     * Creates a new a connection socket to the server,
     * creates a new BufferedReader and a PrinterWriter and sets them to the ClientOut and ClientInput.
     * Start the game setup
     */
    public void startClient() {
        System.out.println(ANSI_PRIMARY + "====Eriantys CLI Client====" + ANSI_RESET);
        try {
            socket = new Socket(ip, port);
            System.out.println(ANSI_SECONDARY + "Connection established" + ANSI_RESET);
            BufferedReader socketIn = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            stdin = new Scanner(System.in);
            ClientOut clientOut = new ClientOut(socketIn, socket);
            clientInput = ClientInput.getInstance();
            clientInput.setSocketOut(socketOut);
            clientOut.start();
            initSetup();

        } catch (Exception e) {
            System.out.println("Connection refused, no server online\n" +
                    "Start a server before launching the client");
        }

    }

    /**
     * Clears the console and prints player information
     */
    public static void clearConsole() {
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
        clientInput.sendString("player", "");
    }


    /**
     * Reads incoming messages from the server white the connection isn't closed and executes the specified commands
     */
    public static void stdinScan() {
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
                case "player" -> clientInput.sendString("player", "");
                case "allPlayer" -> clientInput.sendString("allPlayer", "");
                case "quit" -> quit();
                case "gameInfo" -> clientInput.sendString("gameInfo", "");
                case "singleIsland" -> sendSingleIsland();
                case "cardsPlayed" -> sendCardsPlayed();
                case "enemyDashboard" -> clientInput.sendString("enemyDashboard", "");
                default -> printCommands();

            }

        }
    }


    /**
     * Prints all available commands on the console
     */
    private static void printCommands() {
        System.out.println("""
                Available commands:
                -play character card
                -play assistant card
                -move student to island
                -move student to classroom
                -move mn
                -choose cc
                -islands (shows islands)
                -dashboard (shows dashboards of both players)
                -cloud cards (shows cloud cards)
                -player (shows your info like your order and Move of MN available)
                -allPlayer (shows all player's info)
                -gameInfo(shows the actual player, the number of round and the actual phase)
                -quit (quit the game)
                -clear""");
    }


    /**
     * Asks to create a new game or join an existing one
     */
    public static void initSetup() {
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
    }

    /**
     * Asks the number of player of the game and sends a message to server to start a new game.
     * If the input value isn't 2 or 3 the default value 2 is chosen
     */
    public static void newGame() {
        System.out.println("Starting new Game\nHow many players?");
        String value = stdin.nextLine();
        if (!Objects.equals(value, "2") && !Objects.equals(value, "3")) {
            value = "2";
        }
        clientInput.sendString("newGame", value);
    }

    /**
     * Send a command to the server to join the selected game
     */
    public static void joinGame() {
        clientInput.sendString("avlGames", "");
        clientInput.sendString("joinGame", stdin.nextLine());
    }

    /**
     * Asks the player a username
     * Send a command to the server to log in with the selected username
     */
    public static void login() throws IOException {
        System.out.print("Login\nUsername: ");
        String username = stdin.nextLine();
        clientInput.sendString("login", username);
    }


    /**
     * Asks the player to select an assistant cards deck
     * Send a command to the server to select the chosen deck
     */
    public static void chooseDeck() {
        System.out.println("Select your deck:");
        clientInput.sendString("sendAssistantDecks", "");
        String index = stdin.nextLine();
        while (!Objects.equals(index, "4") && !Objects.equals(index, "1") && !Objects.equals(index, "2") && !Objects.equals(index, "3")) {
            System.out.println("Please input a valid index");
            index = stdin.nextLine();
        }

        clientInput.sendString("chooseDeck", index);
    }

    /**
     * Prints the list of available character cards.
     * Asks the player to select one character card to play and asks the optional values necessary to activate some card effects.
     * Sends a command to the server to play the selected card.
     */
    private static void playCharacterCard() {
        System.out.println("Select character card to play");
        clientInput.sendString("sendCharacterCardDeck", "");
        String specialCardIndex;
        while (true) {
            specialCardIndex = stdin.nextLine();
            if (Objects.equals(specialCardIndex, "1") || Objects.equals(specialCardIndex, "2") || Objects.equals(specialCardIndex, "3")) {
                break;
            } else {
                System.out.println("Please input a valid index: 1-3");
            }
        }
        System.out.println("Select island or color if necessary");
        System.out.println("Select the color\n0-CYAN\n1-MAGENTA\n2-YELLOW\n3-RED\n4-GREEN");
        String index2;
        index2 = stdin.nextLine();
        if (Objects.equals(index2, "")) {
            index2 = "0";
        }
        clientInput.sendString("playCharacterCard", specialCardIndex, index2);
    }


    /**
     * Prints the list of available assistant cards.
     * Asks the player to select one assistant card and sends a command to the server to play the selected card.
     */
    private static void playAssistantCard() {
        System.out.println("Select assistant card to play");
        clientInput.sendString("sendAssistantCardDeck", "");
        clientInput.sendString("playAssistantCard", stdin.nextLine());
    }

    private static void sendSingleIsland() {
        System.out.println("Select an Island: ");
        clientInput.sendString("singleIsland", stdin.nextLine());
    }


    private static void sendCardsPlayed() {
        clientInput.sendString("cardsPlayed", "");
    }

    /**
     * Print the students in the dashboard hall, asks the user to insert  the position of the selected student
     * Check if the selected number is acceptable
     *
     * @return number as a string that indicates the position in the hall of the selected student
     */
    private static String getStudentFromHall() {
        System.out.println("Select student from hall");
        clientInput.sendString("hall", "");
        String indexStudent = stdin.nextLine();
        while (Integer.parseInt(indexStudent) > 7 || Integer.parseInt(indexStudent) < 1) {
            System.out.println("Input a valid index");
            indexStudent = stdin.nextLine();

        }
        return indexStudent;
    }


    /**
     * Sends a command to the server to move a student from the hall to an island.
     * Gets a student from the hall, asks the user to input a number the chose the destination island and
     * sends a message to the server wit the command moveStudentToIsland
     */
    private static void moveStudentToIsland() {
        String indexStudent = getStudentFromHall();
        System.out.println("Select destination island");
        String indexIsland = stdin.nextLine();
        while (Integer.parseInt(indexIsland) > 12 || Integer.parseInt(indexIsland) < 1) {
            System.out.println("Input a valid index");
            indexIsland = stdin.nextLine();
        }
        clientInput.sendString("moveStudentToIsland", indexStudent, indexIsland);
    }


    /**
     * Asks the user to input a number the chose the destination island and
     * sends a command to the server to move mother nature to the selected island
     */
    private static void moveStudentToClassroom() {
        String indexStudent = getStudentFromHall();
        clientInput.sendString("moveStudentToClassroom", indexStudent);
    }


    /**
     * Gets a student from the hall
     * Sends a command to the server to move a student from the hall to the classroom.
     */
    private static void moveMN() {
        System.out.println("Select destination island");
        String indexIsland = stdin.nextLine();
        while (Integer.parseInt(indexIsland) > 12 || Integer.parseInt(indexIsland) < 1) {
            System.out.println("Input a valid index");
            indexIsland = stdin.nextLine();
        }
        clientInput.sendString("moveMN", indexIsland);
    }

    /**
     * Asks the server for the list of cloud cards,
     * asks the user to input a number the chose one cloud card and
     * sends a command to the server to select the cloud card
     */
    private static void chooseCC() {
        clientInput.sendString("sendCloudCards", "");
        System.out.println("Select Cloud Card");
        String indexCC = stdin.nextLine();
        clientInput.sendString("chooseCC", indexCC);
    }

    /**
     * Sends a command to the server to quit the current game and close the connection
     */
    private static void quit() {
        clientInput.sendString("quit", "");
    }
}
