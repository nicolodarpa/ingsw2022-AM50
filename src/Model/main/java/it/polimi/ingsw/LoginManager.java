package it.polimi.ingsw;

public class LoginManager {


    /**
     * private static int numPlayers = 3;
     * <p>
     * public static void setNumPlayers(int num) {
     * numPlayers = num;
     * }
     * <p>
     * public static int getNumPlayers() {
     * return numPlayers;
     * }
     * <p>
     * <p>
     * static ArrayList<String> nicknames;
     * <p>
     * static void allocate() {
     * if (nicknames == null) {
     * nicknames = new ArrayList<String>();
     * }
     * <p>
     * }
     **/

    public static int login(String name, Game game) {
        //allocate();
        //System.out.println("Started login for " + name);
        int count = PlayersList.getCurrentNumberOfPlayers();
        //System.out.println("count: " + count);
        if (count >= game.getNumberOfPlayers()) //max number of player reached
            return 1;
        else if (PlayersList.contains(name)) //name already in use
            return 2;
        game.addPlayer(name);
        System.out.println(name + " logged in");


        return 0;
    }


}