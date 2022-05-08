package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class MultiEchoServer {
    private int port;


    public MultiEchoServer(int port) {
        this.port = port;
    }

    public void startServer() {
        System.out.println("====Eriantys CLI Server====");
        ArrayList<EchoServerClientHandler> threadList = new ArrayList<>();
        ArrayList<Game> gameArrayList = new ArrayList<>();
        Game game = new Game();
        gameArrayList.add(game);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Server ready on port: " + port);
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                if (Objects.equals(game.getGameStatus(), "active")){
                    game = new Game();
                    gameArrayList.add(game);
                }
                EchoServerClientHandler serverThread = new EchoServerClientHandler(clientSocket, game, gameArrayList.size());
                threadList.add(serverThread);
                serverThread.start();
            } catch (IOException e) {
                break;
            }
        }
    }
}