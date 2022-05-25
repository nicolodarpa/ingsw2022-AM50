package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    @DisplayName(" Add assistantCards to the Deck")
    public void deckTest(){
        Deck deckTest = new Deck(1,"BLUE");
        assertEquals(10,deckTest.getCardsList().size());
    }

    @Test
    void getChosen() {
        Deck deckTest = new Deck(1,"BLUE");
        assertFalse(deckTest.getChosen());
    }

    @Test
    void setChosen() {
        Deck deckTest = new Deck(2,"BLUE");
        deckTest.setChosen(true);
        assertTrue(deckTest.getChosen());

    }

    @Test
    void getId() {
        Deck deckTest = new Deck(1,"BLUE");
        assertEquals(deckTest.getId(),1);
    }


    @Test
    void getCardsList() {
        Deck deckTest = new Deck(1,"BLUE");
        assertEquals(deckTest.getCardsList().size(),10);
    }
}
