package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import it.polimi.ingsw.server.model.CharacterCards.AddMoveMNStrategy;
import it.polimi.ingsw.server.model.CharacterCards.NoTowerInfluenceStrategy;
import it.polimi.ingsw.server.model.CharacterCards.SpecialCardStrategy;
import it.polimi.ingsw.server.model.Deck;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link AddMoveMNStrategy}
 */

public class AddMoveMNStrategyTest {

    private Game gameTest;


    /**
     * Tests if the available moves of mother nature are increase by the effect of the card
     */
    @Test
    @DisplayName("Effect test")
    public void effectTest(){
        gameTest = new Game(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        Player p2 = gameTest.getPlist().getPlayers().get(1);
        Deck d1 = new Deck(1,"BLUE");
        Deck d2 = new Deck(2,"PINK");
        p1.setDeck(d1);
        p2.setDeck(d2);
        p1.playAssistantCard(3);
        p2.playAssistantCard(5);
        gameTest.setCurrentPlayer();
        SpecialCardStrategy cardTest = new AddMoveMNStrategy();
        cardTest.update(gameTest.getPlist(),gameTest.getCurrentPlayer(), gameTest.getIslands(),null, 1, gameTest.getStudentsBag());
        assertEquals(2,gameTest.getCurrentPlayer().getMovesOfMN());
        cardTest.effect();
        assertEquals(4,gameTest.getCurrentPlayer().getMovesOfMN());
    }


    @Test
    public void testGetCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        System.out.println(card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        System.out.println(card.getCost());
        card.addCost();
        System.out.println(card.getCost());
    }
    


}
