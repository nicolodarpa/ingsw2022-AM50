package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.CharacterCards.SpecialCardStrategy;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.SpecialDeck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *Tests for {@link SpecialDeck}
 */
public class SpecialDeckTest {
    Game gameTest = new Game();
    SpecialDeck specialDeckTest = new SpecialDeck();

    /**
     * test that there are 3 cards in the deck and all 3 cards are different from each other
     */
    @Test
    @DisplayName("Extract three different card")
    public void extractRandomTest(){
        specialDeckTest.extractRandomCard();
        SpecialCardStrategy card1 = specialDeckTest.getSpecialCard(0);
        SpecialCardStrategy card2 = specialDeckTest.getSpecialCard(1);
        SpecialCardStrategy card3 = specialDeckTest.getSpecialCard(2);
        assertNotEquals(card1.getName(), card2.getName());
        assertNotEquals(card1.getName(), card3.getName());
        assertNotEquals(card2.getName(), card3.getName());
        assertEquals(3, specialDeckTest.getSpecialCardsInGame().size());
    }
}
