package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.CharacterCards.AddInfluence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddInfluenceTest {

    private Game gameTest;

    @Test
    public void testGetCost() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        AddInfluence card = new AddInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1);
        assertEquals(2, card.getCost());
    }

    @Test
    public void testAddCost() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        AddInfluence card = new AddInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1);
        card.addCost();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard() {
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        gameTest.setupGame();
        AddInfluence card = new AddInfluence();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1);
        System.out.println(card.getEffectOfTheCard());
    }
}

