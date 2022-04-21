package it.polimi.ingsw;

import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.Test;

import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginManagerTest {
    @Test
    public void testLogin() {
        Game game = new Game();
        game.setNumberOfPlayers(2);
        LoginManager.login("ale", game);
        LoginManager.login("nic", game);
        assertEquals(1, LoginManager.login("nic", game));
        LoginManager.login("bob", game);
        assertEquals(1, LoginManager.login("bob", game));
        System.out.println("# of players: " + PlayersList.getCurrentNumberOfPlayers());
        assertTrue(PlayersList.contains("ale"));
    }


}