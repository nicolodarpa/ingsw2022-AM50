package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        PawnColor color = player_one.getDashboard().getHall()[2].getColor();
        player_one.moveStudentToClassroom(2);
        gameTest.assignTeacher();
        assertNotNull(player_one.getDashboard().getTeacherTable()[color.ordinal()]);
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();

    }

    @Test
    @DisplayName(" add student on the islands at the beginning of a new match")
    public void addStudentToIslandTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        for(Island i : gameTest.getIslands()){
            if(!i.getPresenceMN() && !i.getOppositeMN()){
                assertEquals(1,i.getStudentList().size());
            }
        }
    }

    @Test
    @DisplayName(" Connect two island with the same owner")
    public void connectIslandTest(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        Player p = PlayersList.getPlayers().get(0);
        for(Island i : gameTest.getIslands()){
            System.out.println("Numero isola: " + i.getId());
        }
        System.out.println(" ");
        gameTest.getIslands().get(2).setOwner(p);
        gameTest.getIslands().get(3).setOwner(p);
        gameTest.connectIsland();
        gameTest.getIslands().get(3).setOwner(p);
        gameTest.connectIsland();
        for(Island i : gameTest.getIslands()){
            System.out.println("Numero isola: " + i.getId());
        }

    }

}