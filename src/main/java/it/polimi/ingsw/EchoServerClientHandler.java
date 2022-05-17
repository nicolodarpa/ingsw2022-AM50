package it.polimi.ingsw;

import com.google.gson.Gson;
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

    private final ArrayList<Game> gameArrayList;
    private Player player;
    private BufferedReader in;

    public EchoServerClientHandler(Socket socket, ArrayList<Game> gameArrayList) throws IOException {
        this.socket = socket;
        this.gameArrayList = gameArrayList;

    }

    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            if (gameArrayList.size() == 0) {
                game = new Game();
                gameArrayList.add(game);
            } else {
                TextMessage textMessage = new TextMessage("1-Create new Game\n" +
                        "2-Join Game");
                Gson gson = new Gson();
                String json = gson.toJson(textMessage, TextMessage.class);
                out.println(json);
                String select = in.readLine();
                switch (select) {
                    case "1" -> {
                        game = new Game();
                        gameArrayList.add(game);
                    }
                    case "2" -> {
                        int i = 0;
                        for (Game game1 : gameArrayList) {
                            textMessage = new TextMessage(i + " game: " + game1.getGameStatus());
                            json = gson.toJson(textMessage, TextMessage.class);
                            out.println(json);
                            i++;
                        }
                        String gameSelection = in.readLine();
                        game = gameArrayList.get(Integer.parseInt(gameSelection));
                    }

                }
            }

            TextMessage textMessage = new TextMessage("Enter your name");
            Gson gson = new Gson();
            String json = gson.toJson(textMessage, TextMessage.class);
            out.println(json);
            String name = in.readLine();

            boolean check = true;
            while (check) {
                switch (LoginManager.login(name, game)) {
                    case 0 -> {
                        player = game.getPlist().getPlayerByName(name);
                        player.setOut(out);
                        player.setSocket(socket);
                        player.sendToClient("notify", "Game " + gameArrayList.size());
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
                        out = new PrintWriter(socket.getOutputStream(), true);
                        TextMessage text = new TextMessage("Name already in use by another player, please select a unique username");
                        gson = new Gson();
                        json = gson.toJson(text, TextMessage.class);
                        out.println(json);
                        name = in.readLine();
                    }
                    case 1 -> {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        TextMessage text = new TextMessage("Max number of players reached");
                        gson = new Gson();
                        json = gson.toJson(text, TextMessage.class);
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


            while (!socket.isClosed()) {
                String line = in.readLine();
                if (line.equals("quit")) {
                    player.sendToClient("quit", "Goodbye " + player.getName());
                    game.removePlayer(player);
                    socket.close();
                } else if (line.equals("dashboard")) {
                    player.sendToClient("dashboard", game.sendDashboard());
                } else if (line.equals("islands")) {
                    player.sendToClient("islands", game.sendIslands());
                } else if (line.equals("deck")) {
                    sendCharacterCardDeck();
                } else if (line.equals("play character card")) {
                    playCharacterCard();
                } else if (player != game.getActualPlayer()) {
                    player.sendToClient("msg", "not your turn");
                } else if (line.equals("play assistant card") && game.getPhase() == 0) {
                    playAssistantCard();
                } else if (line.equals("actions") && game.getPhase() == 0) {
                    player.sendToClient("msg", "play an assistant card of your deck");
                } else if (game.getPhase() == 1) {
                    if (player.getMovesOfStudents() > 0) {
                        switch (line) {
                            case "move student to island" -> moveStudentToIsland();
                            case "move student to classroom" -> moveStudentToClassroom();
                            case "actions" ->
                                    player.sendToClient("msg", "You can move student to classroom or you can move student to island");
                            default -> player.sendToClient("msg", "move all you students before moving MN");
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

            //game.removePlayer(name);
            in.close();
            //socket.close();


        } catch (Exception e) {
            System.out.println("Error with client ");
            //game.removePlayer(player);
            System.out.println(e.getMessage());
        }
    }

    private void sendCharacterCardDeck() {
        player.sendToClient("characterCards", game.sendCharacterCardsDeck());
    }

    private void playCharacterCard() {
        sendCharacterCardDeck();
        player.sendToClient("notify", "Wallet: #" + player.getWallet() + " coins");
        int specialCardIndex = 0;
        SpecialCard specialCard = null;
        PawnColor studentColor = null;
        int index = 1;
        boolean result = true;
        while (result) {
            do {
                player.sendToClient("warning", "Select character card");
                try {
                    String line = in.readLine();
                    if (Objects.equals(line, "exit")) {
                        player.sendToClient("warning", "Quit character card selection");
                        return;
                    } else {
                        specialCardIndex = Integer.parseInt(line);
                        specialCard = game.getCardsInGame().get(specialCardIndex - 1);
                    }
                } catch (Exception e) {
                    player.sendToClient("warning", "Input a number between 1 and 3");
                }
            } while (specialCard == null);
            player.sendToClient("notify", specialCard.getEffectOfTheCard());
            if (Objects.equals(specialCard.getName(), "princess") || Objects.equals(specialCard.getName(), "ambassador") || Objects.equals(specialCard.getName(), "warrior")) {
                index = indexIslandInput();
            } else if (Objects.equals(specialCard.getName(), "thief") || Objects.equals(specialCard.getName(), "wizard")) {
                studentColor = colorSelection();
            }
            result = game.playCharacterCard(specialCardIndex - 1, index - 1, studentColor);
        }
    }


    private void playAssistantCard() {
        int numCard;
        boolean result = true;
        do {
            player.sendToClient("msg", "chose assistant card to play:");
            for (AssistantCard assistantCard : player.getDeck().getCardsList())
                if (!game.checkLastPlayedAssistant(assistantCard.getOrder()))
                    player.sendToClient("warning",  " you can play card with order " + assistantCard.getOrder() + " and #" + assistantCard.getMoveOfMN() + " moves of MN available");
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
        player.sendToClient("hall", game.sendHall(player));
        player.sendToClient("msg", "select student from hall to move to an island:");
        int numPlayer = indexStudentInput();
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
        player.sendToClient("hall", game.sendHall(player));
        player.sendToClient("msg", "select student from hall to move to a classroom:");
        int numPlayer = indexStudentInput();
        if (player.moveStudentToClassroom(numPlayer - 1, game)) {
            player.sendToClient("dashboard", game.sendDashboard());
        } else {
            errorSelectionNotify();
            moveStudentToClassroom();
        }

    }

    private PawnColor colorSelection() {
        player.sendToClient("msg", "Select a color:\n1-CYAN\n2-MAGENTA\n3-YELLOW\n4-RED\n5-GREEN");
        while (true) {
            PawnColor studentColor;
            try {
                studentColor = PawnColor.values()[Integer.parseInt(in.readLine()) - 1];
                return studentColor;
            } catch (IOException e) {
                player.sendToClient("error", "Please select a valid color, input a number between 1-5");
            }
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
        player.sendToClient("msg", "Select an island");
        while (true) {
            try {
                String line = in.readLine();
                int numIsland = Integer.parseInt(line);
                if (numIsland < 1 || numIsland > game.getIslands().size()) {
                    player.sendToClient("msg", "Error input, please select a value between 1 and " + game.getIslands().size());
                    indexIslandInput();
                }
                return numIsland;
            } catch (Exception e) {
                player.sendToClient("msg", "Error input, please select a value between 1 and " + game.getIslands().size());

            }
        }
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
            player.sendToClient("error", "error, you can move mother nature of " + player.getMovesOfMN() + " moves");
            moveMotherNature();
        }

    }


    private void chooseCC() throws IOException {
        player.sendToClient("cloudCard", game.sendCloudCards());

        boolean check = true;
        while (check){
            player.sendToClient("msg", "select a valid cloud card");
            int cloudCardIndex = Integer.parseInt(in.readLine());
            check = game.chooseCloudCard(cloudCardIndex - 1, player);
        }
        player.sendToClient("dashboard", game.sendPlayerDashboard(player));
        game.setActualPlayer();
    }



}
