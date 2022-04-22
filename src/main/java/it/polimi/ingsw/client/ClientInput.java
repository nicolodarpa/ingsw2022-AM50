package it.polimi.ingsw.client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientInput extends Thread {

    private final Scanner stdin;
    private final PrintWriter socketOut;


    public ClientInput(Scanner stdin, PrintWriter socketOut) {
        this.stdin = stdin;
        this.socketOut = socketOut;

    }


    public void run() {
        while (true){
            String inputLine = stdin.nextLine();
            socketOut.println(inputLine);
            socketOut.flush();
        }
    }
}
