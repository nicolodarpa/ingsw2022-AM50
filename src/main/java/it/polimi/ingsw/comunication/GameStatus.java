package it.polimi.ingsw.comunication;

import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.Player;

public class GameStatus {

    public int currentNumber;
    public int totalPlayers;

    public StringBuilder playersName= new StringBuilder("");

    public GameStatus(int currentNumber, int totalPlayers, PlayersList playersList){
        this.currentNumber = currentNumber;
        this.totalPlayers = totalPlayers;
        for (Player player: playersList.getPlayers()){
            playersName.append(" -").append(player.getName());

        }

    }
}
