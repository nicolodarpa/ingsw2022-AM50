package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.Command;
import it.polimi.ingsw.model.Game;

import java.io.PrintWriter;


public final class ClientInput {

    private PrintWriter socketOut;
    private final Gson gson= new Gson();

    private final static ClientInput INSTANCE = new ClientInput();

    private ClientInput() {}

    public static ClientInput getInstance(){
        return INSTANCE;

    }

    public void setSocketOut(PrintWriter socketOut) {
        this.socketOut = socketOut;
    }

    public void sendString(String cmd, String value) {
        Command command = new Command(cmd, value);
        String json = gson.toJson(command, Command.class);
        socketOut.println(json);
        socketOut.flush();
    }

    public void sendString(String cmd, String value, String value2) {
        Command command = new Command(cmd, value, value2);
        String json = gson.toJson(command, Command.class);
        socketOut.println(json);
        socketOut.flush();
    }





}
