package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game gameTest;

    @Test
    public void testMoveStudentsToHall() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        assertEquals(100, gameTest.getStudentsInBag() );
    }

    @Test
    @DisplayName("testing the setup of a new match")
    public void testSetupGame() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        assertEquals(100, gameTest.getStudentsInBag());
        assertEquals(2, gameTest.getCloudCards().size());
        for (int i = 0; i < 2; i++) {
            assertEquals(3, gameTest.getCloudCards().get(i).getStudents().size());
        }
    }

    @Test
    @DisplayName(" Test that there are 12 Islands")
    public void createIslandTest() {
        gameTest = new Game();
        gameTest.createIslands();
        assertEquals(12, gameTest.getIslands().size());
    }

    @Test
    @DisplayName(" Test the assignment of the Teacher")
    public void assignTeacherTest() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        PawnColor color = player_one.getDashboard().getHall()[2].getColor();
        player_one.getDashboard().drawDashboard();
        player_one.moveStudentToClassroom(2);
        gameTest.assignTeacher();
        assertNotNull(player_one.getDashboard().getTeacherTable()[color.ordinal()]);
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();

    }

    @Test
    @DisplayName(" add student on the islands at the beginning of a new match")
    public void addStudentToIslandTest() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        for (Island i : gameTest.getIslands()) {
            if (!i.getPresenceMN() && !i.getOppositeMN()) {
                assertEquals(1, i.getStudentList().size());
            }
        }
    }

    @Test
    @DisplayName(" Connect two island with the same owner")
    public void connectIslandTest() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Player p = gameTest.getPlist().getPlayers().get(0);
        Player q = gameTest.getPlist().getPlayers().get(1);
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - group: " + i.getIdGroup() + ", owner: " + i.getOwner());
        }
        System.out.println(" ");
        gameTest.getIslands().get(2).setOwner(p);
        gameTest.getIslands().get(3).setOwner(p);
        gameTest.getIslands().get(4).setOwner(p);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - group: " + i.getIdGroup() + ", owner: " + i.getOwner());
        }
        System.out.println(" ");
        gameTest.getIslands().get(5).setOwner(q);
        gameTest.getIslands().get(6).setOwner(q);
        gameTest.getIslands().get(9).setOwner(p);
        gameTest.getIslands().get(7).setOwner(p);
        gameTest.getIslands().get(8).setOwner(p);
        gameTest.connectIsland();
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - group: " + i.getIdGroup() + ", owner: " + i.getOwner());
        }
        System.out.println(" ");
        gameTest.getIslands().get(5).setOwner(p);
        gameTest.getIslands().get(6).setOwner(p);
        gameTest.getIslands().get(7).setOwner(q);
        for (Island i : gameTest.getIslands()) {
            System.out.println("Numero isola: " + i.getId() + " - group: " + i.getIdGroup() + ", owner: " + i.getOwner());
        }
        System.out.println(" ");

    }

}