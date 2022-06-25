package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.CharacterCards.NoTowerInfluenceStrategy;
import it.polimi.ingsw.server.model.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test for {@link NoTowerInfluenceStrategy}
 */
public class NoTowerInfluenceStrategyTest {

    private Game gameTest;

    @Test
    @DisplayName("Testing the function")
    public void testEffect(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.effect();
        assertEquals(gameTest.getIslandWithMN().getOwner(), "free");
    }

    @Test
    public void testGetCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.addCost();
        assertEquals(4, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        System.out.println(card.getEffectOfTheCard());
    }




}
