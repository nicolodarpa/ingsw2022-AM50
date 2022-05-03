package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpecialDeckTest {
    Game gameTest = new Game();
    SpecialDeck specialDeckTest = new SpecialDeck();

    @Test
    @DisplayName("Extract three different card")
    public void extractRandomTest(){
        specialDeckTest.extractRandomCard();
        System.out.println("Nel mazzo ci sono " + specialDeckTest.getSpecialCardsInGame().size() + " carte");
        System.out.println("Classe carta 1 " + specialDeckTest.getSpecialCardsInGame().get(0).getClass().getName());
        System.out.println("Classe carta 2 " + specialDeckTest.getSpecialCardsInGame().get(1).getClass().getName());
        System.out.println("Classe carta 3 " + specialDeckTest.getSpecialCardsInGame().get(2).getClass().getName());
    }
}
