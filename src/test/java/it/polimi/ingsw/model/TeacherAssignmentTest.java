package it.polimi.ingsw.model;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.CharacterCards.TeacherAssignment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TeacherAssignmentTest {
    private Game gameTest = new Game();


    @Test
    public void testGetCost(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        TeacherAssignment card = new TeacherAssignment(gameTest.getPlist());
        assertEquals(2, card.getCost());
        card.addCost();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        TeacherAssignment card = new TeacherAssignment(gameTest.getPlist());
        card.addCost();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard(){
        gameTest = new Game();
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        TeacherAssignment card = new TeacherAssignment(gameTest.getPlist());
        System.out.println(card.getEffectOfTheCard());
    }

    @Test
    public void testEffectOfTheCard(){
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz",gameTest);
        LoginManager.login("nic",gameTest);
        gameTest.setupGame();
        TeacherAssignment card = new TeacherAssignment(gameTest.getPlist());
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        card.effect();
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();
    }
}
