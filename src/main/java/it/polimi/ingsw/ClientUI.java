package it.polimi.ingsw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientUI {

    private PrintWriter pw;

    public ClientUI(Socket socket) throws IOException {
        pw = new PrintWriter(socket.getOutputStream(), true);
    }

    public void print(String string) {
        pw.println(string);
    }

    public void close() {
        pw.close();
    }
}
