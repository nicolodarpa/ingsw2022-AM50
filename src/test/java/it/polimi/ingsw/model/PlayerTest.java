package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class PlayerTest{

    private Game gameTest;

    @Test
    @DisplayName("Fill the hall")
    public void testMoveStudentToHall() {
        Player p = new Player("nic");
        StudentsBag s = new StudentsBag();
        s.fillBag(120);
        p.moveStudentsToHall(s);
        p.getDashboard().setupHall(2);
        assertEquals(7,p.getDashboard().getHall().length);
        p.getDashboard().drawDashboard();
    }
    @Test
    @DisplayName("Play card with order 5 and 3 moves available")
    public void testPlayAssistantCard() {
        Deck testDeck = new Deck(1);
        Player p = new Player("nic");
        p.setDeck(testDeck);
        p.playAssistantCard(4);
        assertEquals(5, p.getOrder());
        assertEquals(3, p.getMovesOfMN());
    }

    @Test
    public void testMoveStudentToClassroom(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        Player player = gameTest.getPlist().getPlayerByName("ale");
        player.moveStudentToClassroom(3, gameTest);
        player.getDashboard().drawDashboard();
        assertFalse(player.moveStudentToClassroom(8, gameTest));
    }

    @Test
    public void testMoveStudentToIsland(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        Player player = gameTest.getPlist().getPlayers().get(0);
        player.moveStudentToIsland(gameTest.getIslands().get(2), 3);
        if(gameTest.getIslands().get(2).getOppositeMN() || gameTest.getIslands().get(2).getPresenceMN())
            assertEquals(1,gameTest.getIslands().get(2).getStudentList().size());
        else
            assertEquals(2,gameTest.getIslands().get(2).getStudentList().size());
        player.getDashboard().drawDashboard();
    }

    @Test
    public void testPlaySpecialCard(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        //p1.playSpecialCard(gameTest, 2);
    }

    @Test
    public void changeStudentTest(){
        Game gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        p1.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        p1.getDashboard().drawDashboard();
        p1.changeStudent(PawnColor.RED, 6);
        p1.getDashboard().drawDashboard();
    }


}


