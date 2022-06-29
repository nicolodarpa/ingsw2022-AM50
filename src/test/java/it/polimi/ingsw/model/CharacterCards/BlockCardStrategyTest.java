package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.CharacterCards.BlockCardStrategy;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Island;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for {@link  BlockCardStrategy}
 */
public class BlockCardStrategyTest {
    Game gameTest;


    /**
     * Test the effect of the card,
     * checks that influence cannot be calculated on a blocked islands
     */
    @Test
    @DisplayName("Test effect")
    void effect() {
        gameTest = new Game(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        BlockCardStrategy card = new BlockCardStrategy();
        card.update(gameTest.getPlist(),p1, gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        p1.setMovesOfMN(12);
        assertEquals(4,card.getAvailableBlockCards());
        card.effect();
        Island island = gameTest.getIslands().get(1);
        assertTrue(island.isBlocked());
        assertEquals(3,card.getAvailableBlockCards());



    }


    /**
     * Blocks four  islands, so every block card is used,
     * checks that a fifth island cannot be blocked
     */
    @Test
    @DisplayName("Test all block cards already used")
    void blockUsed(){
        gameTest = new Game(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        BlockCardStrategy card = new BlockCardStrategy();
        for (int i = 1; i<5;i++){ //Blocks 4 islands
            card.update(gameTest.getPlist(),p1, gameTest.getIslands(), null, i, gameTest.getStudentsBag());
            card.effect();
            assertEquals(4-i,card.getAvailableBlockCards());
        }

        card.update(gameTest.getPlist(),p1, gameTest.getIslands(), null, 5, gameTest.getStudentsBag());//tries to block a 5th island
        card.effect();
        assertEquals(0,card.getAvailableBlockCards());
        assertFalse(gameTest.getIslands().get(5).isBlocked());//5th island isn't blocked

    }


    /**
     * We c
     */
    @Test
    @DisplayName("Test retrieve block cards")
    public void retrieveBlocks(){
        gameTest = new Game(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        BlockCardStrategy card = new BlockCardStrategy();
        for (int i = 1; i<5;i++){ //Blocks 4 islands
            card.update(gameTest.getPlist(),p1, gameTest.getIslands(), null, i, gameTest.getStudentsBag());
            card.effect();
            assertEquals(4-i,card.getAvailableBlockCards());
        }
        assertEquals(0, card.getAvailableBlockCards());

        for (int i = 1; i<5;i++){ //Calculate influence on the four blocked islands
            Island island = gameTest.getIslands().get(i);
            assertFalse(island.calculateInfluence(gameTest.getPlist()));
        }

        card.update(gameTest.getPlist(),p1, gameTest.getIslands(), null, 8, gameTest.getStudentsBag());
        card.effect();
        assertEquals(3,card.getAvailableBlockCards());
        card.effect();
        assertEquals(3,card.getAvailableBlockCards());

    }
}