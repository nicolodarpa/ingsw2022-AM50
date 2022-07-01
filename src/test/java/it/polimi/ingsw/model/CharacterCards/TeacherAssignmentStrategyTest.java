package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.server.controller.LoginManager;
import it.polimi.ingsw.server.model.CharacterCards.TeacherAssignmentStrategy;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.PawnColor;
import it.polimi.ingsw.server.model.Player;
import it.polimi.ingsw.server.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link TeacherAssignmentStrategy}
 */
public class TeacherAssignmentStrategyTest {
    private Game gameTest = new Game();


    @Test
    public void testGetCost() {
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        TeacherAssignmentStrategy card = new TeacherAssignmentStrategy();
        assertEquals(2, card.getCost());
        card.addCost();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testAddCost() {
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        TeacherAssignmentStrategy card = new TeacherAssignmentStrategy();
        card.addCost();
        assertEquals(3, card.getCost());
    }

    @Test
    public void testGetEffectOfTheCard() {
        gameTest = new Game(2);

        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        TeacherAssignmentStrategy card = new TeacherAssignmentStrategy();
        System.out.println(card.getEffectOfTheCard());
    }

    /**
     * We test the effect of the card with two player with the same number of students of the same color.
     * we check that teachers are assigned to the player who played the character card
     */
    @Test
    @DisplayName("Test effect with 2 players")
    public void testEffectOfTheCard() {
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        TeacherAssignmentStrategy card = new TeacherAssignmentStrategy();
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        card.setCurrentPlayer(player_one);
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher(PawnColor.RED);
        assertNotNull(player_two.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);

        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher(PawnColor.RED);
        assertNull(player_one.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);
        assertNotNull(player_two.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);


        card.effect();
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher(PawnColor.GREEN);
        assertNotNull(player_two.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher(PawnColor.GREEN);
        assertNotNull(player_one.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);
        assertNull(player_two.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();
    }

    /**
     * We test the effect of the card in a game with three player with the same number of students of the same color.
     * we check that teachers are assigned to the player who played the character card
     */
    @Test
    @DisplayName("Test effect with 3 players")
    public void testEffectOfTheCardThreePlayers() {
        gameTest.setNumberOfPlayers(3);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);
        LoginManager.login("ale", gameTest);
        TeacherAssignmentStrategy card = new TeacherAssignmentStrategy();
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        Player player_three = gameTest.getPlist().getPlayers().get(2);
        card.setCurrentPlayer(player_one);

        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher(PawnColor.RED);
        assertNotNull(player_two.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);

        player_three.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher(PawnColor.RED);
        assertNull(player_three.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);

        player_three.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher(PawnColor.GREEN);
        assertNotNull(player_three.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);

        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher(PawnColor.GREEN);
        assertNull(player_one.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);

        card.effect();

        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher(PawnColor.RED);
        assertNotNull(player_one.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);
        assertNull(player_two.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);
        assertNull(player_three.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);


        gameTest.assignTeacher(PawnColor.CYAN);
        assertNull(player_one.getDashboard().getTeacherTable()[PawnColor.CYAN.ordinal()]);




        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();
        player_three.getDashboard().drawDashboard();
    }




}
