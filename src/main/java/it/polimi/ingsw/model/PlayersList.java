package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentsBag;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class contains the list of the players that are playing a match
 */
public class PlayersList {


    private ArrayList<Player> players = new ArrayList<>();


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public boolean containsByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method add new player to the playersList
     */
    public void addPlayer(Player player) {
        players.add(player);

    }

    public void removePlayer(Player player) {
       players.remove(player);
       System.out.println(player.getName() +" logged out");
    }

    public void moveStudentsToHall(StudentsBag studentsBag) {
        for (Player player : players) {
            player.moveStudentsToHall(studentsBag);
        }
    }


    public int getCurrentNumberOfPlayers() {
        return players.size();
    }

    public void notifyAllClients(String type, String message) {

        for (Player player : players) {
            player.sendToClient(type, message);
        }


    }
}