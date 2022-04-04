package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiEchoServer {
    private int port;


    public MultiEchoServer(String port) {
        this.port = Integer.parseInt(port);
    }

    public void startServer() {
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
        while(true){
            try{
                Socket socket = serverSocket.accept();
                EchoServerClientHandler serverThread = new EchoServerClientHandler(socket, threadList, game);
                threadList.add(serverThread);
                serverThread.start();
            }catch (IOException e){
                break;
            }
        }
    }
}
