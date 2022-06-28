package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.CharacterCards.NoTowerInfluenceStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.ToDoubleBiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for {@link NoTowerInfluenceStrategy}
 */
public class NoTowerInfluenceStrategyTest {

    private Game gameTest;


    /**
     * We check that the after activating the effect of the card towers on an island don't count for the influence calculation.
     *
     */
    @Test
    @DisplayName("Test effect")
    public void testEffect(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        Player player1 = gameTest.getPlist().getPlayerByName("jaz");
        Player player2 = gameTest.getPlist().getPlayerByName("nic");

        Dashboard dashboard1 = player1.getDashboard();
        Dashboard dashboard2 = player2.getDashboard();

        dashboard1.addTeacherToTable(new Teacher(PawnColor.RED));
        dashboard2.addTeacherToTable(new Teacher(PawnColor.GREEN));

        Island island = gameTest.getIslandWithMN(); // to be sure that there are no students on the island we test

        island.addStudent(new Student(PawnColor.RED));// we add one res student and calculate the influence, payer1 conquer the island
        island.calculateInfluence(gameTest.getPlist());
        assertEquals("jaz",island.getOwner());

        island.addStudent(new Student(PawnColor.GREEN));// we add two GREEN students
        island.addStudent(new Student(PawnColor.GREEN));
        island.calculateInfluence(gameTest.getPlist()); //influence is equals for the two players
        assertEquals("jaz",island.getOwner());//island owner doesn't on influence balance


        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, gameTest.getIslandWithMNIndex(), gameTest.getStudentsBag());
        card.effect();

        island.calculateInfluence(gameTest.getPlist()); //player2 win influence comparison, player1 tower doesn't count
        assertEquals("nic",island.getOwner());



    }

    /**
     *
     */
    @Test
    @DisplayName("TestGetCost")
    public void testGetCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());        assertEquals(3, card.getCost());
    }

    @Test
    @DisplayName("TestAddCost")
    public void testAddCost(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        card.addCost();
        assertEquals(4, card.getCost());
    }

    @Test
    @DisplayName("TestGetEffect")
    public void testGetEffectOfTheCard(){
        gameTest = new Game(2);

        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);

        NoTowerInfluenceStrategy card = new NoTowerInfluenceStrategy();
        card.update(gameTest.getPlist(), gameTest.getCurrentPlayer(), gameTest.getIslands(), null, 1, gameTest.getStudentsBag());
        System.out.println(card.getEffectOfTheCard());
    }




}
