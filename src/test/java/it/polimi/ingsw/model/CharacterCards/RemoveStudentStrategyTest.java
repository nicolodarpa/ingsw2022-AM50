package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.CharacterCards.RemoveStudentStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link RemoveStudentStrategy}
 */
class RemoveStudentStrategyTest {

    /**
     * Test if three GREEN players are removed from the dashboard
     */
    @Test
    @DisplayName("Test")
    void effect(){
        Game gameTest;
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        Player ale = gameTest.getPlist().getPlayerByName("ale");
        Dashboard dashboardTest = ale.getDashboard();
        for (int i = 0; i<3;i++){
            dashboardTest.addStudentToClassroom(new Student(PawnColor.GREEN));
        }
        RemoveStudentStrategy card = new RemoveStudentStrategy();
        card.setPlayersList(gameTest.getPlist());
        card.setPawnColor(PawnColor.GREEN);
        card.setBag(gameTest.getStudentsBag());
        card.effect();
        assertEquals(0,dashboardTest.countStudentByColor(PawnColor.GREEN));
    }
}