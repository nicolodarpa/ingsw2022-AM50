package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiEchoServer {
    private int port;


    public MultiEchoServer(int port) {
        this.port = port;
    }

    public void startServer() {
        System.out.println("====Eriantys CLI Server====");
        ArrayList<EchoServerClientHandler> threadList = new ArrayList<>();
        Game game = new Game();
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
                EchoServerClientHandler serverThread = new EchoServerClientHandler(clientSocket, game);
                threadList.add(serverThread);
                serverThread.start();
            } catch (IOException e) {
                break;
            }
        }
    }
}