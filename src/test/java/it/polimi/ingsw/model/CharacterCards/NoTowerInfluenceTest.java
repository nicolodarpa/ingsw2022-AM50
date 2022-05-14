package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.CharacterCards.NoTowerInfluence;
import it.polimi.ingsw.model.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NoTowerInfluenceTest {

    private Game gameTest;

    @Test
    @DisplayName("Testing the function")
    public void testEffect(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluence card = new NoTowerInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.effect();
        assertEquals(gameTest.getIslandWithMN().getOwner(), "free");
    }

    @Test
    public void testGetCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluence card = new NoTowerInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluence card = new NoTowerInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.addCost();
        assertEquals(4, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluence card = new NoTowerInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        System.out.println(card.getEffectOfTheCard());
    }




}
