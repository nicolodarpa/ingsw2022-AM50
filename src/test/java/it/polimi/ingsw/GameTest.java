package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

public class GameTest extends TestCase {
    Game game = new Game();
    public void testMoveStudentsToHall() {

        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        game.moveStudentsToHall();
        assertEquals(114-14, game.getStudentsInBag());
    }

    public void testSetupGame() {
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        assertEquals(114, game.getStudentsInBag());
    }

    public void testFillStudentsBag() {
    }

    public void testCloudCardFill() {
    }
}