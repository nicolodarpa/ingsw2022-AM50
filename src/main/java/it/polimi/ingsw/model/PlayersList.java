package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * This class contains the list of the players that are playing a match
 */
public class PlayersList {

    /**
     * They are the players in game
     */
    private ArrayList<Player> players = new ArrayList<>();


    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @param name is the player's name
     * @return the player who has the name entered in input or null if there isn't any player of that name.
     */
    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * @param name is the player's name
     * @return true if player is contains by the playersList, false otherwise.
     */
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

    /**
     * This method removes a player from the playersList
     */
    public void removePlayer(Player player) {
        //players.remove(player);
        player.setActive(false);
        notifyAllClients("msg", player.getName() + " logged out");
        System.out.println(player.getName() + " logged out");
    }

    /**
     * Moves the students from studentsBag to each player's dashboard.
     *
     * @param studentsBag is the StudentBag where it takes the students. {@link StudentsBag}
     */
    public void moveStudentsToHall(StudentsBag studentsBag) {
        for (Player player : players) {
            player.fillStudentsHall(studentsBag);
        }
    }


    public int getCurrentNumberOfPlayers() {
        return players.size();
    }

    public int getActivePlayers(){
        int i = 0;
        for (Player player: players){
            if (player.isActive()) i++;
        }
        return i;
    }

    /**
     * Send a message to each client connected.
     *
     * @param type    is the message's type.
     * @param message is the message's object.
     */
    public void notifyAllClients(String type, String message) {
        for (Player player : players) {
            if (player.isActive()) player.sendToClient(type, message);

        }


    }
}