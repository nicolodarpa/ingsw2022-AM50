package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Class of the server
 */
public class MultiEchoServer {
    private final int port;


    /**
     * Constructor method
     * @param port port number
     */
    public MultiEchoServer(int port) {
        this.port = port;
    }


    /**
     * Starts a new server listening for connections.
     * Creates the gameArrayList that contains every active game managed by the server
     * For every accepted connection create a new {@link  EchoServerClientHandler} that manages the connection with a single client
     */
    public void startServer() {
        System.out.println("====Eriantys CLI Server====");
        ArrayList<Game> gameArrayList = new ArrayList<>();
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
                System.out.println(clientSocket.getInetAddress() + " connected");
                EchoServerClientHandler serverThread = new EchoServerClientHandler(clientSocket, gameArrayList);
                serverThread.start();
            } catch (IOException e) {
                break;
            }
        }
    }
}