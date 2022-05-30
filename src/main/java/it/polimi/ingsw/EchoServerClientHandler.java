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
import java.util.HashMap;
import java.util.Objects;


public class EchoServerClientHandler extends Thread {

    private final Socket socket;
    private Game game;
    private final Gson gson = new Gson();

    private final ArrayList<Game> gameArrayList;
    private Player player;
    private BufferedReader in;

    private PrintWriter out;
    private final HashMap<String, Commd> commandMap = new HashMap<>();


    interface Commd {
        void runCommand(Command command) throws IOException;
    }

    public EchoServerClientHandler(Socket socket, ArrayList<Game> gameArrayList) throws IOException {
        this.socket = socket;
        this.gameArrayList = gameArrayList;


    }

    public void run() {
        commandMap.put("newGame", this::newGame);
        commandMap.put("avlGames", this::avlGames);
        commandMap.put("joinGame", this::joinGame);
        commandMap.put("login", this::login);
        commandMap.put("chooseDeck", this::chooseDeck);
        commandMap.put("sendAssistantDecks", this::sendAssistantDecks);
        commandMap.put("player", this::sendPlayerInfo);
        commandMap.put("allPlayers", this::sendAllPlayers);
        commandMap.put("islands", this::sendIslands);
        commandMap.put("dashboard", this::sendDashboard);
        commandMap.put("sendCharacterCardDeck", this::sendCharacterCardDeck);
        commandMap.put("sendAssistantCardDeck", this::sendAssistantCardDeck);
        commandMap.put("sendCloudCards", this::sendCloudCards);
        commandMap.put("playCharacterCard", this::playCharacterCard);
        commandMap.put("playAssistantCard", this::playAssistantCard);
        commandMap.put("moveStudentToIsland", this::moveStudentToIsland);
        commandMap.put("moveStudentToClassroom", this::moveStudentToClassroom);
        commandMap.put("moveMN", this::moveMotherNature);
        commandMap.put("chooseCC", this::chooseCC);
        commandMap.put("hall", this::sendHall);


        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);


