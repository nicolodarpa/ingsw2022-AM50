package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.LineClient;
import it.polimi.ingsw.client.view.LineClient;

import java.io.IOException;
import java.util.Objects;


public class Main {
    public static void main(String[] args) {
        int port = getPort(args);
        String ip = getIp(args);
        if (args.length > 0) {

            if (args[0].equals("-server")) {
                MultiEchoServer echoServer = new MultiEchoServer(port);
                echoServer.startServer();
            } else if (args[0].equals("-cli")) {
                startClient(ip,port);
            } else {
                startGuiClient(ip, port);
            }

        } else {
            startGuiClient(ip, port);
        }


    }

    public static void  startGuiClient(String ip, int port){

        ClientGUI.main(ip, port);

    }

    public static void startClient(String ip, int port){
        LineClient client = new LineClient(ip,port);
        try {
            client.startClient();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static int getPort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(args[i], "-p")) {
                return Integer.parseInt(args[i + 1]);
            }
        }
        return 1337;  //default port

    }

    public static String getIp(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(args[i], "-ip")) {
                return args[i + 1];
            }
        }
        return "127.0.0.1";  //default ip
    }

}