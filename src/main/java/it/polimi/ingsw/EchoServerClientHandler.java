package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;


public class EchoServerClientHandler extends Thread {

    private final Socket socket;
    private final Game game;
    private Player player;
    private BufferedReader in;

    public EchoServerClientHandler(Socket socket, Game game) throws IOException {
        this.socket = socket;
        this.game = game;

    }

    public void run() {

        try {

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String name = in.readLine();

            boolean check = true;
            while (check) {
                switch (LoginManager.login(name, game)) {
                    case 0 -> {
                        game.getPlist().getPlayerByName(name).setSocket(socket);
                        player = game.getPlist().getPlayerByName(name);
                        if (game.getCurrentNumberOfPlayers() == 1) {
                            player.sendToClient("msg", "Welcome " + name + " Choose number of players, 2 or 3 allowed:");
                            String numPlayers;
                            numPlayers = in.readLine();
                            while (!Objects.equals(numPlayers, "2") && !Objects.equals(numPlayers, "3")) {
                                player.sendToClient("msg", "Please enter a valid number: 2 or 3 allowed");
                                numPlayers = in.readLine();
                            }
                            game.setNumberOfPlayers(Integer.parseInt(numPlayers));
                        } else {
                            player.sendToClient("msg", "Welcome " + name);
                        }
                        check = false;
                    }
                    case 2 -> {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        TextMessage text = new TextMessage("Name already in use by another player, please select a unique username");
                        Gson gson = new Gson();
                        String json = gson.toJson(text, TextMessage.class);
                        out.println(json);
                        name = in.readLine();
                    }
                    case 1 -> {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        TextMessage text = new TextMessage("Max number of players reached");
                        Gson gson = new Gson();
                        String json = gson.toJson(text, TextMessage.class);
                        out.println(json);
                        check = false;
                        in.close();
                        socket.close();
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + LoginManager.login(name, game));
                }
            }


            game.checkLobby();
            while (!game.waitLobby()) {
                player.sendToClient("msg", "Waiting for other " + (game.getNumberOfPlayers() - game.getCurrentNumberOfPlayers()) + " players");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            player.sendToClient("msg", "Please chose your deck: \n1-blu \n2-purple \n3-green \n4-pink");
            String numDeck;
            numDeck = in.readLine();
            check = true;
            while (check) {
                while (!Objects.equals(numDeck, "1") && !Objects.equals(numDeck, "2") && !Objects.equals(numDeck, "3") && !Objects.equals(numDeck, "4")) {
                    player.sendToClient("msg", "Please chose a valid deck:");
                    numDeck = in.readLine();
                }
                if (game.chooseDeck(Integer.parseInt(numDeck), player) == 1) {
                    check = false;
                    player.sendToClient("msg", "Deck " + numDeck + " chosen");
                } else {
                    player.sendToClient("msg", "Deck already chosen by another player");
                    numDeck = in.readLine();
                }
            }


            while (true) {
                String line = in.readLine();
                if (line.equals("quit")) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    TextMessage text = new TextMessage("quit", "Goodbye " + name);
                    Gson gson = new Gson();
                    String json = gson.toJson(text, TextMessage.class);
                    out.println(json);
                    break;
                } else if (line.equals("dashboard")) {
                    player.sendToClient("dashboard", game.sendDashboard());
                } else if (line.equals("islands")) {
                    player.sendToClient("islands", game.sendIslands());
                } else if (player != game.getActualPlayer()) {
                    player.sendToClient("msg", "not your turn");
                } else if (line.equals("play assistant card") && game.getPhase() == 0) {
                    playAssistantCard();
                } else if (game.getPhase() == 1) {
                    if (player.getMovesOfStudents() > 0) {
                        if (line.equals("move student to island")) {
                            moveStudentToIsland();
                        } else if (line.equals("move student to classroom")) {
                            moveStudentToClassroom();
                        } else {
                            player.sendToClient("msg", "move all you students before moving MN");
                        }

                    } else if (player.getMovesOfStudents() == 0 && line.equals("move MN")) {
                        moveMotherNature();
                    } else if (line.equals("choose cc")) {
                        chooseCC();

                    }

                } else {
                    player.sendToClient("msg", "ok");
                    player.sendToClient("msg", "Received: " + line);


                }
            }


            game.removePlayer(name);
            in.close();
            socket.close();


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }


    private void playAssistantCard() {
        int numCard;
        boolean result = true;
        do {
            player.sendToClient("msg", "chose assistant card to play");
            try {
                numCard = Integer.parseInt((in.readLine()));

                if (numCard > 0 && numCard < 11) {
                    result = game.playAssistantCard(player, (numCard));
                }
            } catch (Exception e) {
                player.sendToClient("warning", "Input a number between 1 and 10");
            }


        } while (result);
        game.setActualPlayer();
    }


    private void moveStudentToIsland() {
        player.sendToClient("msg", "select student from hall to move to an island:");
        int numPlayer = indexStudentInput();
        player.sendToClient("msg", "select island");
        int indexIsland = indexIslandInput();
        if (player.moveStudentToIsland(numPlayer - 1, indexIsland - 1, game)) {
            player.sendToClient("hall", game.sendHall(player));
            player.sendToClient("islands", game.sendIslands());
        } else {
            errorSelectionNotify();
            moveStudentToIsland();

        }

    }

    private void moveStudentToClassroom() {
        player.sendToClient("msg", "select student from hall to move to a classroom:");
        int numPlayer = indexStudentInput();
        if (player.moveStudentToClassroom(numPlayer - 1, game)) {
            player.sendToClient("dashboard", game.sendDashboard());
        } else {
            errorSelectionNotify();
            moveStudentToClassroom();
        }

    }

    private int indexStudentInput() {
        int numPlayer = 0;
        try {
            String line = in.readLine();
            numPlayer = Integer.parseInt(line);
            if (numPlayer < 1 || numPlayer > 7) {
                player.sendToClient("msg", "Error input, please insert a value between 1 and 7");
                indexStudentInput();
            }
        } catch (Exception e) {
            player.sendToClient("msg", "Error input, please insert a value between 1 and 7");
            indexStudentInput();
        }
        return numPlayer;
    }

    private int indexIslandInput() {
        try {
            String line = in.readLine();
            int numIsland = Integer.parseInt(line);
            if (numIsland < 1 || numIsland > game.getIslands().size()) {
                player.sendToClient("msg", "Error input, plese select a value between 1 and " + game.getIslands().size());
                indexIslandInput();
            }
            return numIsland;
        } catch (Exception e) {
            player.sendToClient("msg", "Error input, plese select a value between 1 and " + game.getIslands().size());
            indexIslandInput();
        }
        return 0;

    }


    private void errorSelectionNotify() {
        player.sendToClient("msg", "select a valid student");
        player.sendToClient("hall", game.sendHall(player));

    }

    private void moveMotherNature() throws IOException {
        player.sendToClient("msg", "select destination island");
        int island = Integer.parseInt(in.readLine());
        if (game.moveMN(player, island - 1)) {
            player.sendToClient("islands", game.sendIslands());

        } else {
            player.sendToClient("error", "error, you can move mother nature of " + player.getMovesOfMN() + "moves");
            moveMotherNature();
        }

    }


    private void chooseCC() throws IOException {
        player.sendToClient("cloudCard", game.sendCloudCards());
        player.sendToClient("msg", "select cloud card");
        int cloudCardIndex = Integer.parseInt(in.readLine());
        game.chooseCloudCard(cloudCardIndex - 1, player);
        player.sendToClient("dashboard", game.sendPlayerDashboard(player));
        game.setActualPlayer();
    }

}
