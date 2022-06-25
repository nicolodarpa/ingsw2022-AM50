package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.CharacterCards.BlockCardStrategy;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test for {@link  BlockCardStrategy}
 */
class BlockCardStrategyTest {
    Game gameTest;


    /**
     * Test
     */
    @Test
    @DisplayName("Effect")
    void effect() {
        gameTest = new Game(2);
        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);
        Player p1 = gameTest.getPlist().getPlayers().get(0);
        Player p2 = gameTest.getPlist().getPlayers().get(1);
        BlockCardStrategy card = new BlockCardStrategy();
        card.update(gameTest.getPlist(),gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        p1.setMovesOfMN(12);
        assertEquals(4,card.getAvailableBlockCards());
        card.effect();
        assertTrue(gameTest.getIslands().get(1).getBlock());
        if(gameTest.getIslandWithMN().getId() != card.getIndex()){
            gameTest.moveMN(p1,1);
            assertFalse(gameTest.getIslands().get(1).getBlock());  ///check error
            assertEquals(3,card.getAvailableBlockCards());
            card.update(gameTest.getPlist(),gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 5, gameTest.getStudentsBag());
            card.effect();
            assertEquals(3,card.getAvailableBlockCards());
            card.update(gameTest.getPlist(),gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 8, gameTest.getStudentsBag());
            card.effect();
            assertEquals(2,card.getAvailableBlockCards());
        }

    }
}