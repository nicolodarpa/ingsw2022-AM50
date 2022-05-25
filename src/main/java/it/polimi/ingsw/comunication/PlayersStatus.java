package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class PlayersStatus {

    public String name;

    public int order;
    public int movesOfMN;
    public int movesOfStudents;
    public boolean hasPlayed;

    public PlayersStatus(Player player) {
        this.name = player.getName();
        this.order = player.getOrder();
        this.movesOfMN = player.getMovesOfMN();
        this.movesOfStudents = player.getMovesOfStudents();
        this.hasPlayed = player.getHasPlayed();
    }
}
