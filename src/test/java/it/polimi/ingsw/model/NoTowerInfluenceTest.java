package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NoTowerInfluenceTest {

    private Game gameTest;

    @Test
    @DisplayName("Testing the function")
    public void testEffect(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        NoTowerInfluence card = new NoTowerInfluence(gameTest.getIslandWithMN(), gameTest.getPlist());
        card.effect();
        assertEquals(gameTest.getIslandWithMN().getOwner(), "free");
    }

    @Test
    public void testGetCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        NoTowerInfluence card = new NoTowerInfluence(gameTest.getIslandWithMN(), gameTest.getPlist());
        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        NoTowerInfluence card = new NoTowerInfluence(gameTest.getIslandWithMN(), gameTest.getPlist());
        card.addCost();
        assertEquals(4, card.getCost());
    }


}
