package it.polimi.ingsw;

import java.util.ArrayList;

public class PlayersList {

    private static ArrayList<Player> players;

    private static void allocate() {
        if (players == null) {
            players = new ArrayList<Player>();
        }
    }

    public static boolean contains(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void addPlayer(String name) {
        allocate();
        Player p = new Player(name);
        players.add(p);
        /**System.out.println("Plist");
         for (Player q : players) {
         System.out.println("- " + q.getName() + " -");
         }**/

    }

    public static void removePlayer(String name) {
        for (Player q : players) {
            if (q.getName().equals(name)) {
                players.remove(q);
                System.out.println(q.getName() + " logged out");

            }
        }
    }

    public void moveStudentsToHall(StudentsBag studentsBag){
        for (Player player: players){
            player.moveStudentsToHall(studentsBag);
        }
    }


    public static int getCurrentNumberOfPlayers() {
        allocate();
        return players.size();
    }
}
