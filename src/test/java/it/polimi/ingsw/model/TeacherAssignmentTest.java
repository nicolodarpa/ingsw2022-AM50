package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherAssignmentTest {
    private Game gameTest = new Game();


    @Test
    public void testGetCost(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        TeacherAssignment card = new TeacherAssignment(gameTest.getPlist());
        assertEquals(2, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        TeacherAssignment card = new TeacherAssignment(gameTest.getPlist());
        card.addCost();
        assertEquals(3, card.getCost());
    }
}
