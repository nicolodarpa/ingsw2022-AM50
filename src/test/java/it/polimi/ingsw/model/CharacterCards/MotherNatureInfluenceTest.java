package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.PlayersList;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.CharacterCards.MotherNatureInfluence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MotherNatureInfluenceTest {

    private Game gameTest;

    @Test
    public void testGetCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        MotherNatureInfluence card = new MotherNatureInfluence();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        MotherNatureInfluence card = new MotherNatureInfluence();
        card.addCost();
        assertEquals(4, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard() {
        gameTest = new Game(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        MotherNatureInfluence card = new MotherNatureInfluence();
        System.out.println(card.getEffectOfTheCard());
    }

    @Test
    void effect() {
        gameTest = new Game(2);

        LoginManager.login("ale", gameTest);
        LoginManager.login("jaz", gameTest);

        Player ale = gameTest.getPlist().getPlayerByName("ale");
        ale.getDashboard().addTeacherToTable(new Teacher(PawnColor.RED));
        Island island = gameTest.getIslands().get(3);
        island.addStudent(new Student(PawnColor.RED));
        MotherNatureInfluence card = new MotherNatureInfluence();
        card.setPlayersList(gameTest.getPlist());
        card.setIslands(gameTest.getIslands());
        card.setIndex(3);
        card.effect();
        assertEquals("ale", island.getOwner());


    }
}