            while (!socket.isClosed()) {
                String line = in.readLine();

                Command command = gson.fromJson(line, Command.class);

                if (command.cmd != null) {
                    try {
                        commandMap.get(command.cmd).runCommand(command);
                    } catch (Exception e) {
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


    public boolean checkTurn() {
        if (player == game.getActualPlayer()) {
            return true;
        } else {
            TextMessage message = new TextMessage("error", "Not your turn, wait for " + game.getActualPlayer().getName() + " to finish his turn");
            String json = gson.toJson(message, TextMessage.class);
            out.println(json);
            return false;
        }

    }

    public void newGame(Command command) {
        System.out.println("New game for " + command.value1);
        int num = Integer.parseInt(command.value1);
        game = new Game(num);
        gameArrayList.add(game);
        TextMessage text = new TextMessage("confirmation", "newGame", "Created game for " + num + " players");
        String json = gson.toJson(text, TextMessage.class);
        out.println(json);
    }

    public void avlGames(Command command) {

        ArrayList<GameStatus> list = new ArrayList<>();
        for (Game game1 : gameArrayList) {
            if (Objects.equals(game1.getGameStatus(), "Waiting for players")) {
                list.add(new GameStatus(game1.getCurrentNumberOfPlayers(), game1.getNumberOfPlayers(), game1.getPlist()));
            }
        }
        if (list.size() != 0) {
            TextMessage text = new TextMessage("avlGames", gson.toJson(list));
            String json = gson.toJson(text, TextMessage.class);
            out.println(json);
        } else {
            TextMessage text = new TextMessage("error","No games available");
            String json = gson.toJson(text, TextMessage.class);
            out.println(json);
        }
    }

    public void joinGame(Command command) {
        System.out.println("join game");
        try {
            game = gameArrayList.get(Integer.parseInt(command.value1));
            TextMessage textMessage = new TextMessage("confirmation", "joinGame", "You joined the game");
            String json = gson.toJson(textMessage, TextMessage.class);
            out.println(json);
        } catch (Exception e) {
            TextMessage textMessage = new TextMessage("error", "joinGame02", "Invalid index, please input a valid game index");
            String json = gson.toJson(textMessage, TextMessage.class);
            out.println(json);
        }
    }

    public void login(Command command) throws IOException {
        String json;
        String name = command.value1;
        switch (LoginManager.login(name, game)) {
            case 0:
                player = game.getPlist().getPlayerByName(name);
                player.setOut(out);
                player.setSocket(socket);
                player.sendToClient("confirmation", "login", "Welcome " + player.getName());
                return;
            case 2:
                out = new PrintWriter(socket.getOutputStream(), true);
                TextMessage text = new TextMessage("error", "login02", "Name already in use by another player, please select a unique username");
                json = gson.toJson(text, TextMessage.class);
                out.println(json);
                return;
            case 1:
                out = new PrintWriter(socket.getOutputStream(), true);
                text = new TextMessage("error", "login01", "Max number of players reached for this game");
                json = gson.toJson(text, TextMessage.class);
                out.println(json);
        }


    }


    public void chooseDeck(Command command) {
        String numDeck = command.value1;
        try {
            game.chooseDeck(Integer.parseInt(numDeck), player);
        } catch (Exception e) {
            player.sendToClient("error", "Error choosing deck");

        }


    }

    public void sendAssistantDecks(Command command) {
        player.sendToClient("assistantDecks", game.sendDeck());
    }

    public void sendPlayerInfo(Command command) {
        player.sendToClient("player", game.sendPlayer(player));
    }

    public void sendAllPlayers(Command command) {
        player.sendToClient("player", game.sendAllPlayers());
    }

    public void sendCharacterCardDeck(Command command) {
        player.sendToClient("characterCards", game.sendCharacterCardsDeck());
        player.sendToClient("notify", "Wallet: #" + player.getCoins() + " coins");
    }

    public void sendAssistantCardDeck(Command command) {
        for (AssistantCard assistantCard : player.getDeck().getCardsList()) {
            if (!game.checkLastPlayedAssistant(assistantCard.getOrder()))
                player.sendToClient("warning", assistantCard.order() + ") order: " + assistantCard.getOrder() + " and #" + assistantCard.getMoveOfMN() + " moves of MN available");
        }
    }

    public void sendIslands(Command command) {
        player.sendToClient("islands", game.sendIslands());
    }

    private void sendHall(Command command) {
        player.sendToClient("hall", game.sendHall(player));
    }

    public void sendDashboard(Command command) {
        player.sendToClient("dashboard", game.sendDashboard());
    }

    public void sendCloudCards(Command command) {
        player.sendToClient("cloudCard", game.sendCloudCards());
    }

    public void playCharacterCard(Command command) {
        int specialCardIndex = 0;
        SpecialCard specialCard;
        PawnColor studentColor = null;
        int islandIndex = 0;
        int value2 = Integer.parseInt(command.value2);
        specialCard = game.getCardsInGame().get(Integer.parseInt(command.value1) - 1);
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


    public void playAssistantCard(Command command) {
        if (checkTurn()) {
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

    }


    public void moveStudentToIsland(Command command) {
        if (checkTurn()) {
            int numPlayer = Integer.parseInt(command.value1);
            int indexIsland = Integer.parseInt(command.value2);
            if (player.moveStudentToIsland(numPlayer - 1, indexIsland - 1, game)) {
                player.sendToClient("hall", game.sendHall(player));
                player.sendToClient("islands", game.sendIslands());
            } else {
                errorSelectionNotify();

            }
        }
        //player.sendToClient("msg", "select student from hall to move to an island:");


    }

    public void moveStudentToClassroom(Command command) {
        if (checkTurn()) {
            player.sendToClient("msg", "select student from hall to move to a classroom:");
            int numPlayer = Integer.parseInt(command.value1);
            if (player.moveStudentToClassroom(numPlayer - 1, game)) {
                player.sendToClient("dashboard", game.sendDashboard());
            } else {
                errorSelectionNotify();
            }
        }

    }


    public void errorSelectionNotify() {
        player.sendToClient("msg", "select a valid student");
        player.sendToClient("hall", game.sendHall(player));
    }

    public void moveMotherNature(Command command) {
        if (checkTurn()) {
            int island = Integer.parseInt(command.value1);
            if (game.moveMN(player, island - 1)) {
                player.sendToClient("islands", game.sendIslands());

            } else {
                player.sendToClient("error", "error, you can move mother nature of " + player.getMovesOfMN() + "moves");

            }
        }


    }


    private void chooseCC(Command command) {
        player.sendToClient("cloudCard", game.sendCloudCards());
        boolean check = false;
        int cloudCardIndex = Integer.parseInt(command.value1);
        check = game.chooseCloudCard(cloudCardIndex - 1, player);
        if (cloudCardIndex > game.getCloudCards().size() || cloudCardIndex < 0)
            player.sendToClient("error", "Error, choose a valid cloud card");
        else if(check)
            player.sendToClient("error", "Error, choose a valid cloud card");
        if(!check) {
            player.sendToClient("dashboard", game.sendPlayerDashboard(player));
            game.setActualPlayer();
        }
    }

    }

