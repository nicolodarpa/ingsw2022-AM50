package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;

/**
 * Manages players login to a game.
 */
public class LoginManager {

    /**
     * Checks if the game is already full and if a player is reconnecting to a game he previously left, checks if the username is unique.
     *
     * @param name name of the player trying to log in
     * @param game game chosen
     * @return int value for different results, 0 for a successful login, 1 if the lobby is full, 2 if the name is already in use
     */
    public static int login(String name, Game game) {
        if (game.containsPlayerByName(name)) {
            if (game.getPlist().getPlayerByName(name).isActive()) {
                return 2; //name already in use
            } else {
                System.out.println(name + " logged back");
                game.getPlist().getPlayerByName(name).setActive(true);
                return 0; //log back in
            }
        } else if (game.getCurrentNumberOfPlayers() >= game.getNumberOfPlayers()) {
            return 1; //max number of player reached
        }
        System.out.println(name + " logged in");
        game.addPlayer(name);
        return 0; //login
    }
}

