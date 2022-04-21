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


    public EchoServerClientHandler(Socket socket, Game game) throws IOException {
        this.socket = socket;
        this.game = game;

    }

    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String name = in.readLine();

            boolean check = true;
            while (check) {
                switch (LoginManager.login(name, game, socket)) {
                    case 0 -> {
                        if (game.getCurrentNumberOfPlayers() == 1) {
                            out.println("Welcome " + name + ", choose number of players, 2 or 3 allowed:");
                            String numPlayers;
                            numPlayers = in.readLine();
                            while (!Objects.equals(numPlayers, "2") && !Objects.equals(numPlayers, "3")) {
                                out.println("Please enter a valid number: 2 or 3 allowed");
                                numPlayers = in.readLine();
                            }
                            game.setNumberOfPlayers(Integer.parseInt(numPlayers));
                            out.println("Waiting for other " + (game.getNumberOfPlayers() - 1) + " players");
                        } else {
                            out.println("Welcome " + name);
                        }
                        check = false;
                    }
                    case 2 -> {
                        out.println("Name already in use by another player, chose a different name:");
                        name = in.readLine();
                    }
                    case 1 -> {
                        out.println("Max number of players reached");
                        check = false;
                        in.close();
                        out.close();
                        socket.close();
                    }
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
            out.close();
            socket.close();


        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

}
