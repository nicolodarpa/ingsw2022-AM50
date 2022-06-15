package it.polimi.ingsw.comunication;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;

public class PlayersStatus {

    public String name;

    public int order;
    public int movesOfMN;
    public int movesOfStudents;

    public int wallet;
    public String getName() {
        return name;
    }

    public int getOrder() {
        return order;
    }

    public int getMovesOfMN() {
        return movesOfMN;
    }

    public int getMovesOfStudents() {
        return movesOfStudents;
    }

    public boolean isHasPlayed() {
        return hasPlayed;
    }

    public boolean hasPlayed;

    public PlayersStatus(Player player) {
        this.name = player.getName();
        this.order = player.getOrder();
        this.movesOfMN = player.getMovesOfMN();
        this.movesOfStudents = player.getMovesOfStudents();
        this.hasPlayed = player.getHasPlayed();
        this.wallet = player.getWallet().getCoins();
    }


}
