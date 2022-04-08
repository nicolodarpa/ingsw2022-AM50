package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest{

    @Test
    public void testMoveStudentsToHall() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.getStudentsBag().fillBag(120);
        game.setupGame();
        game.moveStudentsToHall();
        assertEquals(100, game.getStudentsInBag());
    }

    @Test
    @DisplayName("testing the setup of a new match")
    public void testSetupGame() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        assertEquals(114, game.getStudentsInBag());
        assertEquals(2,game.getCloudCards().size());
        for(int i = 0; i < 2; i++){
            assertEquals(3,game.getCloudCards().get(i).getStudents().size());
        }

    }

}