package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.Deck;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    @DisplayName(" Test dimension of the deck")
    public void deckTest() {
        Deck deckTest = new Deck(1, "BLUE");
        assertEquals(10, deckTest.getCardsList().size());
    }


    @Test
    @DisplayName("Test getChosen")
    void getChosen() {
        Deck deckTest = new Deck(1, "BLUE");
        assertFalse(deckTest.getChosen());
    }

    @Test
    @DisplayName("Test setChosen")
    void setChosen() {
        Deck deckTest = new Deck(2, "BLUE");
        deckTest.setChosen(true);
        assertTrue(deckTest.getChosen());

    }

    @Test
    @DisplayName("Test getId")
    void getId() {
        Deck deckTest = new Deck(1, "BLUE");
        assertEquals(deckTest.getId(), 1);
    }


    @Test
    @DisplayName("Test getCardList")
    void getCardsList() {
        Deck deckTest = new Deck(1, "BLUE");
        assertEquals(deckTest.getCardsList().size(), 10);
    }

    @Test
    @DisplayName("Test getDeckColor")
    void getDeckColor() {
        Deck deckTest = new Deck(1, "BLUE");
        assertEquals(deckTest.getColor(), "BLUE");
    }

    @Test
    @DisplayName("Test getPlayer")
    void getPlayer() {
        Deck deckTest = new Deck(1, "BLUE");
        deckTest.setPlayer(new Player("nic"));
        assertEquals(deckTest.getPlayer().getName(), "nic");
    }

    @Test
    @DisplayName("Test getCardOrder")
    void getCardOrder() {
        Deck deckTest = new Deck(1, "BLUE");
        assertNull(deckTest.getCardOrder(11));
        AssistantCard card = new AssistantCard(2, 1);
        assertEquals(card, deckTest.getCardOrder(2));
    }
}
