package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TableTest {

    private Game gameTest = new Game();
    private Table tableTest = new Table(gameTest.getCloudCards(), gameTest.getIslands());


    @Test
    @DisplayName("Draw the table at the beginning of the match")
    public void drawTableTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        tableTest.drawTable();
    }

    @Test
    @DisplayName("Add a student to an Island")
    public void addStudentTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Island islandTest = gameTest.getIslands().get(0);
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        player_one.moveStudentToIsland(islandTest,2);
        player_one.getDashboard().drawDashboard();
        tableTest.drawTable();

    }

}
