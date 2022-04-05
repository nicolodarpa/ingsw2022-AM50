package it.polimi.ingsw;

import java.io.IOException;
import java.util.Objects;


/**
 *
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Eriantis CLI");
        int port = getPort(args);
        if (args.length > 0) {

            if (args[0].equals("-server")) {
                MultiEchoServer echoServer = new MultiEchoServer(port);
                echoServer.startServer();
            }
        } else {
            LineClient client = new LineClient("127.0.0.1", port);
            try {
                client.startClient();

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }


    }

    public static int getPort(String[] args){
        for (int i = 0; i<args.length; i++){
            if (Objects.equals(args[i], "-p")){
                return Integer.parseInt(args[i+1]);
            }
        }
        return 1337;  //default port

    }
}
