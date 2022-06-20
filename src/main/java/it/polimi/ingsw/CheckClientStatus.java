package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class CheckClientStatus extends Thread {

    private final Game game;
    private final Player player;
    private final Socket socket;

    private final BufferedReader in;

    public CheckClientStatus(Game game, Player player, Socket socket, BufferedReader in) {
        this.game = game;
        this.player = player;
        this.socket = socket;
        this.in = in;
    }


    public void run() {
        while (true) {
            try {
                if (in.read() == -1) {
                    System.out.println(player.getName() + " disconnected");
                    game.removePlayer(player);
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
