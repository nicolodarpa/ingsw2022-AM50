package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.comunication.Command;
import it.polimi.ingsw.comunication.TextMessage;
import it.polimi.ingsw.model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


public final class ClientInput {

    private PrintWriter socketOut;

    private BufferedReader socketIn;
    private final Gson gson= new Gson();

    private final static ClientInput INSTANCE = new ClientInput();

    private ClientInput() {}

    public static ClientInput getInstance(){
        return INSTANCE;

    }

    public void setSocketOut(PrintWriter socketOut) {
        this.socketOut = socketOut;
    }

    public void setSocketIn(BufferedReader socketIn){
        this.socketIn = socketIn;
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


    public BufferedReader getSocketIn(){
        return socketIn;
    }


    public TextMessage readLine(){
        BufferedReader socketIn = ClientInput.getInstance().getSocketIn();
        String msg;
        try {
            msg = socketIn.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();
        return gson.fromJson(msg, TextMessage.class);
    }





}
