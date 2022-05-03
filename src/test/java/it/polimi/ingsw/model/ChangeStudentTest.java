package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.CharacterCards.ChangeStudent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeStudentTest {

    private Game gameTest;

    @Test
    public void testGetCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        ChangeStudent card = new ChangeStudent();
        assertEquals(1, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        ChangeStudent card = new ChangeStudent();
        card.addCost();
        assertEquals(2, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        ChangeStudent card = new ChangeStudent();
        System.out.println(card.getEffectOfTheCard());
    }
}
