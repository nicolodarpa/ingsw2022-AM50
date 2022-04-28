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

    public EchoServerClientHandler(Socket socket, Game game) throws IOException {
        this.socket = socket;
        this.game = game;

    }

    public void run() {

        try {

            BufferedReader in = new BufferedReader(
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
                } else if (player != game.getActualPlayer()) {
                    player.sendToClient("msg", "not your turn");
                } else if (line.equals("play assistant card") && game.getPhase() == 0) {
                    String numCard;
                    boolean result;
                    do {
                        player.sendToClient("msg", "chose assistant card to play");
                        numCard = in.readLine();
                        result = player.playAssistantCard(Integer.parseInt(numCard));
                    } while (result);
                    game.setActualPlayer();
                } else if (line.equals("move students") && game.getPhase() == 1) {
                    player.sendToClient("msg", "move students");
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

}
