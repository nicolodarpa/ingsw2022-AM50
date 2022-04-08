package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//NOO public class GameTest extends TestCase {

public class GameTest  {

    @Test
    public void testMoveStudentsToHall() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        game.setupGame();
        game.moveStudentsToHall();

        //TDB
        assertEquals(114-14, game.getStudentsInBag(), "MESSAGE ON FAILURE");

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