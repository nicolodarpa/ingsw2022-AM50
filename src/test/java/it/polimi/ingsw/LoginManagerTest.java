package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginManagerTest{

    @Test
    public void testLogin() {

        Game game = new Game();
        game.setNumberOfPlayers(3);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        assertEquals(2, LoginManager.login("nic", game));
        LoginManager.login("bob", game);
        assertEquals(1, LoginManager.login("bob", game));
        System.out.println("# of players: " + PlayersList.getCurrentNumberOfPlayers());
        assertTrue(PlayersList.contains("ale"));
    }


}