package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.net.Socket;

public class LoginManager {

    public static int login(String name, Game game) {

        if (game.getCurrentNumberOfPlayers() >= game.getNumberOfPlayers()) //max number of player reached
            return 1;
        else if (game.containsPlayerByName(name)) //name already in use
            return 2;
        System.out.println(name + " logged in");
        game.addPlayer(name);
        return 0;
    }
}