package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.CharacterCards.MotherNatureInfluenceStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for {@link MotherNatureInfluenceStrategy}
 */
public class MotherNatureInfluenceStrategyTest {

    private Game gameTest;

    @Test
    public void testGetCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        MotherNatureInfluenceStrategy card = new MotherNatureInfluenceStrategy();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        MotherNatureInfluenceStrategy card = new MotherNatureInfluenceStrategy();
        card.addCost();
        assertEquals(4, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard() {
        gameTest = new Game(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        MotherNatureInfluenceStrategy card = new MotherNatureInfluenceStrategy();
        System.out.println(card.getEffectOfTheCard());
    }

    /**
     * Test if the influence is correctly calculated on an island without the presence of mother nature.
     * A student is added to an island and the relative teacher is assigned to a player. We check if the island owner
     * is the player
     */
    @Test
    void effect() {
        gameTest = new Game(2);
        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);
        Player ale = gameTest.getPlist().getPlayerByName("ale");

        ale.getDashboard().addTeacherToTable(new Teacher(PawnColor.RED));
        Island island = gameTest.getIslands().get(3);
        island.addStudent(new Student(PawnColor.RED));
        MotherNatureInfluenceStrategy card = new MotherNatureInfluenceStrategy();
        card.setPlayersList(gameTest.getPlist());
        card.setIslands(gameTest.getIslands());
        card.setIndex(3);
        card.effect();
        assertEquals("ale", island.getOwner());


    }
}
