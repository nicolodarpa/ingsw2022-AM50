package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.PlayersList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PlayersList}
 */
public class PlayersListTest {

    private final PlayersList playersList = new PlayersList();
    private final Player playerTest = new Player("nic");

    @Test
    void removePlayer(){
        playersList.addPlayer(playerTest);
        assertTrue(playersList.containsByName(playerTest.getName()));
        playersList.removePlayer(playerTest);
        assertFalse(playerTest.isActive());
    }
}
