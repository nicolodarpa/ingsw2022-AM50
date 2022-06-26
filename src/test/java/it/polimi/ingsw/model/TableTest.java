package it.polimi.ingsw.model;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Table}
 */
public class TableTest {

    private final Game gameTest = new Game();
    private final Table tableTest = new Table(gameTest.getIslands());


    /**
     * Tests if the islands are correctly draw.
     */
    @Test
    @DisplayName("Draw the table at the beginning of the match")
    public void drawTableTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        tableTest.drawTable();
    }

    @Test
    @DisplayName("Add a student to an Island")
    public void addStudentTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        Island islandTest = gameTest.getIslands().get(0);
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        player_one.moveStudentToIsland(gameTest.getIslandWithMNIndex(), 2, gameTest);
        player_one.getDashboard().drawDashboard();
        tableTest.drawTable();

    }

}
