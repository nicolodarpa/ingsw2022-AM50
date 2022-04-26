package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LineClient {
    private final static String ANSI_PRIMARY = "\u001B[36m";
    private final static String ANSI_SECONDARY = "\u001B[32m";
    private static final String ANSI_RESET = "\u001B[0m";

    private final String ip;
    private final int port;

    public LineClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }





    public void startClient() throws IOException {
        System.out.println(ANSI_PRIMARY + "====Eriantys CLI Client====" + ANSI_RESET);
        Socket socket;
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ANSI_SECONDARY + "Connection established" + ANSI_RESET);
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
