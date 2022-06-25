package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test login functionalities
 */
public class LoginManagerTest {

    /**
     * Checks if the connection of two players with the same username is refused, checks the player is successfully added to the game
     *
     */
    @DisplayName("Login same username")
    @Test
    public void testLogin() {
        Game game = new Game();
        game.setNumberOfPlayers(3);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        assertEquals(2, LoginManager.login("nic", game));
        LoginManager.login("bob", game);
        assertEquals(1, LoginManager.login("bob", game));
        System.out.println("# of players: " + game.getPlist().getCurrentNumberOfPlayers());
        assertTrue(game.containsPlayerByName("ale"));
    }


}