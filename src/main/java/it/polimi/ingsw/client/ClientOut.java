package it.polimi.ingsw.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.BatchUpdateException;

public class ClientOut extends Thread {

    private final BufferedReader socketIn;

    public ClientOut(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }

    @Override
    public void run() {
        while (true) {
            String socketLine;
            try {
                socketLine = socketIn.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (socketLine!=null){
                System.out.println(socketLine);
            }

        }

    }
}
