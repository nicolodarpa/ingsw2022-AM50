package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class PlayerTest{
    @Test
    @DisplayName("Fill the hall")
    public void testMoveStudentToHall() {
        Player p = new Player("nic");
        StudentsBag s = new StudentsBag();
        s.fillBag(120);
        p.moveStudentsToHall(s);
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
        Player player = new Player("jaz");
        StudentsBag s = new StudentsBag();
        s.fillBag(120);
        player.moveStudentsToHall(s);
        player.moveStudentToClassroom(3);
        player.getDashboard().drawDashboard();
    }

    @Test
    public void testMoveStudentToIsland(){
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        Player player = game.getPlist().getPlayers().get(0);
        player.moveStudentToIsland(game.getIslands().get(2), 3);
        if(game.getIslands().get(2).getOppositeMN() || game.getIslands().get(2).getPresenceMN())
            assertEquals(1,game.getIslands().get(2).getStudentList().size());
        else
            assertEquals(2,game.getIslands().get(2).getStudentList().size());
        player.getDashboard().drawDashboard();
    }
}


