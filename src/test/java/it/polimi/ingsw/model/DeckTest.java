package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckTest {

    @Test
    @DisplayName(" Add assistantCards to the Deck")
    public void deckTest(){
        Deck deckTest = new Deck(1);
        assertEquals(10,deckTest.getCardsList().size());
    }
}
