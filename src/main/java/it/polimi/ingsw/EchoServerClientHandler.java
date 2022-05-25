package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.Command;
import it.polimi.ingsw.comunication.GameStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.CharacterCards.SpecialCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;


public class EchoServerClientHandler extends Thread {

    private final Socket socket;
    private Game game;
    private final Gson gson = new Gson();

    private final ArrayList<Game> gameArrayList;
    private Player player;
    private BufferedReader in;

    private PrintWriter out;


    public EchoServerClientHandler(Socket socket, ArrayList<Game> gameArrayList) throws IOException {
        this.socket = socket;
        this.gameArrayList = gameArrayList;
    }

    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);


            while (!socket.isClosed()) {
                String line = in.readLine();

                Command command = gson.fromJson(line, Command.class);

                if (command.cmd != null) {
                    if (command.cmd.equals("quit")) {
                        player.sendToClient("quit", "Goodbye " + player.getName());
                        game.removePlayer(player);
                        socket.close();
                    } else if (command.cmd.equals("avlGames")) {
                        if (gameArrayList.size() > 0) {
                            ArrayList<GameStatus> list = new ArrayList<>();
                            for (Game game1 : gameArrayList) {
                                if (Objects.equals(game1.getGameStatus(), "Waiting for players")) {
                                    list.add(new GameStatus(game1.getCurrentNumberOfPlayers(), game1.getNumberOfPlayers(), game1.getPlist()));
                                }
                            }
                            TextMessage text = new TextMessage("avlGames", gson.toJson(list));
                            String json = gson.toJson(text, TextMessage.class);
                            out.println(json);
                        } else {
                            TextMessage text = new TextMessage("error", "No games available");
                            String json = gson.toJson(text, TextMessage.class);
                            out.println(json);
                        }

                    } else if (command.cmd.equals("newGame")) {
                        System.out.println("New game for " + command.value1);
                        int num = Integer.parseInt(command.value1);
                        game = new Game(num);
                        gameArrayList.add(game);
                    } else if (command.cmd.equals("joinGame")) {
                        System.out.println("join game");
                        try {
                            game = gameArrayList.get(Integer.parseInt(command.value1));
                            TextMessage textMessage = new TextMessage("confirm", "You joined the game ");
                            String json = gson.toJson(textMessage, TextMessage.class);
                            out.println(json);
                        } catch (Exception e) {
                            TextMessage textMessage = new TextMessage("errInvIndex", "Invalid index, please input a valid game index");
                            String json = gson.toJson(textMessage, TextMessage.class);
                            out.println(json);
                        }

                    } else if (command.cmd.equals("login")) {
                        Login(command.value1);
                    } else if (command.cmd.equals("sendAssistantDecks")){
                        sendAssistantDecks();
                    } else if (command.cmd.equals("player")){
                        sendPlayers();
                    } else if (command.cmd.equals("chooseDeck")) {
                        chooseDeck(command);
                    } else if (command.cmd.equals("dashboard")) {
                        player.sendToClient("dashboard", game.sendDashboard());
                    } else if (command.cmd.equals("islands")) {
                        player.sendToClient("islands", game.sendIslands());
                    } else if (command.cmd.equals("sendCloudCards")) {
                        sendCloudCards();
                    } else if (command.cmd.equals("assistantCardDeck")) {
                        sendAssistantCardDeck();
                    } else if (command.cmd.equals("characterCards")) {
                        sendCharacterCardDeck();
                    } else if (command.cmd.equals("playCharacterCard")) {
                        playCharacterCard(command);
                    } else if (game != null) {
                        if (player != game.getActualPlayer()) {
                            player.sendToClient("msg", "not your turn");
                        } else if (command.cmd.equals("playAssistantCard") && game.getPhase() == 0) {
                            playAssistantCard(command);
                        } else if (command.cmd.equals("actions") && game.getPhase() == 0) {
                            player.sendToClient("msg", "play an assistant card of your deck");
                        } else if (game.getPhase() == 1) {
                            if (player.getMovesOfStudents() > 0) {
                                switch (command.cmd) {
                                    case "moveStudentToIsland" -> moveStudentToIsland(command);
                                    case "moveStudentToClassroom" -> moveStudentToClassroom(command);
                                    case "actions" ->
                                            player.sendToClient("msg", "You can move student to classroom or you can move student to island");

                                    default -> player.sendToClient("msg", "move all you students before moving MN");
                                }
                            } else if (player.getMovesOfStudents() == 0 && command.cmd.equals("moveMN")) {
                                moveMotherNature(command);
                            } else if (command.cmd.equals("chooseCC")) {
                                chooseCC(command);
                            }
                        }
                    } else {
                        TextMessage text = new TextMessage("msg", "Received " + command.cmd);
                        String json = gson.toJson(text, TextMessage.class);
                        out.println(json);

                    }
                } else System.out.println("null cmd");

            }

            //game.removePlayer(name);
            in.close();
            //socket.close();


        } catch (Exception e) {
            System.out.println(e);
            //game.removePlayer(player);

        }
    }

    private void Login(String name) throws IOException {
        String json;
        switch (LoginManager.login(name, game)) {
            case 0:
                player = game.getPlist().getPlayerByName(name);
                player.setOut(out);
                player.setSocket(socket);
                player.sendToClient("msg", "Welcome " + player.getName());
                return;
            case 2:
                out = new PrintWriter(socket.getOutputStream(), true);
                TextMessage text = new TextMessage("error02", "Name already in use by another player, please select a unique username");
                json = gson.toJson(text, TextMessage.class);
                out.println(json);
                return;
            case 1:
                out = new PrintWriter(socket.getOutputStream(), true);
                text = new TextMessage("error01", "Max number of players reached for this game");
                json = gson.toJson(text, TextMessage.class);
                out.println(json);
        }


    }


    private void chooseDeck(Command command) {
        String numDeck = command.value1;
        try {
            if (game.chooseDeck(Integer.parseInt(numDeck), player) == 1) {
                player.sendToClient("msg", "Deck " + numDeck + " chosen");
            } else {
                player.sendToClient("error", "Deck already chosen by another player");
            }
        } catch (Exception e) {
            player.sendToClient("error", "Error choosing deck");

        }


    }

    private void sendAssistantDecks(){
        player.sendToClient("msg", game.sendDeck());
    }

    private void sendPlayers(){
        player.sendToClient("player", game.sendPlayers());
    }

    private void sendCharacterCardDeck() {
        player.sendToClient("characterCards", game.sendCharacterCardsDeck());
        player.sendToClient("notify", "Wallet: #" + player.getCoins() + " coins");
    }

    private void sendAssistantCardDeck() {
        for (AssistantCard assistantCard : player.getDeck().getCardsList()){
            if (!game.checkLastPlayedAssistant(assistantCard.getOrder()))
                player.sendToClient("warning", assistantCard.order() + ") order: " + assistantCard.getOrder() + " and #" + assistantCard.getMoveOfMN() + " moves of MN available");
        }
    }

    private void sendCloudCards(){
        player.sendToClient("cloudCard", game.sendCloudCards());
    }

    private void playCharacterCard(Command command) {
        int specialCardIndex = 0;
        SpecialCard specialCard;
        PawnColor studentColor = null;
        int islandIndex = 0;
        int value2 = Integer.parseInt(command.value2);
        specialCard = game.getCardsInGame().get(Integer.parseInt(command.value1)-1);
        if (specialCard.getCost() > player.getCoins()) {
            player.sendToClient("error", "You don't have enough coins to play this card");
            return;
        }
        player.sendToClient("notify", specialCard.getEffectOfTheCard());
        if (Objects.equals(specialCard.getName(), "princess") || Objects.equals(specialCard.getName(), "ambassador") || Objects.equals(specialCard.getName(), "warrior")) {
            //player.sendToClient("msg", "Select the island");
            if (value2 < 1 || value2 > game.getIslands().size()) {
                player.sendToClient("error", "Error selecting island");
                return;
            }
            islandIndex = value2;

        } else if (Objects.equals(specialCard.getName(), "thief") || Objects.equals(specialCard.getName(), "wizard")) {
            //player.sendToClient("msg", "Select the color\n0-CYAN\n1-MAGENTA\n2-YELLOW\n3-RED\n4-GREEN");
            if (value2 > 4 || value2 < 0) {
                player.sendToClient("error", "Error selecting color");
                return;
            } else studentColor = PawnColor.values()[value2];


        }
        game.playCharacterCard(specialCardIndex - 1, islandIndex - 1, studentColor);

    }


    private void playAssistantCard(Command command) {
        int numCard;
        try {
            numCard = Integer.parseInt(command.value1);

            if (numCard > 0 && numCard < 11) {
                game.playAssistantCard(player, numCard);
            } else {
                player.sendToClient("error", "Input a number between 1 and 10");
            }
        } catch (Exception e) {
            player.sendToClient("error", "Input a number between 1 and 10");
        }
    }


    private void moveStudentToIsland(Command command) {
        //player.sendToClient("msg", "select student from hall to move to an island:");
        int numPlayer = Integer.parseInt(command.value1);
        int indexIsland = Integer.parseInt(command.value2);
        if (player.moveStudentToIsland(numPlayer - 1, indexIsland - 1, game)) {
            player.sendToClient("hall", game.sendHall(player));
            player.sendToClient("islands", game.sendIslands());
        } else {
            errorSelectionNotify();

        }

    }

    private void moveStudentToClassroom(Command command) {
        player.sendToClient("msg", "select student from hall to move to a classroom:");
        int numPlayer = Integer.parseInt(command.value1);
        if (player.moveStudentToClassroom(numPlayer - 1, game)) {
            player.sendToClient("dashboard", game.sendDashboard());
        } else {
            errorSelectionNotify();
        }

    }






    private void errorSelectionNotify() {
        player.sendToClient("msg", "select a valid student");
        player.sendToClient("hall", game.sendHall(player));
    }

    private void moveMotherNature(Command command) {
        int island = Integer.parseInt(command.value1);
        if (game.moveMN(player, island - 1)) {
            player.sendToClient("islands", game.sendIslands());

        } else {
            player.sendToClient("error", "error, you can move mother nature of " + player.getMovesOfMN() + "moves");

        }

    }


    private void chooseCC(Command command) {
        int cloudCardIndex = Integer.parseInt(command.value1);
        game.chooseCloudCard(cloudCardIndex - 1, player);
        player.sendToClient("dashboard", game.sendPlayerDashboard(player));
    }

}
