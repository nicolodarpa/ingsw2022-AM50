package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.Player;

public class GameInfoStatus {

    public String phase;

    public String round;
    public String actualPlayer;




    public GameInfoStatus(int phase, Player actualPlayer, int round){
        if(phase == 0)
            this.phase = "Planning phase";
        else
            this.phase = "Action phase";
        this.actualPlayer = actualPlayer.getName();
        this.round = String.valueOf(round);
    }
}
