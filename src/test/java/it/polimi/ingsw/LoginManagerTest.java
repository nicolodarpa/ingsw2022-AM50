package it.polimi.ingsw;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test login functionalities
 */
public class LoginManagerTest {

    /**
     * Checks if the connection of two players with the same username is refused, checks that the player is successfully added to the game
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
        assertEquals(2, LoginManager.login("bob", game));
        assertEquals(3,game.getCurrentNumberOfPlayers());
        assertTrue(game.containsPlayerByName("ale"));
    }

    /**
     * Check if a player can quit a game and join back
     */
    @Test
    @DisplayName("Test log back")
    public void testLogBack() {
        Game game = new Game();
        game.setNumberOfPlayers(3);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        LoginManager.login("jaz", game);

        Player p3 = game.getPlist().getPlayerByName("jaz");
        game.removePlayer(p3);//p3 logs out

        assertFalse(p3.isActive());//checks if p3 correctly logged out
        assertEquals(0,LoginManager.login("jaz",game));//p3 logs back
        assertTrue(p3.isActive());//checks if p3 correctly logged back
    }


    /**
     * Checks that no more than the selected number of players can log in
     */
    @Test
    @DisplayName("Test max number of player recahed")
    public void testMaxPlayers(){
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);

        assertEquals(1, LoginManager.login("jaz",game));
        assertEquals(2,game.getCurrentNumberOfPlayers());
    }

}