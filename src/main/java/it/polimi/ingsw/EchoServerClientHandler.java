package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;


public class EchoServerClientHandler extends Thread {

    private final Socket socket;
    private final Game game;

    private PrintWriter out;
    private BufferedReader in;

    public EchoServerClientHandler(Socket socket, Game game) throws IOException {
        this.socket = socket;
        this.game = game;

    }

    public void sendMessage(String string) {
        out.println(string);

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
                        out = game.getPlist().getPlayerByName(name).getOut();
                        if (game.getCurrentNumberOfPlayers() == 1) {
                            out.println("Welcome " + name + " Choose number of players, 2 or 3 allowed:");
                            String numPlayers;
                            numPlayers = in.readLine();
                            while (!Objects.equals(numPlayers, "2") && !Objects.equals(numPlayers, "3")) {
                                out.println("Please enter a valid number: 2 or 3 allowed");
                                numPlayers = in.readLine();
                            }
                            game.setNumberOfPlayers(Integer.parseInt(numPlayers));
                        } else {
                            out.println("Welcome " + name);
                        }
                        check = false;
                    }
                    case 2 -> {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Name already in use by another player, chose a different name:");
                        name = in.readLine();
                    }
                    case 1 -> {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Max number of players reached");
                        check = false;
                        in.close();
                        socket.close();
                    }
                }
            }


            game.checkLobby();
            while (!game.waitLobby()){
                out.println("Waiting for other " +  (game.getNumberOfPlayers() - game.getCurrentNumberOfPlayers()) + " players");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }




            while (true) {
                String line = in.readLine();
                if (line.equals("quit")) {
                    break;
                } else {
                    out.println("Received: " + line);
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
