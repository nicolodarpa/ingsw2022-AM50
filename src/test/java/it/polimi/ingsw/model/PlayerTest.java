package it.polimi.ingsw.model;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class PlayerTest{
    @Test
    @DisplayName("Fill the hall")
    public void testMoveStudentToHall() {
        Player p = new Player("nic");
        StudentsBag s = new StudentsBag();
        s.fillBag(120);
        p.moveStudentsToHall(s);
        assertEquals(7,p.getDashboard().getHall().length);
        p.getDashboard().drawDashboard();
    }
    @Test
    @DisplayName("Play card with order 5 and 3 moves available")
    public void testPlayAssistantCard() {
        Deck testDeck = new Deck(1);
        Player p = new Player("nic");
        p.setDeck(testDeck);
        p.playAssistantCard(4);
        assertEquals(5, p.getOrder());
        assertEquals(3, p.getMovesOfMN());
    }
}


