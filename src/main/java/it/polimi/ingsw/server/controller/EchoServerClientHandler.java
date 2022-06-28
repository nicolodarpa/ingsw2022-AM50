package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.Command;
import it.polimi.ingsw.comunication.GameStatus;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.CharacterCards.SpecialCardStrategy;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Handles connection to a single client.
 */

public class EchoServerClientHandler extends Thread {

    /**
     * Connection socket
     */
    private final Socket socket;

    /**
     * Current game
     */
    private Game game;
    private final Gson gson = new Gson();

    /**
     * List of available games
     */
    private final ArrayList<Game> gameArrayList;
    /**
     * Player associated to the client managed
     */
    private Player player;

    private PrintWriter out;

    /**
     * Hash map of the command available to the client
     */
    private final HashMap<String, Commd> commandMap = new HashMap<>();


    interface Commd {
        void runCommand(Command command) throws IOException;
    }

    /**
     * Sets the connection socket and the list of active games
     *
     * @param socket        connection socket
     * @param gameArrayList list of active games
     */
    public EchoServerClientHandler(Socket socket, ArrayList<Game> gameArrayList) {
        this.socket = socket;
        this.gameArrayList = gameArrayList;


    }

    /**
     * Run method of the thread.
     * Maps every command to the relative method, instantiates a new BufferedReader and PrinterWriter
     * exchange messages with the client. While the connection socket is open reads incoming messages.
     * Removes a player from the current game if the connection with the client stops
     */
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
        commandMap.put("singleDashboard", this::sendSingleDashboard);
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
        commandMap.put("quit", this::quit);
        commandMap.put("gameInfo", this::sendGameInfo);
        commandMap.put("singleIsland", this::sendSingleIsland);
        commandMap.put("cardsPlayed", this::sendCardsPlayed);
        commandMap.put("enemyDashboard", this::sendEnemyDashboard);


        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream(), true);


            while (!socket.isClosed()) {
                String line = in.readLine();

                Command command = gson.fromJson(line, Command.class);

                if (command.command != null) {
                    try {
                        commandMap.get(command.command).runCommand(command);
                    } catch (Exception e) {
                        TextMessage text = new TextMessage("msg", "Received " + command.command);
                        String json = gson.toJson(text, TextMessage.class);
                        out.println(json);
                    }

                } else System.out.println("null cmd");

            }
            in.close();
        } catch (Exception e) {
            System.out.println("Client disconnected");
            if (game != null) game.removePlayer(player);


        }
    }

    /**
     * Manages disconnection requests from the client.
     * Remove the player from the current game, closes the connection socket
     */
    private void quit(Command command) {
        TextMessage message = new TextMessage("quit", "Goodbye " + player.getName());
        String json = gson.toJson(message, TextMessage.class);
        out.println(json);
        game.removePlayer(player);
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Check if the player is the current player
     *
     * @return boolean value corresponding to the check result
     */
    public boolean checkTurn() {
        if (player == game.getCurrentPlayer()) {
            return true;
        } else {
            TextMessage message = new TextMessage("error", "Not your turn, wait for " + game.getCurrentPlayer().getName() + " to finish his turn");
            String json = gson.toJson(message, TextMessage.class);
            out.println(json);
            return false;
        }
    }

    /**
     * Starts a new Game and adds it to the games list
     *
     * @param command message payload received from the client, contains the desired number of player
     */
    public void newGame(Command command) {
        System.out.println("New game for " + command.value1);
        int num = Integer.parseInt(command.value1);
        TextMessage textMessage;
        try {
            game = new Game(num);
            gameArrayList.add(game);
             textMessage = new TextMessage("confirmation", "newGame", "Created game for " + num + " players");
        } catch (Exception e){
            textMessage  = new TextMessage("erro",  "Error creating new game");

        }

        String json = gson.toJson(textMessage, TextMessage.class);
        out.println(json);
    }

    /**
     * Sends to the client the list of available games
     * If a game has ended and is flagged as "ENDED" it gets removed from the games list
     *
     * @param command message payload received from the client
     */
    public void avlGames(Command command) {
        gameArrayList.removeIf(game -> Objects.equals(game.getGameStatus(), "ENDED"));
        ArrayList<GameStatus> list = new ArrayList<>();
        int gameId = 0;
        for (Game game1 : gameArrayList) {
            list.add(new GameStatus(gameId, game1.getCurrentNumberOfPlayers(), game1.getNumberOfPlayers(), game1.getPlist(), game1.getGameStatus()));
            gameId++;
        }
        if (list.size() != 0) {
            TextMessage text = new TextMessage("avlGames", gson.toJson(list));
            String json = gson.toJson(text, TextMessage.class);
            out.println(json);
        } else {
            TextMessage text = new TextMessage("error", "joinGame02", "No games available");
            String json = gson.toJson(text, TextMessage.class);
            out.println(json);
        }
    }

    /**
     * Select one game from the games list and sets it as the current game for this client handler.
     * Checks if the input from the client is correct and if then chosen game is still active
     *
     * @param command message payload received from the client, contains the index of the selected game as value1
     */
    public void joinGame(Command command) {
        System.out.println("join game");
        TextMessage textMessage;
        try {
            game = gameArrayList.get(Integer.parseInt(command.value1));
            if (Objects.equals(game.getGameStatus(), "ENDED")) {
                textMessage = new TextMessage("error", "joinGame02", "Error, the game has ended");

            } else textMessage = new TextMessage("confirmation", "joinGame", "You joined the game");

        } catch (Exception e) {
            textMessage = new TextMessage("error", "joinGame01", "Invalid index, please input a valid game index");
        }
        String json = gson.toJson(textMessage, TextMessage.class);
        out.println(json);

    }


    /**
     * Manages login to a game.
     * Send a message to the client containing the
     *
     * @param command message payload received from the client, contains the player's username as value1
     * @throws IOException If an exception occurred setting up the PrinterWriter
     */
    public void login(Command command) throws IOException {
        String json;
        String name = command.value1;
        switch (LoginManager.login(name, game)) {
            case 0:
                player = game.getPlist().getPlayerByName(name);
                player.setOut(out);
                player.setSocket(socket);
                if (player.getDeck() != null) {
                    player.sendToClient("confirmation", "logBack", "Welcome back" + player.getName());
                } else player.sendToClient("confirmation", "login", "Welcome " + player.getName());

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


    /**
     * Calls the Game class to assign the selected deck to a player
     */
    public void chooseDeck(Command command) {
        String numDeck = command.value1;
        try {
            game.chooseDeck(Integer.parseInt(numDeck), player);
        } catch (Exception e) {
            player.sendToClient("error", "Error choosing deck");

        }


    }

    /**
     * Sends to the client the list of assistants card decks
     */
    public void sendAssistantDecks(Command command) {
        player.sendToClient("assistantDecks", game.sendDeck());
    }

    /**
     * Sends to the client the info about the player
     */
    public void sendPlayerInfo(Command command) {
        player.sendToClient("player", game.sendPlayer(player));
    }

    /**
     * Sends to the client the info of every player
     */
    public void sendAllPlayers(Command command) {
        player.sendToClient("player", game.sendAllPlayers());
    }

    /**
     * Sends to the client the info about the current game
     */
    public void sendGameInfo(Command command) {
        player.sendToClient("gameInfo", game.sendGameInfo());
    }

    /**
     * Sends to the client the deck of character card
     */
    public void sendCharacterCardDeck(Command command) {
        player.sendToClient("characterCards", game.sendCharacterCardsDeck());
    }

    /**
     * Sends to client the list of available assistant cards in his deck
     */
    public void sendAssistantCardDeck(Command command) {
        for (AssistantCard assistantCard : player.getDeck().getCardsList()) {
            if (!game.checkLastPlayedAssistant(assistantCard.getOrder()))
                player.sendToClient("warning", assistantCard.order() + ") order: " + assistantCard.getOrder() + " and #" + assistantCard.getMoveOfMN() + " moves of MN available");
        }
    }

    /**
     * Sends to the client a message with the state of the islands
     */
    public void sendIslands(Command command) {
        player.sendToClient("islands", game.sendIslands());
    }

    /**
     * Sends to the client a message with the state of students hall of the player
     */
    private void sendHall(Command command) {
        player.sendToClient("hall", game.sendHall(player));
    }

    /**
     * Sends to the client a message with the state of the dashboard of every player
     */
    public void sendDashboard(Command command) {
        player.sendToClient("dashboard", game.sendDashboard());
    }

    /**
     * Sends to the client a message with the state of the dashboard of the player
     */
    public void sendSingleDashboard(Command command) {
        player.sendToClient("dashboard", game.sendPlayerDashboard(player));
    }

    /**
     * Sends to the client a message with the state of the dashboard of the enemies
     */
    public void sendEnemyDashboard(Command command) {
        player.sendToClient("enemyDashboard", game.sendEnemyDashboard(player));
    }

    /**
     * Sends to the client a message with the state of the cloud cards
     */
    public void sendCloudCards(Command command) {
        player.sendToClient("cloudCard", game.sendCloudCards());
    }


    /**
     * Sends to the client a message with the state of the islands
     */
    public void sendCardsPlayed(Command command) {
        player.sendToClient("cardsPlayed", game.sendPlayer(player));
    }

    /**
     * Play a character card
     * @param command message payload received from the client, contains the card's index as value1
     *                and an optional argument as value2 for special cards' effects
     */
    public void playCharacterCard(Command command) {
        if (!checkTurn()) {
            return;
        }
        SpecialCardStrategy specialCard;
        PawnColor studentColor = null;
        int islandIndex = 0;
        int value2;
        specialCard = game.getCardsInGame().get(Integer.parseInt(command.value1) - 1);
        if (specialCard.getCost() > player.getCoins()) {
            player.sendToClient("error", "You don't have enough coins to play this card");
            return;
        }
        player.sendToClient("notify", specialCard.getEffectOfTheCard());
        if (Objects.equals(specialCard.getName(), "princess") || Objects.equals(specialCard.getName(), "ambassador") || Objects.equals(specialCard.getName(), "warrior")) {
            try {
                value2 = Integer.parseInt(command.value2);
            } catch (Exception e) {
                player.sendToClient("error", "Error selecting island");
                return;
            }
            if (value2 < 1 || value2 > game.getIslands().size()) {
                player.sendToClient("error", "Error selecting island");
                return;
            }
            islandIndex = value2;

        } else if (Objects.equals(specialCard.getName(), "thief") || Objects.equals(specialCard.getName(), "wizard")) {
            try {
                value2 = Integer.parseInt(command.value2);
            } catch (Exception e) {
                player.sendToClient("error", "Error selecting color");
                return;
            }
            if (value2 > 4 || value2 < 0) {
                player.sendToClient("error", "Error selecting color");
                return;
            } else studentColor = PawnColor.values()[value2];


        }
        game.playCharacterCard(specialCard, islandIndex - 1, studentColor);
        game.notifyAllClients("msg", player.getName() + " has played the card " + specialCard.getName());

    }

    /**
     * Sends to the client information about one island selected by the user
     * @param command message payload received from the client, contains the index of the island
     */

    public void sendSingleIsland(Command command) {
        int indexIsland = Integer.parseInt(command.value1);
        try {
            player.sendToClient("singleIsland", game.sendSingleIsland(game.getIslands().get(indexIsland - 1)));
        } catch (Exception e) {
            player.sendToClient("warning", "Invalid index");
        }

    }

    /** Calls the Game method to move a student to play an assistant card
     * Checks if the game is currently in the action phase and if it's the player turn
     * Sends and error message to the client in case failure
     * @param command message payload received from the client, contains the index of the chosen student to move from the hall
     */
    public void playAssistantCard(Command command) {
        final String invalidCardError = "Input a number between 1 and 10";

        if (game.getPhase() == 1) {
            player.sendToClient("error", "You can't play an assistant card in Action phase");
            return;
        }
        if (checkTurn()) {
            int numCard;
            try {
                numCard = Integer.parseInt(command.value1);

                if (numCard > 0 && numCard < 11) {
                    game.playAssistantCard(player, numCard);
                } else {
                    player.sendToClient("error", invalidCardError);
                }
            } catch (IndexOutOfBoundsException e) {
                player.sendToClient("error", invalidCardError);
            }
        }
    }


    /** Calls the Game method to move a student to an island
     * Checks if the game is currently in the action phase and if it's the player turn
     * Sends and error message to the client in case failure
     * @param command message payload received from the client, contains the index of the chosen student to move from the hall
     */
    public void moveStudentToIsland(Command command) {
        if (game.getPhase() == 0) {
            player.sendToClient("error", " Before move students to island, you have to play an assistant card! ");
            return;
        }
        if (checkTurn()) {
            int numPlayer = Integer.parseInt(command.value1);
            int indexIsland = Integer.parseInt(command.value2) - 1;
            if (indexIsland < game.getIslands().size()) {
                if (player.moveStudentToIsland(numPlayer - 1, indexIsland, game)) {
                    game.notifyAllClients("hall", game.sendHall(player));
                    game.notifyAllClients("islands", game.sendIslands());
                    if(player.getMovesOfStudents() == 0)
                        player.sendToClient("notify", "You have finished your moves, now move Mother Nature on an island!");
                } else {
                    errorSelectionNotify();
                }
            }
        }

    }

    /** Calls the Game method to move a student to the classrooms
     * Checks if the game is currently in the action phase and if it's the player turn
     * Sends and error message to the client in case failure
     * @param command message payload received from the client, contains the index of the chosen student to move from the hall
     */
    public void moveStudentToClassroom(Command command) {
        if (game.getPhase() == 0) {
            player.sendToClient("error", " Before move students to classroom, you have to play an assistant card! ");
            return;
        }

        if (checkTurn()) {
            int numPlayer = Integer.parseInt(command.value1);
            if (player.moveStudentToClassroom(numPlayer - 1, game)) {
                sendSingleDashboard(command);
                if(player.getMovesOfStudents() == 0)
                    player.sendToClient("notify", "You have finished your moves, now move Mother Nature on an island!");
            } else {
                errorSelectionNotify();
            }
        }
    }


    /**
     * Sends an error message to the client regarding student selection from the hall
     */
    public void errorSelectionNotify() {
        player.sendToClient("error", "select a valid student");
    }



    /** Call the Game method to move mother nature
     * Check if the player can move mother nature at that moment and if the input from the user is acceptable
     * before calling the game method
     * Sends and error message to the client in case failure
     * @param command message payload received from the client, contains the index of the chosen cloud card
     */
    public void moveMotherNature(Command command) {
        if (game.getPhase() == 0 || player.getMovesOfStudents() > 0) {
            player.sendToClient("error", " You can't move mother nature now! ");
            return;
        }
        if (checkTurn() && player.getMovesOfStudents() == 0) {
            int island = Integer.parseInt(command.value1);
            if (game.moveMN(player, island - 1)) {
                game.notifyAllClients("islands", game.sendIslands());
                player.sendToClient("notify", "Perfect, now choose a cloud card!");

            } else {
                player.sendToClient("error", "error, you can move mother nature of " + player.getMovesOfMN() + " moves");

            }
        }
    }


    /** Call the Game method to choose a cloud card
     * Check if the player can choose a cloud card at that moment and if the input from the user is acceptable
     * before calling the game method
     * @param command message payload received from the client, contains the index of the chosen cloud card
     */
    private void chooseCC(Command command) {
        final String invalidCloudCardError = " Error, choose a valid cloud card ";
        if (game.getPhase() == 0 || player.getMovesOfStudents() > 0) {
            player.sendToClient("error", "You can't choose a cloud card now");
        } else if (checkTurn() && game.getPhase() == 1) {
            int cloudCardIndex = Integer.parseInt(command.value1);
            if (cloudCardIndex > game.getCloudCards().size() || cloudCardIndex < 0) {
                player.sendToClient("error", invalidCloudCardError);
                return;
            }
            game.chooseCloudCard(cloudCardIndex, player);

        }
    }

}

