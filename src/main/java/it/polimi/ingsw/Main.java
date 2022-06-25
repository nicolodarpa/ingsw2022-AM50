package it.polimi.ingsw;

import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.LineClient;
import it.polimi.ingsw.server.controller.MultiEchoServer;

import java.util.Objects;


/**
 * Main class of the project.
 * Manages command line arguments and start the correct class
 */
public class Main {
    public static void main(String[] args) {
        int port = getPort(args);
        String ip = getIp(args);
        if (args.length > 0) {

            if (args[0].equals("-server")) {
                startServer(port);
            } else if (args[0].equals("-cli")) {
                startClient(ip, port);
            } else {
                startGuiClient();
            }
        } else {
            startGuiClient();
        }


    }

    /**
     * Starts server
     */
    private static void startServer(int port) {
        MultiEchoServer echoServer = new MultiEchoServer(port);
        echoServer.startServer();
    }

    /**
     * Starts the client GUI.
     */
    private static void startGuiClient() {
        ClientGUI.main();
    }

    /**
     * Starts the client CLI
     *
     * @param ip   ip address
     * @param port port number
     */
    private static void startClient(String ip, int port) {
        LineClient client = new LineClient(ip, port);
        try {
            client.startClient();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gets the port number added via command line arguments
     *
     * @param args command line argument
     * @return port number if added via args, else return 1337 as default
     */
    private static int getPort(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(args[i], "-p")) {
                return Integer.parseInt(args[i + 1]);
            }
        }
        return 1337;

    }

    /**
     * Gets the ip address added via command line arguments
     *
     * @param args command line argument
     * @return ip address if added via args, else return 127.0.0.1 as default
     */
    private static String getIp(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (Objects.equals(args[i], "-ip")) {
                return args[i + 1];
            }
        }
        return "127.0.0.1";  //default ip
    }

}