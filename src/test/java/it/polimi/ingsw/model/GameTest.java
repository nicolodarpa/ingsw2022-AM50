package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest{

    private final Game gameTest = new Game();

    @Test
    public void testMoveStudentsToHall() {
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.getStudentsBag().fillBag(120);
        gameTest.setupGame();
        assertEquals(100, gameTest.getStudentsInBag());
    }

    @Test
    @DisplayName("testing the setup of a new match")
    public void testSetupGame() {
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        assertEquals(100, gameTest.getStudentsInBag());
        assertEquals(2,gameTest.getCloudCards().size());
        for(int i = 0; i < 2; i++){
            assertEquals(3,gameTest.getCloudCards().get(i).getStudents().size());
        }
    }

    @Test
    @DisplayName(" Test that there are 12 Islands")
    public void createIslandTest(){
        gameTest.createIslands();
        assertEquals(12,gameTest.getIslands().size());
    }

    @Test
    @DisplayName(" Test the assignment of the Teacher")
    public void assignTeacherTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Player player_one = PlayersList.getPlayers().get(0);
        Player player_two = PlayersList.getPlayers().get(1);

        gameTest.assignTeacher();
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();

    }

}