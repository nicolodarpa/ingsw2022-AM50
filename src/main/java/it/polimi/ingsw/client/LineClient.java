package it.polimi.ingsw.client;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
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


    public void startClient() {
        System.out.println(ANSI_PRIMARY + "====Eriantys CLI Client====" + ANSI_RESET);
        Socket socket;
        try {
            socket = new Socket(ip, port);
            System.out.println(ANSI_SECONDARY + "Connection established" + ANSI_RESET);
            System.out.println("Enter your name");
            BufferedReader socketIn;
            PrintWriter socketOut;
            Scanner stdin;
            socketIn = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            socketOut = new PrintWriter(socket.getOutputStream());
            stdin = new Scanner(System.in);
            new ClientOut(socketIn, socket).start();
            while (!socket.isClosed()) {
                String inputLine = stdin.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
            }

        } catch (Exception e) {
            System.out.println("Connection refused, no server online\n" +
                    "Start a server before launching the client");
        }

    }
}
