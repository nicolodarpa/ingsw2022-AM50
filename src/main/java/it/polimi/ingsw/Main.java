package it.polimi.ingsw;

import java.io.IOException;


/**
 * Hello world!
 *
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Eriantis CLI");

        if (args.length > 0) {
            if (args[0].equals("-server")) {
                MultiEchoServer echoServer = new MultiEchoServer(args[1]);
                echoServer.startServer();
            }
        } else {
            LineClient client = new LineClient("127.0.0.1", 1337);
            try {
                client.startClient();

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }


    }
}
