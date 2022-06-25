package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.Command;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.server.controller.EchoServerClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Send messages from the client to the server and read incoming message from the server.
 */


public final class ClientInput {


    /**
     * PrintWriter used to send messages as strings to the server
     */
    private PrintWriter socketOut;

    /**
     * BufferedReader used to receive messages from the server
     */
    private BufferedReader socketIn;
    private final Gson gson = new Gson();

    /**
     * Static instance of the class.
     */
    private final static ClientInput INSTANCE = new ClientInput();

    private ClientInput() {
    }

    /**
     * @return the instance of ClientInput
     */
    public static ClientInput getInstance() {
        return INSTANCE;

    }

    public void setSocketOut(PrintWriter socketOut) {
        this.socketOut = socketOut;
    }

    public void setSocketIn(BufferedReader socketIn) {
        this.socketIn = socketIn;
    }

    /**
     * Sends a message to the server as a string formatted from a json with the layout of {@link Command} whit only one optional value
     *
     * @param cmd   the command to be sent, must match one of the keys of the commandMap in {@link EchoServerClientHandler}
     * @param value option value necessary to execute the command, example a student index or an assistant card index
     */
    public void sendString(String cmd, String value) {
        Command command = new Command(cmd, value);
        String json = gson.toJson(command, Command.class);
        socketOut.println(json);
        socketOut.flush();
    }

    /**
     * Sends a message to the server as a string formatted from a json with the layout of {@link Command}
     *
     * @param cmd    the command to be sent, must match one of the keys of the commandMap in {@link EchoServerClientHandler}
     * @param value  option value necessary to execute the command, example a student index or an assistant card index
     * @param value2 option value necessary to execute the command, example an island index or a pawn color
     */
    public void sendString(String cmd, String value, String value2) {
        Command command = new Command(cmd, value, value2);
        String json = gson.toJson(command, Command.class);
        socketOut.println(json);
        socketOut.flush();
    }


    /**
     *
     * @return socketIn, used to read messages from the server
     */
    public BufferedReader getSocketIn() {
        return socketIn;
    }


    /**
     * Read incoming messages from the server and decode the received string to json
     * @return json formatted as {@link TextMessage}
     */
    public TextMessage readLine() {
        BufferedReader socketIn = ClientInput.getInstance().getSocketIn();
        String msg;
        try {
            msg = socketIn.readLine();
            Gson gson = new Gson();
            return gson.fromJson(msg, TextMessage.class);
        } catch (IOException e) {
            System.out.println("No connection to the server");
        }
        return new TextMessage("quit","No connection to the server");
    }


}
