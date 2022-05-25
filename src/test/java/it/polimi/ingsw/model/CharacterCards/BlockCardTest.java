package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BlockCardTest {
    Game gameTest;



    @Test
    void effect() {
        gameTest = new Game(2);

        LoginManager.login("ale",gameTest);
        LoginManager.login("nic",gameTest);

        Player p1 = gameTest.getPlist().getPlayers().get(0);
        Player p2 = gameTest.getPlist().getPlayers().get(1);
        BlockCard card = new BlockCard();
        card.update(gameTest.getPlist(),gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        p1.setMovesOfMN(12);
        assertEquals(4,card.getAvailableBlockCards());
        card.effect();
        assertTrue(gameTest.getIslands().get(1).getBlock());
        if(gameTest.getIslandWithMN().getId() != card.index){
            gameTest.moveMN(p1,1);
            assertFalse(gameTest.getIslands().get(1).getBlock());  ///check error
            assertEquals(3,card.getAvailableBlockCards());
            card.update(gameTest.getPlist(),gameTest.getActualPlayer(), gameTest.getIslands(), null, 5, gameTest.getStudentsBag());
            card.effect();
            assertEquals(3,card.getAvailableBlockCards());
            card.update(gameTest.getPlist(),gameTest.getActualPlayer(), gameTest.getIslands(), null, 8, gameTest.getStudentsBag());
            card.effect();
            assertEquals(2,card.getAvailableBlockCards());
        }

    }
}