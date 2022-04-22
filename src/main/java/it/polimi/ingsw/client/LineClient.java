package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LineClient {

    private final String ip;
    private final int port;

    public LineClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }





    public void startClient() throws IOException {
        System.out.println("====Eriantys CLI Client====");
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        System.out.println("Enter your name");
        BufferedReader socketIn = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);



            new ClientInput(stdin, socketOut).start();
            new ClientOut(socketIn).start();

            /**
        finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }**/
    }
}
