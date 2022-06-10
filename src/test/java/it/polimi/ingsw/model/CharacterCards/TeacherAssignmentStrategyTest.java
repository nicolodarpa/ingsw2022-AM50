package it.polimi.ingsw.model.CharacterCards;

import it.polimi.ingsw.LoginManager;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("Test effect with 2 players")
    public void testEffectOfTheCard() {
        gameTest.setNumberOfPlayers(2);
        LoginManager.login("jaz", gameTest);
        LoginManager.login("nic", gameTest);

        TeacherAssignmentStrategy card = new TeacherAssignmentStrategy();
        Player player_one = gameTest.getPlist().getPlayers().get(0);
        Player player_two = gameTest.getPlist().getPlayers().get(1);
        card.setActualPlayer(player_one);
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher();
        assertNull(player_one.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);
        card.effect();
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher();
        assertNotNull(player_one.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);
        player_one.getDashboard().drawDashboard();
        player_two.getDashboard().drawDashboard();
    }

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
        card.setActualPlayer(player_one);
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        gameTest.assignTeacher();
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.RED));
        assertNotNull(player_two.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);
        assertNull(player_one.getDashboard().getTeacherTable()[PawnColor.RED.ordinal()]);
        card.effect();
        player_two.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        player_one.getDashboard().addStudentToClassroom(new Student(PawnColor.GREEN));
        gameTest.assignTeacher();
        assertNotNull(player_one.getDashboard().getTeacherTable()[PawnColor.GREEN.ordinal()]);
    }
}
