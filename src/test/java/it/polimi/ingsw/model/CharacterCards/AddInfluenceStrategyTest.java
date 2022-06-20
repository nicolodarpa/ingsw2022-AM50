package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddInfluenceStrategyTest {

    private Game gameTest;

    @Test
    public void testGetCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        assertEquals(2, card.getCost());
    }

    @Test
    public void testAddCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.addCost();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getActualPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        System.out.println(card.getEffectOfTheCard());
    }

    @Test
    @DisplayName("Test effect of the card")
    public void testEffect(){
        Player player = new Player("ale");
        assertEquals(0,player.getInfluencePoint());
        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.setActualPlayer(player);
        card.effect();
        assertEquals(2,player.getInfluencePoint());
    }

    @Test
    @DisplayName("Test getName of the card")
    public void testGetName(){
        SpecialCardStrategy card = new AddInfluenceStrategy();
        assertNotNull(card.getName());
    }
}

