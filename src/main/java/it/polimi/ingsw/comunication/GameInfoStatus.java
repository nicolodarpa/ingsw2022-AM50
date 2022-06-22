package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Player;

/**
 * Template class to encode and decode json with the info of a deck
 */
public class GameInfoStatus {

    public String phase;

    public String round;
    public String actualPlayer;

    public int numberOfPlayer;




    public GameInfoStatus(int phase, Player actualPlayer, int round, int numberOfPlayers){
        if(phase == 0)
            this.phase = "Planning phase";
        else
            this.phase = "Action phase";
        this.actualPlayer = actualPlayer.getName();
        this.round = String.valueOf(round);
        this.numberOfPlayer = numberOfPlayers;
    }
}
