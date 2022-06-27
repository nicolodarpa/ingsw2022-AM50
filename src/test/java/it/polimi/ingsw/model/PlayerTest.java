package it.polimi.ingsw.model;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for {@link Player}
 */
public class PlayerTest{

    /**
     * We fill players' hall with students from the students bag and check that the correct number of students are extracted from the bag
     */
    @Test
    @DisplayName("Test fill the hall")
    public void testMoveStudentToHall() {
        Player p = new Player("nic");
        StudentsBag s = new StudentsBag();
        s.fillBag(120);
        assertEquals(120,s.getNum());
        p.getDashboard().setupHall(2);
        p.fillStudentsHall(s);
        assertEquals(7,p.getDashboard().getHall().length);
        assertEquals(113,s.getNum());
        p.getDashboard().drawDashboard();
    }

    /**
     * We play card with order 5 and 3 mother nature moves, and we check that player's attributes are correctly updated
     */
    @Test
    @DisplayName("Test playAssistantCard")
    public void testPlayAssistantCard() {
        Deck testDeck = new Deck(1,"BLUE");
        Player p = new Player("nic");
        p.setDeck(testDeck);
        AssistantCard cardToPlay = testDeck.getCardOrder(5);
        p.playAssistantCard(cardToPlay.getOrder());
        assertEquals(cardToPlay.getOrder(), p.getOrder());
        assertEquals(cardToPlay.getMoveOfMN(), p.getMovesOfMN());
    }

    /**
     * Test to move a student from the hall to the classroom
     *  Check the result of the actions selecting a valid student, an empty position and then a position out of bound
     */
    @Test
    @DisplayName("Test moveStudentToClassroom")
    public void testMoveStudentToClassroom(){
        Game gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);

        Player player = gameTest.getPlist().getPlayerByName("ale");
        assertTrue(player.moveStudentToClassroom(3, gameTest));
        assertFalse(player.moveStudentToClassroom(3, gameTest));
        player.getDashboard().drawDashboard();
        assertFalse(player.moveStudentToClassroom(8, gameTest));
    }

    /**
     * Test to move a student from the hall to an island
     *  Check the result of the actions selecting a valid student, an empty position and then a position out of bound
     *  Check if the number of students on the island changed
     */
    @Test
    @DisplayName("Test moveStudentToIsland")
    public void testMoveStudentToIsland(){
        Game gameTest = new Game(2);

        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);

        Player player = gameTest.getPlist().getPlayers().get(0);
        assertTrue(player.moveStudentToIsland(2, 3, gameTest));
        assertFalse(player.moveStudentToIsland(2, 3, gameTest));
        assertNull(player.getDashboard().getHall()[2]);
        if(gameTest.getIslands().get(3).getOppositeMN() || gameTest.getIslands().get(3).getPresenceMN())
            assertEquals(1,gameTest.getIslands().get(3).getStudentList().size());
        else
            assertEquals(2,gameTest.getIslands().get(3).getStudentList().size());

    }

    /**
     * Testing invalid input of island's index and student's position
     */
    @Test
    @DisplayName("Test select invalid student position")
    public void testMoveInvalidStudentToIsland(){
        Game gameTest = new Game(2);

        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        Player player = gameTest.getPlist().getPlayers().get(0);
        assertFalse(player.moveStudentToIsland(9,15,gameTest)); //invalid Island index
        assertFalse(player.moveStudentToIsland(9, 9, gameTest)); //invalid student's position
        if(gameTest.getIslands().get(9).getOppositeMN() || gameTest.getIslands().get(9).getPresenceMN())
            assertEquals(0,gameTest.getIslands().get(9).getStudentList().size()); //doesn't add any students to the selected island
        else
            assertEquals(1,gameTest.getIslands().get(9).getStudentList().size()); //doesn't add any students to the selected island
    }


    @Test
    public void sendToClientTest(){
        Player p = new Player("nic");
        p.sendToClient("msg", "must be 'no connection to client'");
    }

    @Test
    public void setSocketTest(){
        Player p = new Player("nic");
        Socket socket = new Socket();
        p.setSocket(socket);
        assertEquals(p.getSocket(), socket, "must be equals");
    }

    @Test
    public void spendCoinsTest(){
        Player p = new Player("nic");
        int coin = p.getCoins();
        int spentCoins;
        p.addCoin(2);
        p.spendCoins(1);
        spentCoins = coin + 2 - 1;
        assertEquals(p.getWallet().getCoins(), spentCoins);
    }


}


