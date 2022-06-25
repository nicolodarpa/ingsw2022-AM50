package it.polimi.ingsw.model;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest{

    private Game gameTest;

    @Test
    @DisplayName("Fill the hall")
    public void testMoveStudentToHall() {
        Player p = new Player("nic");
        StudentsBag s = new StudentsBag();
        s.fillBag(120);
        p.getDashboard().setupHall(2);
        p.fillStudentsHall(s);
        p.getDashboard().setupHall(2);
        assertEquals(7,p.getDashboard().getHall().length);
        p.getDashboard().drawDashboard();
    }
    @Test
    @DisplayName("Play card with order 5 and 3 moves available")
    public void testPlayAssistantCard() {
        Deck testDeck = new Deck(1,"BLUE");
        Player p = new Player("nic");
        p.setDeck(testDeck);
        AssistantCard cardToPlay = testDeck.getCardOrder(5);
        p.playAssistantCard(cardToPlay.getOrder());
        assertEquals(cardToPlay.getOrder(), p.getOrder());
        assertEquals(cardToPlay.getMoveOfMN(), p.getMovesOfMN());
    }

    @Test
    public void testMoveStudentToClassroom(){
        gameTest = new Game(2);

        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);

        Player player = gameTest.getPlist().getPlayerByName("ale");
        player.moveStudentToClassroom(3, gameTest);
        player.getDashboard().drawDashboard();
        assertFalse(player.moveStudentToClassroom(8, gameTest));
    }

    @Test
    public void testMoveStudentToIsland(){
        Game gameTest = new Game(2);

        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);

        Player player = gameTest.getPlist().getPlayers().get(0);
        player.moveStudentToIsland(gameTest.getIslands().get(2), 3);
        assertNull(player.getDashboard().getHall()[3]);
        player.moveStudentToIsland(gameTest.getIslands().get(2), 3); //select a null student
        if(gameTest.getIslands().get(2).getOppositeMN() || gameTest.getIslands().get(2).getPresenceMN())
            assertEquals(1,gameTest.getIslands().get(2).getStudentList().size());
        else
            assertEquals(2,gameTest.getIslands().get(2).getStudentList().size());
        player.getDashboard().drawDashboard();
    }

    @Test
    @DisplayName("Testing invalid input of island's index and student's position")
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
    public void testPlaySpecialCard(){
        Game gameTest = new Game(2);

        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);

        Player p1 = gameTest.getPlist().getPlayerByName("ale");
        //p1.playSpecialCard(gameTest, 2);
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
        int spentCoins = 0;
        p.addCoin(2);
        p.spendCoins(1);
        spentCoins = coin + 2 - 1;
        assertEquals(p.getWallet().getCoins(), spentCoins);
    }


}


