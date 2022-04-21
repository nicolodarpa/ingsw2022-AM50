package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;

import java.net.Socket;

public class LoginManager {

    public static int login(String name, Game game, Socket socket) {
        int count = PlayersList.getCurrentNumberOfPlayers();

        if (count >= game.getNumberOfPlayers()) //max number of player reached
            return 1;
        else if (PlayersList.contains(name)) //name already in use
            return 2;
        game.addPlayer(name, socket);
        System.out.println(name + " logged in");
        return 0;
    }
    public static int login(String name, Game game) {
        Socket socket = new Socket();
        int count = PlayersList.getCurrentNumberOfPlayers();
        if (count >= game.getNumberOfPlayers()) //max number of player reached
            return 1;
        else if (PlayersList.contains(name)) //name already in use
            return 2;
        game.addPlayer(name, socket);
        System.out.println(name + " logged in");
        return 0;
    }



}