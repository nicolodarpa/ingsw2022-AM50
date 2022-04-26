package it.polimi.ingsw;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.StudentsBag;

import java.util.ArrayList;

/**
 * This class contains the players list that are playing a match
 */

public class PlayersList {

    private final ArrayList<Player> players = new ArrayList<>();

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
    public void addPlayer(String name) {
        Player p = new Player(name);
        players.add(p);

    }

    public synchronized void removePlayer(String name) {
        for (Player q : players) {
            if (q.getName().equals(name)) {
                players.remove(q);
                System.out.println(q.getName() + " logged out");

            }
        }
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