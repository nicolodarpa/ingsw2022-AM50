package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.PlayersList;
import it.polimi.ingsw.model.Player;

import java.util.Objects;


/**
 * Template class to encode and decode json with the state of a game
 */
public class GameStatus {

    public int gameId;
    public int currentNumber;
    public int totalPlayers;

    public String status;


    public StringBuilder playersName= new StringBuilder("");

    public GameStatus(int gameId, int currentNumber, int totalPlayers, PlayersList playersList, String status){
        this.gameId = gameId;
        this.currentNumber = currentNumber;
        this.totalPlayers = totalPlayers;
        this.status= status;
        if (Objects.equals(status, "ENDED")){
            playersName.append("GAME ENDED");
        }else {
            for (Player player: playersList.getPlayers()){
                playersName.append(" -").append(player.getName());
            }
        }

    }
}
