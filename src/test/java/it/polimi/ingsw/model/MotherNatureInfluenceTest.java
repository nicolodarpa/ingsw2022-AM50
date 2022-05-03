package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.CharacterCards.MotherNatureInfluence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MotherNatureInfluenceTest {

    private Game gameTest;

    @Test
    public void testGetCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        MotherNatureInfluence card = new MotherNatureInfluence();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        MotherNatureInfluence card = new MotherNatureInfluence();
        card.addCost();
        assertEquals(4, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        MotherNatureInfluence card = new MotherNatureInfluence();
        System.out.println(card.getEffectOfTheCard());
    }
}
