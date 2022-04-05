package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

public class GameTest extends TestCase {

    public void testMoveStudentsToHall() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        game.moveStudentsToHall();
        assertEquals(114-14, game.getStudentsInBag());

    }

    public void testSetupGame() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        assertEquals(114, game.getStudentsInBag());
    }

}