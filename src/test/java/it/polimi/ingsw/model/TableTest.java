package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TableTest {

    private Game gameTes = new Game();
    private Table tableTest = new Table(gameTes.getCloudCards(), gameTes.getIslands());


    @Test
    @DisplayName("Draw the table at the beginning of the match")
    public void drawTableTest(){
        gameTes.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTes);
        LoginManager.login("nic", gameTes);
        gameTes.setupGame();
        tableTest.drawTable();
    }

    @Test
    @DisplayName("Add a student to an Island")
    public void addStudentTest(){
        gameTes.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTes);
        LoginManager.login("nic", gameTes);
        gameTes.setupGame();
        Island islandTest = gameTes.getIslands().get(0);
        Player player_one = PlayersList.getPlayers().get(0);
        player_one.moveStudentToIsland(islandTest,2);
        player_one.getDashboard().drawDashboard();
        tableTest.drawTable();

    }

}
