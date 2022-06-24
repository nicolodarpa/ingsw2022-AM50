package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayersListTest {

    private final PlayersList playersList = new PlayersList();
    private final Player playerTest = new Player("nic");

    @Test
    void removePlayer(){
        playersList.addPlayer(playerTest);
        assertTrue(playersList.containsByName(playerTest.getName()));
        playersList.removePlayer(playerTest);
        System.out.println(playersList.getPlayers().size());
        assertFalse(playerTest.isActive());

    }
}
