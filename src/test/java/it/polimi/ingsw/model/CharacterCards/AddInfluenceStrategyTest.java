package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for {@link AddInfluenceStrategy}
 */
public class AddInfluenceStrategyTest {

    private Game gameTest;

    /**
     * Test if the initial cost of the card is correct
     */
    @Test
    public void testGetCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        assertEquals(2, card.getCost());
    }

    /**
     * Tests if the cost is correctly increased after playing the card
     */
    @Test
    public void testAddCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getPlist().getPlayerByName("nic"), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.effect();
        assertEquals(3, card.getCost());
    }

    /**
     * Test if the influence points of the player are correctly increased after playing the card
     */
    @Test
    @DisplayName("Test effect of the card")
    public void testEffect(){
        Player player = new Player("ale");
        assertEquals(0,player.getInfluencePoint());
        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.setCurrentPlayer(player);
        card.effect();
        assertEquals(2,player.getInfluencePoint());
    }

    /**
     * Test if the islands is assigned to the player with fewer players on the island with influence boosted by the special card
     */
    @Test
    @DisplayName("Test influence calculation fro an island")
    void effect() {
        Game gameTest = new Game(3);
        LoginManager.login("ale", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("jaz", gameTest);
        Island island = new Island(3);
        Player ale = gameTest.getPlist().getPlayerByName("ale");
        Player jaz = gameTest.getPlist().getPlayerByName("jaz");
        ale.getDashboard().addTeacherToTable(new Teacher(PawnColor.RED));
        jaz.getDashboard().addTeacherToTable(new Teacher(PawnColor.GREEN));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.RED));
        island.addStudent(new Student(PawnColor.GREEN));
        island.calculateInfluence(gameTest.getPlist());
        assertEquals("ale", island.getOwner() );
        island.calculateInfluence(gameTest.getPlist());
        System.out.println(island.getOwner());
        assertEquals("ale",island.getOwner());
        AddInfluenceStrategy card = new AddInfluenceStrategy();
        card.setCurrentPlayer(jaz);
        card.effect();
        island.addStudent(new Student(PawnColor.GREEN));
        island.calculateInfluence(gameTest.getPlist());
        assertEquals("jaz", island.getOwner());
    }

    @Test
    @DisplayName("Test getName of the card")
    public void testGetName(){
        SpecialCardStrategy card = new AddInfluenceStrategy();
        assertNotNull(card.getName());
    }
}

