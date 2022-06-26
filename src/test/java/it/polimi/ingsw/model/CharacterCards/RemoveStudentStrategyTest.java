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
     * Test if three GREEN students are removed from the players' hall.
     */
    @Test
    @DisplayName("Test")
    void effect(){
        Player p1 = new Player("Jaz");
        Player p2 = new Player("Nic");

        PlayersList playersList = new PlayersList();

        playersList.addPlayer(p1);
        playersList.addPlayer(p2);

        Dashboard dashboardOne = new Dashboard();
        Dashboard dashboardTwo = new Dashboard();

        dashboardOne.setupHall(2);
        dashboardTwo.setupHall(2);

        p1.setDashboard(dashboardOne);

        p2.setDashboard(dashboardTwo);
        for (int i = 0 ; i < 4; i++){
            dashboardOne.addStudentToHall(new Student(PawnColor.GREEN));
        }
        for (int i = 0; i < 3; i++){
            dashboardOne.addStudentToHall(new Student(PawnColor.CYAN));
        }

        for (int i = 0; i<2 ; i++){
            dashboardTwo.addStudentToHall(new Student(PawnColor.GREEN));
        }
        for (int i=0 ; i<5; i++){
            dashboardTwo.addStudentToHall(new Student(PawnColor.RED));
        }

        dashboardOne.drawDashboard();
        dashboardTwo.drawDashboard();

        RemoveStudentStrategy card = new RemoveStudentStrategy();
        card.setPlayersList(playersList);
        card.setBag(new StudentsBag());
        card.setPawnColor(PawnColor.GREEN);
        card.effect();

        dashboardOne.drawDashboard();
        dashboardTwo.drawDashboard();

        for (int i = 0; i<3; i++){
            assertNull(dashboardOne.getHall()[i]);
        }
        for (int i = 3; i < dashboardOne.getHall().length; i++){
            assertNotNull(dashboardOne.getHall()[i]);
        }

        for (int j = 0; j<2; j++){
            assertNull(dashboardTwo.getHall()[j]);
        }
        for (int j = 2; j < dashboardOne.getHall().length; j++){
            assertNotNull(dashboardTwo.getHall()[j]);
        }

    }
}